package config;

public abstract class ClientConfigImporter {
	private ClientConfigDataType dataType;
	private String dataLocation;

	public ClientConfigDataType getImporterType() {
		return dataType;
	}
	public String getLocation() {
		return dataLocation;
	}
	
	public void setImporterType(ClientConfigDataType dataType){ 
		this.dataType = dataType;
	}
	
	public void setLocation(String dataLocation) {
		this.dataLocation = dataLocation;
	}
}
