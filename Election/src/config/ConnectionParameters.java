package config;

public class ConnectionParameters {
	private String IP;
	private int port;
	
	public ConnectionParameters(Object IP, Object port) {
		this.IP = (String)IP;
		this.port = ((Integer)port).intValue();
	}
	public String getIP() {
		return IP;
	}
	public int getPort() {
		return port;
	}
}
