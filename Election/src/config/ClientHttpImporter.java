package config;

public class ClientHttpImporter extends ClientConfigImporter {
	
	public ClientHttpImporter() {
		this.setImporterType(ClientConfigDataType.HTTP);
	}
	
	public ClientHttpImporter(String dataLocation) {
		this.setImporterType(ClientConfigDataType.HTTP);
		this.setLocation(dataLocation);
	}
	
}
