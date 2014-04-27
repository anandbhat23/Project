package config;

public enum ClientConfigDataType {
	HTTP("HTTP"),
	MYSQL("MYSQL");
	
	private String type;
	
	private ClientConfigDataType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}
