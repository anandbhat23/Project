package config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientConfigFile {
	private String configFileName;
	private Map<String, ClientConfigImporter> clientImporterList;
	private ClientConfigExporter clientExporter;
	
	public ClientConfigFile(String configFileName, ClientConfigDataType importerType, ClientConfigDataType exporterType) {
		this.configFileName = configFileName;
		this.clientImporterList = new HashMap<String, ClientConfigImporter>();
		this.clientExporter = ClientConfigFileFactory.createClientExporter(exporterType);
	}
	
	
	public String getConfigFileName() {
		return configFileName;
	}
	
	public void addClientImporter(String importerName, ClientConfigImporter importer) {
		clientImporterList.put(importerName, importer);
	}
	
	public ClientConfigImporter getClientImporter(String importerName) {
		return clientImporterList.get(importerName);
	}
	
	public ClientConfigExporter getClientExporter() {
		return clientExporter;
	}
	
	
}
