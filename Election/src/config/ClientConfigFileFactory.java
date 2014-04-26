package config;


public class ClientConfigFileFactory {
	public static ClientConfigImporter createClientImporter(ClientConfigDataType type) {
		ClientConfigImporter clientImporter = null;
		if (type.equals(ClientConfigDataType.HTTP)) {
			clientImporter =  new ClientHttpImporter();
		} else if (type.equals(ClientConfigDataType.MYSQL)) {
			clientImporter =  new ClientMySqlImporter();
		}
		return clientImporter;
	}
	
	public static ClientConfigExporter createClientExporter(ClientConfigDataType type) {
		ClientConfigExporter clientExporter = null;
		if (type.equals(ClientConfigDataType.HTTP)) {
			clientExporter =  new ClientHttpExporter();
		} else if (type.equals(ClientConfigDataType.MYSQL)) {
			clientExporter =  new ClientMySqlExporter();
		}
		return clientExporter;
	}
}
