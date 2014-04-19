package config;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

	private Map<String, ConnectionParameters> connections = new HashMap<String, ConnectionParameters>();
	private String serverId;

	public Configuration() {
		super();
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public Map<String, ConnectionParameters> getConnections() {
		return connections;
	}

	public void addConnections(String local_name,
			ConnectionParameters connectionParameters) {
		connections.put(local_name, connectionParameters);
	}
}
