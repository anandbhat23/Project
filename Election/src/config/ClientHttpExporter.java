package config;

public class ClientHttpExporter extends ClientConfigExporter{
	public ClientHttpExporter() {
		this.setExporterType(ClientConfigDataType.HTTP);
	}
	
	public ClientHttpExporter(String dataLocation) {
		this.setExporterType(ClientConfigDataType.HTTP);
		this.setLocation(dataLocation);
	}
}
