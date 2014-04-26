package client;

import javax.swing.DefaultListModel;

public class ClientModel {
	private DefaultListModel configListModel;
	
	public ClientModel() {
		configListModel = new DefaultListModel();
	}
	
	public DefaultListModel getConfigListModel() {
		return configListModel;
	}
}
