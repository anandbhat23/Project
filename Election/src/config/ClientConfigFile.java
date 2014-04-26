package config;

public class ClientConfigFile {
	private String configFileName;
	private ClientConfigImporter clientImporter;
	private ClientConfigExporter clientExporter;
	
	public ClientConfigFile(String configFileName, ClientConfigDataType importerType, ClientConfigDataType exporterType) {
		this.configFileName = configFileName;
		this.clientImporter = ClientConfigFileFactory.createClientImporter(importerType);
		this.clientExporter = ClientConfigFileFactory.createClientExporter(exporterType);
	}
	
	public String getConfigFileName() {
		return configFileName;
	}
	
	public ClientConfigImporter getClientImporter() {
		return clientImporter;
	}
	
	public ClientConfigExporter getClientExporter() {
		return clientExporter;
	}
	
	
}
