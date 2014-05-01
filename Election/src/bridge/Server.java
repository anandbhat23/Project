package bridge;



import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;

import common.ETLMessage;
import common.MessageType;


public class Server {

	String ip = "128.237.214.105";
	String port = "12345";

	public Server() {
		ListenerThread lt = new ListenerThread();
		lt.start();
	}

	public void sendJob(String yamlFile) {
		String addr = null;
		try {
			addr = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String yamlURI = "http://"+addr+":8000/"+yamlFile;
		ETLMessage msg = new ETLMessage(MessageType.MsgNewJobClient, yamlURI, addr + ":11111");

		if(ip !=null && port !=null){
			MsgDispatcherClient dispatcher = new MsgDispatcherClient(msg, ip +":"+ port);
			dispatcher.start();
		}

	}

	/*
	 * ServerSocket sersock = null; try { sersock = new ServerSocket(3000); }
	 * catch (IOException e) {
	 * System.out.println("Not able to create the socket!"); }
	 * System.out.println("Server ready for chatting");
	 * 
	 * Socket sock = null; try { sock = sersock.accept( ); } catch (IOException
	 * e) { System.out.println("Error in accept call"); }
	 * 
	 * // reading from keyboard (keyRead object) BufferedReader keyRead = new
	 * BufferedReader(new InputStreamReader(System.in));
	 * 
	 * // sending to client (pwrite object) OutputStream ostream = null; try {
	 * ostream = sock.getOutputStream(); } catch (IOException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } PrintWriter pwrite =
	 * new PrintWriter(ostream, true);
	 * 
	 * // receiving from server ( receiveRead object) InputStream istream =
	 * null; try { istream = sock.getInputStream(); } catch (IOException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); } BufferedReader
	 * receiveRead = new BufferedReader(new InputStreamReader(istream));
	 * 
	 * String receiveMessage, sendMessage = null;
	 * 
	 * while(true) { try { if((receiveMessage = receiveRead.readLine()) != null)
	 * { System.out.println(receiveMessage);
	 * 
	 * if(receiveMessage.contains("hey")) { Screen4.addSlave(1); }
	 * if(receiveMessage.contains("hi")) { Screen4.addSlave(2);
	 * Screen4.removeSlave(1); } } } catch (IOException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } try { sendMessage =
	 * keyRead.readLine(); } catch (IOException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } pwrite.println(sendMessage);
	 * System.out.flush(); }
	 */
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

				if (clientAddr == null)
					return;

				String ip = clientAddr.split(":")[0];
				String port = clientAddr.split(":")[1];
				s = new Socket(ip, Integer.parseInt(port));
				ObjectOutputStream os = new ObjectOutputStream(
						s.getOutputStream());
				os.writeObject(m);
				ObjectInputStream is = new ObjectInputStream(s.getInputStream());
				ETLMessage responseMsg = (ETLMessage) is.readObject();
				if (responseMsg.getType() != MessageType.MsgOK)
					throw new RuntimeException("MSG ERROR 01");
				System.out
						.println("msg sent to slave " + ip + " " + port + ".");
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
				ETLMessage response = new ETLMessage(MessageType.MsgOK, null,
						null);
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
					Map<Integer, String> slaveMap = (Map<Integer, String>) msg
							.getObj();
					System.out.println("================");
					for (int k : slaveMap.keySet()) {
						System.out.println(slaveMap.get(k));
					}
				}

				if (type == MessageType.MsgNewMaster) {
					String addr = (String) msg.getObj();
					System.out.println("Get Msg new Master");

					ETLMessage m = new ETLMessage(MessageType.MsgNewJobClient,
							"resources/conf.yaml", addr + ":11111");

					MsgDispatcherClient dispatcher = new MsgDispatcherClient(m,
							addr + ":12345");
					dispatcher.start();

				}
			} catch (Exception e) {

			}
		}

	}
}