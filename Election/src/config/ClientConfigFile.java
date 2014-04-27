package config;

import java.util.HashMap;
import java.util.Map;

public class ClientConfigFile {
	private String configFileName;
	private Map<String, ClientConfigImporter> clientImporterList;
	private String transformerScript;
	private ClientConfigExporter clientExporter;
	
	public ClientConfigFile() {
		this.clientImporterList = new HashMap<String, ClientConfigImporter>();
	}
	
	public ClientConfigFile(String configFileName, ClientConfigDataType importerType, ClientConfigDataType exporterType) {
		this.configFileName = configFileName;
		this.clientImporterList = new HashMap<String, ClientConfigImporter>();
		this.clientExporter = ClientConfigFileFactory.createClientExporter(exporterType);
	}
	
	
	public void setConfigFileName(String configFileName) {
		this.configFileName = configFileName;
	}
	
	public void setTransformerScript(String transformerScript) {
		this.transformerScript = transformerScript;
	}
	
	public void setClientExporter(ClientConfigExporter clientExporter) {
		this.clientExporter = clientExporter;
	}
	
	public String getConfigFileName() {
		return configFileName;
	}
	
	public void addClientImporter(String importerName, ClientConfigImporter importer) {
		clientImporterList.put(importerName, importer);
	}
	
	public String getTransformerScript() {
		return transformerScript;
	}
	
	public ClientConfigImporter getClientImporter(String importerName) {
		return clientImporterList.get(importerName);
	}
	
	public ClientConfigExporter getClientExporter() {
		return clientExporter;
	}
	
	
}
