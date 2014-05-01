import java.io.BufferedReader;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import protobuf.ProtoMessageConfig.ProtoMessage;
import utils.Fileserver;
import core.ETLJob;
import common.YamlParser;


public class MasterJobTracker {

  private static final int TASK_PER_SLAVE = 3;
  private static final int TIMEOUT = 2100; //ms
  private int MASTER_PORT = 12345;
  private String clientAddr = null;

  private int nextjobid = 0;

  // slave id to heatbeat timestamp
  private ConcurrentHashMap<Integer, Long> heartbeatMap = new ConcurrentHashMap<Integer, Long>();

  // TerminatedJobsList
  private HashSet<Integer> terminatedJobs = new HashSet<Integer>();

  // slave id to slave addr
  private Map<Integer, String> slaveAddr = new HashMap<Integer, String>();

  // job id to job instance
  private Map<Integer, ETLJob> idToJob = new HashMap<Integer, ETLJob>();

  // job id to job's number of tasks
  private Map<Integer, Integer> jobSize = new HashMap<Integer, Integer>();

  // key = slave id value = task list on slave
  private Map<Integer, List<Task>> slaveLoadMap = new HashMap<Integer, List<Task>>();
  // key=job id value= remaining tasks of the job
  private TreeMap<Integer, List<Task>> taskWaitQueue = new TreeMap<Integer, List<Task>>();
  // key=job id value= finished tasks of the job
  // TODO: Do we need to store those tasks?
  private TreeMap<Integer, List<Task>> taskFinishedQueue = new TreeMap<Integer, List<Task>>();

  private String localhost;
  private PrintWriter logger;
  
  public MasterJobTracker() {

    try {
      // TODO : should get from system config file
      logger = new PrintWriter("resources/log", "UTF-8");

      //BufferedReader br = new BufferedReader(new FileReader("resources/sysconfig"));
      //TODO Take this from central file server
      BufferedReader br = Fileserver.getFile("http://127.0.0.1:8000/sysconfig");
      String[] ms = br.readLine().split(":");
      localhost = InetAddress.getLocalHost().getHostAddress();
      if(ms[1].equals("localhost"))
        ms[1] = localhost;
      MASTER_PORT = Integer.parseInt(ms[2]);
      String line = br.readLine();
      int sid = 0;
      while (line != null) {
        ms = line.split(":");
        if(ms[1].equals("localhost"))
          ms[1] = InetAddress.getLocalHost().getHostAddress();
        slaveAddr.put(sid, ms[1]+":"+ms[2]);
        slaveLoadMap.put(sid, new LinkedList<Task>());
        sid++;
        line = br.readLine();
      } 
      br.close();
      
    } catch (Exception e) {
      
      e.printStackTrace();
    }

  }

  public void start() {
    ListenerThread listener = new ListenerThread();
    listener.start();
    
    //Heartbeat check timer
    Timer timer = new Timer();
    timer.schedule(new HeartBeatCheckTimer(), 15 * 1000, // initial delay
        2 * 1000); // subsequent rate
  }

  //every 2s check every slave
  class HeartBeatCheckTimer extends TimerTask {

    public void run() {
      // System.out.println("Check HeartBeat!");
      for (int slaveid : heartbeatMap.keySet()) {
        long now = System.currentTimeMillis();
        long ts = heartbeatMap.get(slaveid);

        if ((now - ts) < TIMEOUT) {
          //System.out.println("SLAVE "+slaveid+" IS GOOD");
        } else {
          // System.out.println("SLAVE "+slaveid+" IS BAD");
          // slave died!
          System.out.println("node " + slaveid + " is died!");
          FaultTolerance_NodeFailure(slaveid);
          updateSlaveList();
        }
        

        if(clientAddr!= null) {
          ETLMessage m = new ETLMessage(MessageType.MsgSlaveStatus, slaveAddr, null);
          SendMsgToClient(m);
        }

      }
    }
  }

  
  public class ListenerThread extends Thread {

    public ListenerThread() {
    }

