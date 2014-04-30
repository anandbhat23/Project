import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;



public class SampleClient {

  public static void main(String args[]) throws Exception {
    
    int size = 0;
    
    String addr= InetAddress.getLocalHost().getHostAddress();


    ListenerThread lt = new ListenerThread();
    lt.start();
    
    ETLMessage msg = new ETLMessage(MessageType.MsgNewJobClient, "resources/conf.yaml", addr+":11111");

    
    MsgDispatcherClient dispatcher = new MsgDispatcherClient(msg, addr+":12345");
    dispatcher.start();

  }
  
  
  
  public static class ListenerThread extends Thread {

    public ListenerThread() {
    }

    public void run() {
      ServerSocket serverSock = null;
      int port = 11111;
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
  
  
  public static class MsgDispatcherClient extends Thread {

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
        System.out.println("msg sent to slave " + ip + " " + port + ".");
        s.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }  

  
  public static class MsgHandler extends Thread {

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
        if (type == MessageType.MsgTaskStatus) {
          System.out.println("Get Msg TaskStatus");
        }
        if (type == MessageType.MsgJobStatus) {
          System.out.println("Get Msg JobStatus");
        }
        if (type == MessageType.MsgSlaveStatus) {
          System.out.println("Get Msg SlaveStatus");
          Map<Integer, String> slaveMap = (Map<Integer, String>)msg.getObj();
          System.out.println("================");
          for(int k: slaveMap.keySet()) {
            System.out.println(slaveMap.get(k));
          }
        }
        
        if (type == MessageType.MsgNewMaster) {
          String addr = (String)msg.getObj();
          System.out.println("Get Msg new Master");
          

          ETLMessage m = new ETLMessage(MessageType.MsgNewJobClient, "resources/conf.yaml", addr+":11111");

          
          MsgDispatcherClient dispatcher = new MsgDispatcherClient(m, addr+":12345");
          dispatcher.start();
          
        }
      }catch(Exception e) {
        
      }
    }
  }

  
}
