import java.io.BufferedReader;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import protobuf.ProtoMessageConfig.ProtoMessage;
import utils.Fileserver;
import common.Parser;
import common.ParserFactory;
import core.ETLJob;
import core.Exporter;
import core.Importer;
import core.Transformer;

public class SlaveTaskTracker {


  private static final int ANSWER_TIMEOUT = 3000; // ms
  private static final int TIMEOUT = 2000; // ms
  private static final int MASTER_PORT = 12345; // ms
  
	String masterAddr;
	int port;
	String slaveAddr;
	List<String> slaveList;
	int slaveid;
	boolean bootstrapped = false;
	String clientAddr;
	
	boolean master_died= false;
	boolean inElection = false;
	boolean electionStartedByMe = false;
	long msgElectionTime;

	public SlaveTaskTracker(int port) {

    try {
      // TODO : should get from system config file
      this.port = port;
      BufferedReader br = new BufferedReader(new FileReader("resources/sysconfig"));
      //BufferedReader br = Fileserver.getFile("http://127.0.0.1:8000/sysconfig");
      String[] ms = br.readLine().split(":");
      if(ms[1].equals("localhost"))
        ms[1] = InetAddress.getLocalHost().getHostAddress();
      masterAddr = ms[1]+":"+ms[2];
      String line = br.readLine();
      int sid = 0;
      slaveList = new ArrayList<String>();
      while (line != null) {
        ms = line.split(":");
        if(ms[1].equals("localhost")) {
          
          ms[1] = InetAddress.getLocalHost().getHostAddress();
          
          if(ms[2].equals(String.valueOf(port))) {
            this.slaveid = sid;
            slaveAddr= ms[1]+":"+ms[2];
            bootstrapped = true;
          }
          
        }
        slaveList.add(ms[1]+":"+ms[2]);
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
		Timer timer = new Timer();
		timer.schedule(new HeartBeatTimer(), 10 * 1000, // initial delay
				2 * 1000); // subsequent rate
	}

	class HeartBeatTimer extends TimerTask {

		public void run() {
			// System.out.println("HeartBeat!");
		  if(!master_died) {
			  ETLMessage m = new ETLMessage(MessageType.MsgHeartBeat, slaveid,
					null);
		  	sendMsgToMaster(m);
		  }
		  
		  if(master_died && inElection && electionStartedByMe) {
		    long cur = System.currentTimeMillis();
		    if(cur- msgElectionTime > ANSWER_TIMEOUT) {
		      System.out.println("I WIN ELECTION!");
          inElection = false;
          electionStartedByMe=false;
          master_died = false;
          
          MasterJobTracker jobTracker = new MasterJobTracker();
          jobTracker.restart(slaveList, clientAddr);
          
          try {
          String ip = InetAddress.getLocalHost().getHostAddress();
          ETLMessage m = new ETLMessage(MessageType.MsgVictory,
              ip+":"+MASTER_PORT, null);
            inElection = true;
            msgElectionTime = System.currentTimeMillis();
            electionStartedByMe=true;
            multicast(m);
            
            
            System.out.println("client is "+clientAddr+" new master sent");
            
            ETLMessage m2 = new ETLMessage(MessageType.MsgNewMaster,
                ip+":"+MASTER_PORT, null);  
            
            sendMsgToSlave(m2, clientAddr);
          }catch(Exception e) {
            e.printStackTrace();
          }
          
          
		    }
		  }
		}
	}

	public class ListenerThread extends Thread {

		public ListenerThread() {
		}

		public void run() {
			ServerSocket serverSock = null;

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
				ETLMessage response = new ETLMessage(MessageType.MsgOK, null,
						null);
				ObjectOutputStream out = new ObjectOutputStream(
						socket.getOutputStream());
				out.writeObject(response);
				out.flush();
				out.close();
				socket.close();

				if (type == MessageType.MsgTaskStart) {
					Task task = (ETLTask) msg.getObj();
					String client = (String) msg.getArg();
					if(clientAddr == null) {
					  clientAddr = client;
					}
					System.out.println("get Msg TaskStart");
					TaskStart(task);
				}
				
        if (type == MessageType.MsgNewSlaveRemove) {
          ArrayList<String> addrs = ((ArrayList<String>) msg.getObj());
          addrs.remove(slaveAddr);
          slaveList = addrs;
          System.out.println("get Msg SlaveList Update: "+addrs);
          
        }
				
        if (type == MessageType.MsgElection) {
          System.out.println("get msg elec");
          master_died= true;
          String e_slaveid = Integer.toString((Integer) msg.getObj());
          System.out.println("Received MsgElection from "+ e_slaveid);
          if(slaveid> Integer.parseInt(e_slaveid)) {
            ETLMessage m = new ETLMessage(MessageType.MsgAnswer,
                slaveid, null);
            System.out.println("Give MsgAnswer to "+ e_slaveid);
            int sid = Integer.parseInt( e_slaveid);
            System.out.println("msg sent to "+slaveList.get(sid));
            sendMsgToSlave(m, slaveList.get(sid));
            
            if(!electionStartedByMe) {
              m = new ETLMessage(MessageType.MsgElection,
                slaveid, null);
              inElection = true;
              msgElectionTime = System.currentTimeMillis();
              electionStartedByMe=true;
              multicast(m);
            }
          } else {
            inElection = false;
            electionStartedByMe=false;
          }
        }
        
        if (type == MessageType.MsgAnswer) {
          System.out.println("get msg ans");
          inElection = false;
          electionStartedByMe=false;
        }
        
        if (type == MessageType.MsgMasterAddrRequest) {
          System.out.println("get master addr req");
          String dst = (String) msg.getObj();
          if(master_died== false) {
            ETLMessage m = new ETLMessage(MessageType.MsgMasterAddrResponse, masterAddr,null);
            sendMsgToSlave(m,dst);
          }
        }
        
        if (type == MessageType.MsgVictory) {
          System.out.println("get msg victory");
          String m_addr = (String) msg.getObj();
          masterAddr = m_addr;
          inElection = false;
          electionStartedByMe=false;
          master_died = false;
          
        }
        
        if (type == MessageType.MsgNewSlaveUpdate) {
          System.out.println("get msg slave update");
          ArrayList<String> addrs = ((ArrayList<String>) msg.getObj());
          
          if(!bootstrapped) {
            bootstrapped = true;
            slaveList = addrs;
            slaveid = addrs.size()-1;
            addrs.remove(addrs.size()-1);
            masterAddr =  (String) msg.getArg();
            if(msg.getArg2()!= null)
              clientAddr = (String)msg.getArg2();
            System.out.println("update slave list "+ addrs);
          } else {
            String newslave = addrs.get(addrs.size()-1);
            System.out.println("update slave lis. add "+ newslave);
            slaveList.add(newslave);
          }
          
        }
        
        
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void TaskStart(Task T) {
		TaskRunner runner = new TaskRunner(T);
		runner.start();
	}

	public class TaskRunner extends Thread {

		Task task;

		public TaskRunner(Task task) {
			this.task = task;
		}

		@Override
		public void run() {
			ETLJob job = null;

			System.out.println("here we start our task!");
			int jid = task.getJobId();
			int taskid = task.getTaskId();

			System.out.println("here we start our task! jid=" + jid + " tid="
					+ taskid);
			ProtoMessage protoMessage = null;
			try {
				protoMessage = task.getMessage();
				Parser parser = ParserFactory.getParser(protoMessage
						.getImporter().getType());
				Importer importer = parser
						.createImporterFromProtoMessage(protoMessage
								.getImporter().getAllFields());
				String transformation = protoMessage.getTransformer().getTransformOp();
				parser = ParserFactory.getParser(protoMessage
						.getExporter().getType());
				Exporter exporter = parser
						.createExporterFromProtoMessage(protoMessage
								.getExporter().getAllFields());
				job = new ETLJob(importer, exporter, transformation);
			} catch (Exception e) {
				// FT This is a job failure
				String err_msg = e.getMessage();
				Task task = new ETLTask(taskid, jid, null, null);
				ETLMessage msg = new ETLMessage(MessageType.MsgJobFailure,
						task, err_msg);
				sendMsgToMaster(msg);
				return;
			}

			try {
				//TODO ADD the transformations
				List<Map<String, String>> data = job.getImporter().importData(
						protoMessage);
				List<Map<String, String>> transformedData = Transformer.applyTransformations(data, job.getTransformation());
				job.getExporter().export(transformedData);

			} catch (Exception e) {
				// FT This is a task failure
				System.out.println("Exception running etl task. jid=" + jid
						+ " tid" + taskid + ". This is a task failure.");
				String err_msg = e.getMessage();
				Task task = new ETLTask(taskid, jid, null, null);
				ETLMessage msg = new ETLMessage(MessageType.MsgTaskFailure,
						task, err_msg);
				sendMsgToMaster(msg);

			}

			System.out.println("task  jid=" + jid + " tid=" + taskid
					+ " finished");
			task.setMessage(null);
			ETLMessage msg = new ETLMessage(MessageType.MsgTaskFinish, task,
					null);
			sendMsgToMaster(msg);
		}

	}

	private void sendMsgToMaster(ETLMessage m) {
	  if(master_died) {
	    return;
	  }
		MsgDispatcher dispatcher = new MsgDispatcher(m, masterAddr);
		dispatcher.start();
	}
	
	 public void sendJobToMaster(String config) {
	    if(master_died) {
	      System.out.println("master died!");
	      return;
	    }
	    ETLMessage msg = new ETLMessage(MessageType.MsgNewJob, config,
	          null);
	    MsgDispatcher dispatcher = new MsgDispatcher(msg, masterAddr);
	    dispatcher.start();
	  }
	 
	 
	 public void addNode(int port) throws Exception{
	   
	    ListenerThread listener = new ListenerThread();
	    listener.start();
	    Timer timer = new Timer();
	    timer.schedule(new HeartBeatTimer(), 10 * 1000, // initial delay
	        2 * 1000); // subsequent rate
	   
	   String localhost = InetAddress.getLocalHost().getHostAddress();
     ETLMessage msg = new ETLMessage(MessageType.MsgNewSlaveRequest, localhost+":"+port,
         null);
     sendMsgToMaster(msg);
     
     
	 }

	
	private void sendMsgToSlave(ETLMessage m, String slave) { 
	  MsgDispatcherSlave dispatcher = new MsgDispatcherSlave(m, slave); 
	  dispatcher.start(); 
	}
	
	private void multicast(ETLMessage m) {
	  for(String s: slaveList) {
	    if(!s.equals(slaveAddr)) {
	      System.out.println("sent to"+s);
	      sendMsgToSlave(m,s);
	    }
	  }
	}

	public class MsgDispatcher extends Thread {
		private ETLMessage m;
		private String dstAddr;

		public MsgDispatcher(ETLMessage m, String dstAddr) {
			this.m = m;
			this.dstAddr = dstAddr;
		}

		public void run() {
			Socket s;
			try {
				String ip = dstAddr.split(":")[0];
				String port = dstAddr.split(":")[1];
				s = new Socket(ip, Integer.parseInt(port));
				ObjectOutputStream os = new ObjectOutputStream(
						s.getOutputStream());
				os.writeObject(m);
				os.flush();
				ObjectInputStream is = new ObjectInputStream(s.getInputStream());
				ETLMessage responseMsg = (ETLMessage) is.readObject();
				if (responseMsg.getType() != MessageType.MsgOK)
					throw new RuntimeException("MSG ERROR");
				s.close();
			} catch (Exception e) {
				master_died = true;
				System.out.println("master died. start process");
				if(!inElection){
				    inElection = true;
				    if(!electionStartedByMe) {
				      electionStartedByMe = true;
				      System.out.println("Master is dead.. Starting an election...");
              ETLMessage m = new ETLMessage(MessageType.MsgElection,
                slaveid, null);
              msgElectionTime = System.currentTimeMillis();
              multicast(m);
				    }
				}
			}
		}

	}
	
	 public class MsgDispatcherSlave extends Thread {
	    private ETLMessage m;
	    private String dstAddr;

	    public MsgDispatcherSlave(ETLMessage m, String dstAddr) {
	      this.m = m;
	      this.dstAddr = dstAddr;
	    }

	    public void run() {
	      Socket s;
	      try {
	        String ip = dstAddr.split(":")[0];
	        String port = dstAddr.split(":")[1];
	        s = new Socket(ip, Integer.parseInt(port));
	        ObjectOutputStream os = new ObjectOutputStream(
	            s.getOutputStream());
	        os.writeObject(m);
	        os.flush();
	        ObjectInputStream is = new ObjectInputStream(s.getInputStream());
	        ETLMessage responseMsg = (ETLMessage) is.readObject();
	        if (responseMsg.getType() != MessageType.MsgOK)
	          throw new RuntimeException("MSG ERROR");
	        s.close();
	      } catch (Exception e) {
	        
	      }
	    }
	 }

}