    public void run() {
      ServerSocket serverSock = null;
      int port = MASTER_PORT;
      try {
        serverSock = new ServerSocket(port);
        while (true) {
          Socket socket = serverSock.accept();
          MsgHandler handler = new MsgHandler(socket);
          handler.start();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public void newETLJob(ETLJob job, String classname) {

    int jid = nextjobid++;
    idToJob.put(jid, job);
    
    logger.println(jid+","+classname+","+ "0");
    logger.flush();
    taskWaitQueue.put(jid, new LinkedList<Task>());
    taskFinishedQueue.put(jid, new LinkedList<Task>());

    distributeETLTasks(jid, job, classname);
  }

  public void distributeETLTasks(int jid, ETLJob job, String classname) {

    // TODO: buffer limit . when all slaves are busy.we buffer the task.
    int taskid = 0;
    int taskcount = 0;
    ProtoMessage m;
    while ((m = job.getNextMessage()) != null) {

      ETLTask task = new ETLTask(taskid++, jid, classname, m);

      int freeSlave = -1;
      for (int slave : slaveLoadMap.keySet()) {
        if (slaveLoadMap.get(slave).size() < TASK_PER_SLAVE)
          freeSlave = slave;
      }
      if (freeSlave == -1) {
        // all slave busy
        taskWaitQueue.get(jid).add(task);
      } else {
        task.setSlaveId(freeSlave);
        System.out.println("send task" + task.getTaskId() + " to slave "
            + freeSlave);
        slaveLoadMap.get(freeSlave).add(task);
        // System.out.println("[slaveLoadMap debug]"+slaveLoadMap.toString());
        ETLMessage msg = new ETLMessage(MessageType.MsgTaskStart, task, clientAddr);
        SendMsgToSlave(msg, freeSlave);
      }
      taskcount++;
    }

    jobSize.put(jid, taskcount);

    if (taskWaitQueue.get(jid).size() == 0) {
      taskWaitQueue.remove(jid);
    }

  }

  private void SendMsgToSlave(ETLMessage m, int slaveId) {
    MsgDispatcher dispatcher = new MsgDispatcher(m, slaveId);
    dispatcher.start();
  }

  public class MsgDispatcher extends Thread {

    private ETLMessage m;
    private int slaveId;

    public MsgDispatcher(ETLMessage m, int slaveId) {
      this.m = m;
      this.slaveId = slaveId;
    }

    public void run() {
      Socket s;
      try {
        String ip = slaveAddr.get(slaveId).split(":")[0];
        String port = slaveAddr.get(slaveId).split(":")[1];
        System.out.println(ip+" "+port);
        s = new Socket(ip, Integer.parseInt(port));
        ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
        os.writeObject(m);
        ObjectInputStream is = new ObjectInputStream(s.getInputStream());
        ETLMessage responseMsg = (ETLMessage) is.readObject();
        if (responseMsg.getType() != MessageType.MsgOK)
          throw new RuntimeException("MSG ERROR 01");
        System.out.println("msg sent to slave " + ip + " " + port + ".");
        s.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private void SendMsgToClient(ETLMessage m) {
    MsgDispatcherClient dispatcher = new MsgDispatcherClient(m, clientAddr);
    dispatcher.start();
  }

  public class MsgDispatcherClient extends Thread {

    private ETLMessage m;
    private String clientAddr;

    public MsgDispatcherClient(ETLMessage m, String clientAddr) {
      this.m = m;
      this.clientAddr = clientAddr;
    }

    public void run() {
      Socket s;
      try {
        
        if(clientAddr == null) 
          return;
        
        String ip = clientAddr.split(":")[0];
        String port = clientAddr.split(":")[1];
        s = new Socket(ip, Integer.parseInt(port));
        ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
        os.writeObject(m);
        ObjectInputStream is = new ObjectInputStream(s.getInputStream());
        ETLMessage responseMsg = (ETLMessage) is.readObject();
        if (responseMsg.getType() != MessageType.MsgOK)
          throw new RuntimeException("MSG ERROR 01");
        s.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }  
  
  
  public class MsgHandler extends Thread {

    Socket socket;

    public MsgHandler(Socket socket) {
      this.socket = socket;
    }

    @Override
    public void run() {
      processMessage(socket);
    }

    public void processMessage(Socket socket) {
      ObjectInputStream is;
      try {
        is = new ObjectInputStream(socket.getInputStream());
        ETLMessage msg = (ETLMessage) is.readObject();
        MessageType type = msg.getType();
        // TODO error handle
        ETLMessage response = new ETLMessage(MessageType.MsgOK, null, null);
        ObjectOutputStream out = new ObjectOutputStream(
            socket.getOutputStream());
        out.writeObject(response);
        out.flush();
        out.close();
        socket.close();
        if (type == MessageType.MsgTaskFinish) {
          System.out.println("Get Msg TaskFinish");
          Task task = (Task) msg.getObj();

          int jid = task.getJobId();
          if (terminatedJobs.contains(jid))
            return;

          TaskFinished(task);
          
        } else if (type == MessageType.MsgJobFailure) {
          System.out.println("Get Msg Job failure");
          Task task = (Task) msg.getObj();

          int jid = task.getJobId();
          if (terminatedJobs.contains(jid))
            return;

          String error_msg = (String) msg.getArg();
          System.out.println(error_msg);
          FaultTolerance_JobFailure(task);

          if(clientAddr!= null) {
            ETLMessage m = new ETLMessage(MessageType.MsgJobStatus, 0,null);
            SendMsgToClient(m);
          }
        }

        else if (type == MessageType.MsgTaskFailure) {

          Task task = (Task) msg.getObj();
          System.out.println("Get Msg Task failure tid=" + task.getTaskId());
          int jid = task.getJobId();
          if (terminatedJobs.contains(jid))
            return;

          String error_msg = (String) msg.getArg();
          System.out.println(error_msg);
          FaultTolerance_JobFailure(task);
        }

        else if (type == MessageType.MsgHeartBeat) {

          Integer slaveid = (Integer) msg.getObj();
          // System.out.println("Get Msg Heart Beat from sid=" +slaveid);
          heartbeatMap.put(slaveid, System.currentTimeMillis());
        }
        
        else if (type == MessageType.MsgNewJob) {
          String conf_name = (String) msg.getObj();
          System.out.println("master starts a new job! " + conf_name);
          List<ETLJob> etlJobs = YamlParser.parse(conf_name);
          for(ETLJob etlJob : etlJobs){
            newETLJob(etlJob, "");
          }
        }
        
        else if (type == MessageType.MsgNewJobClient) {
          String conf_url = (String) msg.getObj();
          if(clientAddr==null) {
             clientAddr =  (String) msg.getArg();
          }
          System.out.println("master starts a new job! " + conf_url);
          List<ETLJob> etlJobs = YamlParser.parseFromURL(conf_url);
          for(ETLJob etlJob : etlJobs){
            newETLJob(etlJob, "");
          }
        }
        
        else if (type == MessageType.MsgNewSlaveRequest) {
          String new_addr = (String) msg.getObj();
          System.out.println("master get new slave! " + new_addr);
          int sid = slaveAddr.size();
          slaveAddr.put(sid, new_addr);
          slaveLoadMap.put(sid, new LinkedList<Task>());
          
          
          ArrayList<String> addrs = new ArrayList<String>();
          for(int k : slaveAddr.keySet()) {
            addrs.add(slaveAddr.get(k));
          }
            
          ETLMessage m = new ETLMessage(MessageType.MsgNewSlaveUpdate, addrs, localhost+":"+MASTER_PORT, clientAddr);

          for(int i=0;i<sid+1;i++) {
          
             SendMsgToSlave(m, i);
          }
          
        }


      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  
  
  private void updateSlaveList() {
    
    System.out.println("update slave list!");
    
    ArrayList<String> addrs = new ArrayList<String>();
    
    for(int k: slaveAddr.keySet() ) {
      addrs.add(slaveAddr.get(k));
    }
    
    ETLMessage m = new ETLMessage(MessageType.MsgNewSlaveRemove, addrs, null);

    
    for(int k:slaveAddr.keySet()) {
      SendMsgToSlave(m, k);
    }
  }

  public void TaskFinished(Task t) {

    int jid = t.getJobId();
    int slaveid = t.getSlaveId();
    int tid = t.getTaskId();

    System.out.println("Task finished jid ==" + jid + " tid==" + tid
        + " from slave" + slaveid);

    if(clientAddr!= null) {
      ETLMessage m = new ETLMessage(MessageType.MsgTaskStatus, 1, slaveid);
      SendMsgToClient(m);
    }
    
    // TODO : this is o(n) operation
    Task dummy_task = new ETLTask(tid, jid, null, null);
    List<Task> slave_task_list = slaveLoadMap.get(slaveid);
    // get task from slave task list
    Task task = slave_task_list.remove(slave_task_list.indexOf(dummy_task));

    // put this task into finishedQueue
    List<Task> job_finished_list = taskFinishedQueue.get(jid);
    job_finished_list.add(task);

    if (taskWaitQueue.size() != 0) {
      int nextjb = taskWaitQueue.firstKey();
      List<Task> nextTaskList = taskWaitQueue.get(nextjb);
      Task nextTask = nextTaskList.remove(0);
      if (nextTaskList.size() == 0) {
        taskWaitQueue.remove(nextjb);
      }
      nextTask.setSlaveId(slaveid);
      slaveLoadMap.get(slaveid).add(nextTask);
      ETLMessage msg = new ETLMessage(MessageType.MsgTaskStart, nextTask, clientAddr);
      System.out.println("send task" + nextTask.getTaskId() + " to slave "
          + slaveid);
      SendMsgToSlave(msg, slaveid);

    }

    if (job_finished_list.size() == jobSize.get(jid)) {
      // we finish this job!
      System.out.println("job" + jid + " finished!");
      // clean up here
      terminatedJobs.add(jid);
      taskFinishedQueue.remove(jid);
      
      logger.println(jid+","+","+ "1");
      logger.flush();
      

      if(clientAddr!= null) {
        ETLMessage m = new ETLMessage(MessageType.MsgJobStatus, 1, null);
        SendMsgToClient(m);
      }
      
    }
  }

  // simple handler. just delete job info on master
  public void FaultTolerance_JobFailure(Task task) {
    int jid = task.getJobId();
    terminatedJobs.add(jid);
    taskWaitQueue.remove(jid);
    taskFinishedQueue.remove(jid);
    // We are not going to clean SlaveLoadMap here.
    // as it will be cleaned on timeout
  }

  // simple handler. just re-assign task on another node
  public void FaultTolerance_TaskFailure(Task task) {

    int retry = task.getRetry();
    if (retry == 2) {
      FaultTolerance_JobFailure(task);
    }

    int jid = task.getJobId();
    int slaveid = task.getSlaveId();

    List<Task> slave_task_list = slaveLoadMap.get(slaveid);
    // get real task from slave task list
    Task true_task = slave_task_list.remove(slave_task_list.indexOf(task));
    true_task.setRetry(true_task.getRetry() + 1);
    taskWaitQueue.get(jid).add(true_task);
  }

  // simple handler.remove node, restart unfinished task, no return
  public void FaultTolerance_NodeFailure(int slaveid) {

    slaveAddr.remove(slaveid);
    heartbeatMap.remove(slaveid);

    // retry current tasks
    List<Task> slave_task_list = slaveLoadMap.get(slaveid);

    if (slave_task_list == null)
      return;

    System.out.println("slave running task = " + slave_task_list.toString());
    slaveLoadMap.remove(slaveid);
    if (slave_task_list.size() == 0)
      return;

    for (Task t : slave_task_list) {
      int jid = t.getJobId();
      int tid = t.getTaskId();
      System.out.println("fault tolerant. re-start task jid=" + jid + " tid="
          + tid);

      int freeSlave = -1;
      for (int slave : slaveLoadMap.keySet()) {
        if (slaveLoadMap.get(slave).size() < TASK_PER_SLAVE)
          freeSlave = slave;
      }
      if (freeSlave == -1) {

        // all slave busy. put task back into task queue
        if (!taskWaitQueue.containsKey(jid))
          taskWaitQueue.put(jid, new LinkedList<Task>());
        taskWaitQueue.get(jid).add(t);
        System.out.println("[dbg] now taskWaitQueue is "
            + taskWaitQueue.get(jid));
      } else {
        // we have free slave now. start task on the free slave
        t.setSlaveId(freeSlave);
        System.out.println("send task" + t.getTaskId() + " to slave "
            + freeSlave);
        slaveLoadMap.get(freeSlave).add(t);
        ETLMessage msg = new ETLMessage(MessageType.MsgTaskStart, t, null);
        SendMsgToSlave(msg, freeSlave);
      }
    }

  }
  
  public void restart(List<String> slave_addr, String clientAddr) {
    
    this.clientAddr = clientAddr;
    int sid = 0;
    for(String s: slave_addr) {
      slaveAddr.put(sid,s);
      slaveLoadMap.put(sid, new LinkedList<Task>());
      sid++;
    }
    
    ListenerThread listener = new ListenerThread();
    listener.start();
    
    //Heartbeat check timer
    Timer timer = new Timer();
    timer.schedule(new HeartBeatCheckTimer(), 5* 1000, // initial delay
        2 * 1000); // subsequent rate

    int largest_jid = 0;
    HashMap<Integer, String> jobstatus = new HashMap<Integer, String>();
    try {
      BufferedReader br = new BufferedReader(new FileReader("resources/log"));
      String line = br.readLine();
      while (line != null) {
        String[] status = line.split(",");
        int jid = Integer.parseInt(status[0]);
        largest_jid = Math.max(largest_jid, jid);
        String conf_name = status[1];
        String stat = status[2];

        if (stat.equals("0")) {
          jobstatus.put(jid, conf_name);
        }
        if (stat.equals("1")) {
          jobstatus.remove(jid);
        }
      }
      br.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    this.nextjobid = largest_jid+1;
    
    for(int jid : jobstatus.keySet()) {
      String conf_name = jobstatus.get(jid);
      List<ETLJob> etlJobs = YamlParser.parse(conf_name);
      for(ETLJob etlJob : etlJobs){
        newETLJob(etlJob, "");
      }
    }

  }

}
