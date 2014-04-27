package view;

import javax.swing.DefaultListModel;

import config.ClientConfigDataType;

public class ImporterHttpConfigPanel extends GeneralImportConfigPanel{

	public ImporterHttpConfigPanel(DefaultListModel importListModel) {
		super(importListModel);
//		this.createClientConfigImporter(ClientConfigDataType.HTTP);
		this.setDataType(ClientConfigDataType.HTTP);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void setClientConfigImporter() {
		this.clientConfigImporter.setImporterName(this.getImporterName());
		this.clientConfigImporter.setImporterType(ClientConfigDataType.HTTP);
		this.clientConfigImporter.setLocation(this.getDataLocation());
	}


	@Override
	public void resetImporterConfigGUI() {
		// TODO Auto-generated method stub
		this.resetImporterName();
		this.resetDataLocation();
	}
	
}
