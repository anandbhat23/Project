package client;

import javax.swing.DefaultListModel;

public class ClientModel {
	private DefaultListModel configListModel;
	private DefaultListModel importListModel;
	
	public ClientModel() {
		configListModel = new DefaultListModel();
		importListModel = new DefaultListModel();
	}
	
	public DefaultListModel getConfigListModel() {
		return configListModel;
	}
	
	public DefaultListModel getImportListModel() {
		return importListModel;
	}
}
