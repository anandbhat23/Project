package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import multicast.MsgDispatcher;
import utils.YamlParser;
import config.Configuration;
import config.ConnectionParameters;
import config.MasterConfig;
import config.MessageType;
import core.Message;

public class TaskTracker {

	private Configuration configuration;
	private String configuration_filename;
	private ConcurrentHashMap<String, Long> heartbeatMap = new ConcurrentHashMap<String, Long>();
	private MasterConfig masterConfig;
	int port;
	private static final int TIMEOUT = 9000; // ms
	private static final int MASTER_TIMEOUT = 20000; // ms
	private static final int ANSWER_TIMEOUT = 9000; // ms
	boolean inElection = false;
	boolean electionStartedByMe = false;
	long msgElectionTime = 0;

	public TaskTracker(String configuration_filename, String localName) {
		this.configuration_filename = configuration_filename;
		masterConfig = new MasterConfig();
		initialiseConfig(localName);
	}

	public void startHeartBeatSend() {
		ListenerThread listener = new ListenerThread();
		listener.start();
		Timer timer = new Timer();
		timer.schedule(new HeartBeatTimer(), 10 * 1000, // initial delay
				2 * 1000); // subsequent rate
	}

	class HeartBeatTimer extends TimerTask {

		public void run() {
			//System.out.println("Sending HeartBeat!");
			String arg = null;
			if(masterConfig.amMaster(configuration.getServerId())){
				arg = "M";
			}
			Message m = new Message(MessageType.MsgHeartBeat,
					configuration.getServerId(), arg);
			muticastToGroup(m);
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

	private void muticastToGroup(Message m) {
		MsgDispatcher dispatcher = new MsgDispatcher(m, configuration);
		dispatcher.start();
	}
	
	private void sendTo(String serverId, Message m) {
		MsgDispatcher dispatcher = new MsgDispatcher(m, configuration, serverId);
		dispatcher.start();
	}

	public void startHeartBeatCheck() {
		ListenerThread listener = new ListenerThread();
		listener.start();

		// Heartbeat check timer
		Timer timer = new Timer();
		timer.schedule(new HeartBeatCheckTimer(), 15 * 1000, // initial delay
				3 * 1000); // subsequent rate
	}

	// every 2s check every slave
	class HeartBeatCheckTimer extends TimerTask {

		public void run() {
			
			if(masterConfig.amMaster(configuration.getServerId())){
				//I'm the master node
				for (String slaveid : heartbeatMap.keySet()) {
					if(!slaveid.equalsIgnoreCase(configuration.getServerId())){
						long now = System.currentTimeMillis();
						long ts = heartbeatMap.get(slaveid);
						
							if ((now - ts) < TIMEOUT) {
								// this slave is good
								// System.out.println("SLAVE "+slaveid+" IS GOOD");
							} else {
								// System.out.println("SLAVE "+slaveid+" IS BAD");
								// slave died!
								System.out.println(slaveid + " is dead!");
								// TODO Handle TimeOut
							}
						}
					}
			}else{
				//I'm a slave node
				long now = System.currentTimeMillis();
				if(masterConfig.getId() != null){
					masterConfig.setHeartbeat(heartbeatMap.get(masterConfig.getId()));
				}
				if(!inElection){
					System.out.println("Time waiting for Master Heartbeat "+ (now - masterConfig.getHeartbeat()));
					if((now - masterConfig.getHeartbeat()) < MASTER_TIMEOUT){
						System.out.println("Master is alive");
					}else{
						if(!electionStartedByMe){
							System.out.println("Master is dead.. Starting an election...");
							masterConfig.setId(null);
							Message m = new Message(MessageType.MsgElection,
									configuration.getServerId(), null);
							inElection = true;
							electionStartedByMe = true;
							msgElectionTime = System.currentTimeMillis();
							muticastToGroup(m);
						}
					}
				}else{
					System.out.println("Time waiting for MsgAnswer "+ (now - msgElectionTime));
					if((now - msgElectionTime) < ANSWER_TIMEOUT){
						System.out.println("Waiting for MsgAnswer");
					}else{
						System.out.println("Announcing Victory");
						Message m = new Message(MessageType.MsgVictory,
								configuration.getServerId(), null);
						muticastToGroup(m);
						electionStartedByMe = false;
						inElection = false;
						masterConfig.setId(configuration.getServerId());
					}
				}
			}
		}
	}
	
	public void initialiseConfig(String localName){
		try {
			this.configuration = YamlParser.readYaml(configuration_filename);
			configuration.setServerId(localName);
			this.port = configuration.getConnections().get(localName).getPort();
			for (Entry<String, ConnectionParameters> connection : configuration.getConnections().entrySet()) {
				if(connection.getKey() != configuration.getServerId()){
					heartbeatMap.put(connection.getKey(), (long) 0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
				Message msg = (Message) is.readObject();
				MessageType type = msg.getType();
				Message response = new Message(MessageType.MsgOK, null, null);
				ObjectOutputStream out = new ObjectOutputStream(
						socket.getOutputStream());
				out.writeObject(response);
				out.flush();
				out.close();
				socket.close();

				if (type == MessageType.MsgHeartBeat) {

					String slaveid = (String) msg.getObj();
					String arg = (String)msg.getArg();
					if(arg != null && arg.equalsIgnoreCase("M")){
						masterConfig.setHeartbeat(System.currentTimeMillis());
					}
					System.out.println("Got Msg Heart Beat from sid=" + slaveid);
					heartbeatMap.put(slaveid, System.currentTimeMillis());
				}
				else if (type == MessageType.MsgElection) {
					String slaveid = (String) msg.getObj();
					System.out.println("Received MsgElection from "+ slaveid);
					if(configuration.getServerId().compareToIgnoreCase(slaveid) > 0){
						Message m = new Message(MessageType.MsgAnswer,
								configuration.getServerId(), null);
						sendTo(slaveid, m);
						if(!electionStartedByMe){
							m = new Message(MessageType.MsgElection,
									configuration.getServerId(), null);
						}
						msgElectionTime = System.currentTimeMillis();
						muticastToGroup(m);
					}
				}
				else if (type == MessageType.MsgAnswer) {
					System.out.println("Received MsgAnswer");
					inElection = false;
					masterConfig.setHeartbeat(System.currentTimeMillis());
					electionStartedByMe = false;
				}
				else if (type == MessageType.MsgVictory) {
					String master = (String) msg.getObj();
					System.out.println("Received MsgVictory from "+ master);
					inElection = false;
					masterConfig.setId(master);
					heartbeatMap.put(master, System.currentTimeMillis());
					electionStartedByMe = false;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
