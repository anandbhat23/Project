package config;

public abstract class ClientConfigExporter {
	private ClientConfigDataType dataType;
	private String dataLocation;

	public ClientConfigDataType getExporterType() {
		return dataType;
	}
	public String getLocation() {
		return dataLocation;
	}
	
	public void setExporterType(ClientConfigDataType dataType){ 
		this.dataType = dataType;
	}
	
	public void setLocation(String dataLocation) {
		this.dataLocation = dataLocation;
	}
}
