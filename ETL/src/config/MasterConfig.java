package config;

public class MasterConfig {

	private long heartbeat = System.currentTimeMillis();
	private String id = null;
	
	public long getHeartbeat() {
		return heartbeat;
	}
	public void setHeartbeat(long heartbeat) {
		this.heartbeat = heartbeat;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean amMaster(String serverId){
		if(id != null){
			return id.equalsIgnoreCase(serverId);
		}
		return false;
	}
	
}
