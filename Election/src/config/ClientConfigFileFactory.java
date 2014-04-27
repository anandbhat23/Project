package config;


public class ClientConfigFileFactory {
	public static ClientConfigImporter createClientImporter(ClientConfigDataType type) {
		ClientConfigImporter clientImporter = null;
		if (type.equals(ClientConfigDataType.HTTP)) {
			clientImporter =  new ClientHttpImporter();
			clientImporter.setImporterType(type);
		} else if (type.equals(ClientConfigDataType.MYSQL)) {
			clientImporter =  new ClientMySqlImporter();
			clientImporter.setImporterType(type);
		}
		return clientImporter;
	}
	
	public static ClientConfigExporter createClientExporter(ClientConfigDataType type) {
		ClientConfigExporter clientExporter = null;
		if (type.equals(ClientConfigDataType.HTTP)) {
			clientExporter =  new ClientHttpExporter();
			clientExporter.setExporterType(type);
		} else if (type.equals(ClientConfigDataType.MYSQL)) {
			clientExporter =  new ClientMySqlExporter();
			clientExporter.setExporterType(type);
		}
		return clientExporter;
	}
}
