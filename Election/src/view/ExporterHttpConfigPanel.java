package view;

import config.ClientConfigDataType;

public class ExporterHttpConfigPanel extends GeneralExportConfigPanel{
	public ExporterHttpConfigPanel() {
		this.setDataType(ClientConfigDataType.HTTP);
		this.createClientConfigExporter(ClientConfigDataType.HTTP);
	}

	@Override
	public void setClientConfigExporter() {
		// TODO Auto-generated method stub
		clientConfigExporter.setExporterType(ClientConfigDataType.HTTP);
		clientConfigExporter.setLocation(this.getDataLocation());
	}

	@Override
	public void resetExporterConfigGUI() {
		// TODO Auto-generated method stub
		this.resetDataLocation();
	}
}
