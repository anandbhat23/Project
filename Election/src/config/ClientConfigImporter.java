package config;

public abstract class ClientConfigImporter {
	private ClientConfigDataType dataType;
	private String dataLocation;
	private String importerName;

	public ClientConfigDataType getImporterType() {
		return dataType;
	}
	public String getLocation() {
		return dataLocation;
	}
	
	public String getImporterName() {
		return importerName;
	}
	
	public void setImporterType(ClientConfigDataType dataType){ 
		this.dataType = dataType;
	}
	
	public void setLocation(String dataLocation) {
		this.dataLocation = dataLocation;
	}
	
	public void setImporterName(String importerName) {
		this.importerName = importerName;
	}
}
