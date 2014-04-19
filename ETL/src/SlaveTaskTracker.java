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

import common.Parser;
import common.ParserFactory;

import core.ETLJob;
import core.Exporter;
import core.Importer;

public class SlaveTaskTracker {

	String masterAddr;
	int port;
	String slaveAddr;
	List<String> slaveList;
	int slaveid;

	public SlaveTaskTracker(int port, int slaveid) {
		try {
			this.port = port;
			this.slaveid = slaveid;
			String masterIp = InetAddress.getLocalHost().getHostAddress();
			this.slaveAddr = InetAddress.getLocalHost().getHostAddress() + ":"
					+ port;
			masterAddr = masterIp + ":12345";

			slaveList = new ArrayList<String>();
			slaveList.add(InetAddress.getLocalHost().getHostAddress()
					+ ":15619");
			slaveList.add(InetAddress.getLocalHost().getHostAddress()
					+ ":15719");

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
			ETLMessage m = new ETLMessage(MessageType.MsgHeartBeat, slaveid,
					null);
			sendMsgToMaster(m);
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
					System.out.println("get Msg TaskStart");
					TaskStart(task);
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
				parser = ParserFactory.getParser(protoMessage
						.getExporter().getType());
				Exporter exporter = parser
						.createExporterFromProtoMessage(protoMessage
								.getExporter().getAllFields());
				job = new ETLJob(importer, exporter);
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
				job.getExporter().export(data);

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
		MsgDispatcher dispatcher = new MsgDispatcher(m, masterAddr);
		dispatcher.start();
	}

	/*
	 * private void sendMsgToSlave(Message m, String slave) { MsgDispatcher
	 * dispatcher = new MsgDispatcher(m, slave); dispatcher.start(); }
	 */

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
				e.printStackTrace();
			}
		}

	}

}
