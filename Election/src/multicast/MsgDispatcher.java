package multicast;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map.Entry;

import config.Configuration;
import config.ConnectionParameters;
import config.MessageType;
import core.Message;

public class MsgDispatcher extends Thread {
	private Message m;
	private Configuration configuration;
	private int type = 0;//Multicast Default
	private String destination;
	
	public MsgDispatcher(Message m, Configuration configuration) {
		this.m = m;
		type = 0;
		this.configuration = configuration;
	}
	
	public MsgDispatcher(Message m, Configuration configuration, String destination) {
		this.m = m;
		this.type = 1;//Unicast
		this.destination = destination;
		this.configuration = configuration;
	}

	public void run() {
		Socket s;
		if(type == 0){
			
				for (Entry<String, ConnectionParameters> connection : configuration
						.getConnections().entrySet()) {
					try{
					if (!connection.getKey().equalsIgnoreCase(
							configuration.getServerId())) {
						String ip = connection.getValue().getIP();
						int port = connection.getValue().getPort();
						s = new Socket(ip, port);
						ObjectOutputStream os = new ObjectOutputStream(
								s.getOutputStream());
						os.writeObject(m);
						System.out.println("Sending Msg " + m.getType().name() + " to " + connection.getKey());
						os.flush();
						ObjectInputStream is = new ObjectInputStream(
								s.getInputStream());
						Message responseMsg = (Message) is.readObject();
						if (responseMsg.getType() != MessageType.MsgOK)
							throw new RuntimeException("MSG ERROR");
						s.close();
						}
					}catch(Exception e){
						
					}
				}
			}else if(type == 1){
				try{
				String ip = configuration.getConnections().get(destination).getIP();
				int port = configuration.getConnections().get(destination).getPort();
				s = new Socket(ip, port);
				ObjectOutputStream os = new ObjectOutputStream(
						s.getOutputStream());
				System.out.println("Sending MsgAnswer to " + destination);
				os.writeObject(m);
				os.flush();
				ObjectInputStream is = new ObjectInputStream(
						s.getInputStream());
				Message responseMsg = (Message) is.readObject();
				if (responseMsg.getType() != MessageType.MsgOK)
					throw new RuntimeException("MSG ERROR");
				s.close();}
				catch(Exception e){
					
				}
			}
	}
	
	
}