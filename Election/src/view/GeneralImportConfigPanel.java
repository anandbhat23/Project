package view;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import config.ClientConfigDataType;
import config.ClientConfigFileFactory;
import config.ClientConfigImporter;

public abstract class GeneralImportConfigPanel extends JPanel{
	private JList importerList;
	private JScrollPane importerScrollPane;
	private JPanel basicInfoPanel;
	private JTextField importerName;
	private JComboBox dataTypeList;
	private JTextField dataLocationField;
	private JButton addImporterButton;
	private Integer panelWidth;
	private Integer panelHeight;
	private ClientConfigDataType dataType;
	protected ClientConfigImporter clientConfigImporter;
	
	public GeneralImportConfigPanel(DefaultListModel importListModel) {
		panelWidth = super.getWidth();
		panelHeight = super.getHeight();
		importerList = new JList(importListModel);
		importerScrollPane = new JScrollPane(importerList);
		basicInfoPanel = new JPanel();
		importerName = new JTextField();
		dataTypeList = new JComboBox(ClientConfigDataType.values());
		dataLocationField = new JTextField();
		addImporterButton = new JButton(Constants.ADD_LABEL);
		setDetailConfigGUI();
		createGeneralImportConfigPanel();
	}
	
	public GeneralImportConfigPanel(Integer panelWidth, Integer panelHeight) {
		this.panelWidth = panelWidth;
		this.panelHeight = panelHeight;
		basicInfoPanel = new JPanel();
		importerName = new JTextField();
		dataLocationField = new JTextField();
		setDetailConfigGUI();
		createGeneralImportConfigPanel();
	}
	
	public Integer getPanelWidth() {
		return panelWidth;
	}
	
	public Integer getPanelHeight() {
		return panelHeight;
	}
	
	public JPanel getBasicInfoPanel() {
		return basicInfoPanel;
	}
	
	
	public JTextField getDataLocationField() {
		return dataLocationField;
	}
	
	public void setDetailConfigGUI() {
		importerScrollPane.setBorder(BorderFactory.createTitledBorder(Constants.IMPORTER_LIST_LABEL));
		importerName.setBorder(BorderFactory.createTitledBorder(Constants.IMPORTER_NAME_LABEL));
		dataTypeList.setBorder(BorderFactory.createTitledBorder(Constants.DATA_TYPE_LABEL));
		dataLocationField.setBorder(BorderFactory.createTitledBorder(Constants.DATA_LOCATION_LABEL));
		
		importerName.setColumns(20);
		dataLocationField.setColumns(20);
		
		dataTypeList.setEditable(false);
		dataTypeList.setSelectedItem(ClientConfigDataType.MYSQL);
		
		GridBagConstraints c = new GridBagConstraints();
		
		basicInfoPanel.setLayout(new GridBagLayout());
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		basicInfoPanel.add(importerName, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.gridheight = 2;
		basicInfoPanel.add(dataLocationField, c);
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 3;
		c.gridheight = 1;
		basicInfoPanel.add(dataTypeList,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 4;
		c.gridheight = 1;
		basicInfoPanel.add(addImporterButton, c);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
	}
	
	public void createGeneralImportConfigPanel() {
		this.add(importerScrollPane);
		this.add(basicInfoPanel);
	}
	
	public void setDataType(ClientConfigDataType dataType) {
		this.dataType = dataType;
	}
	
	public ClientConfigDataType getDataType() {
		return dataType;
	}
	
	public JList getImporterList () {
		return importerList;
	}
	
	public JButton getAddImporterButton (){
		return addImporterButton;
	}
	
	public String getDataLocation() {
		return dataLocationField.getText();
	}
	
	public String getImporterName() {
		return importerName.getText();
	}
	
//	public void createClientConfigImporter(ClientConfigDataType dataType) {
//		clientConfigImporter = ClientConfigFileFactory.createClientImporter(dataType);
//	}
	
	public abstract void setClientConfigImporter();
	
	public ClientConfigImporter getClientConfigImporter() {
		return clientConfigImporter;
	}
	
	public void resetImporterName() {
		importerName.setText("");
	}
	
	public void resetDataLocation() {
		dataLocationField.setText("");
	}
	
	public void addDataTypeListActionListener(ItemListener listener) {
//		dataTypeList.addActionListener(listener);
		dataTypeList.addItemListener(listener);
	}
	
	public abstract void resetImporterConfigGUI();
}
