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
	
	public static ClientConfigDataType getDataType(String dataType) {
		ClientConfigDataType type = null;
		if (dataType.equals("HTTP")) {
			type =  HTTP;
		} else if (dataType.equals("MYSQL")) {
			type =  MYSQL;
		}
		return type;
	}
}
