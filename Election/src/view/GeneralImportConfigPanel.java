package view;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import config.ClientConfigDataType;

public abstract class GeneralImportConfigPanel extends JPanel{
	private JList importerList;
	private JScrollPane importerScrollPane;
	private JPanel basicInfoPanel;
	private JTextField importerExporterName;
	private JComboBox dataTypeList;
	private JTextField dataLocationField;
	private JButton addImporterButton;
	private Integer panelWidth;
	private Integer panelHeight;
	
	
	public GeneralImportConfigPanel() {
		panelWidth = super.getWidth();
		panelHeight = super.getHeight();
		importerList = new JList();
		importerScrollPane = new JScrollPane(importerList);
		basicInfoPanel = new JPanel();
		importerExporterName = new JTextField();
		dataTypeList = new JComboBox(ClientConfigDataType.values());
		dataLocationField = new JTextField();
		addImporterButton = new JButton(Constants.ADD_LABEL);
		setDetailConfigGUI();
		createView();
	}
	
	public GeneralImportConfigPanel(Integer panelWidth, Integer panelHeight) {
		this.panelWidth = panelWidth;
		this.panelHeight = panelHeight;
		basicInfoPanel = new JPanel();
		importerExporterName = new JTextField();
		dataLocationField = new JTextField();
		setDetailConfigGUI();
		createView();
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
		importerExporterName.setBorder(BorderFactory.createTitledBorder(Constants.IMPORTER_NAME_LABEL));
		dataTypeList.setBorder(BorderFactory.createTitledBorder(Constants.DATA_TYPE_LABEL));
		dataLocationField.setBorder(BorderFactory.createTitledBorder(Constants.DATA_LOCATION_LABEL));
		
		importerExporterName.setColumns(20);
		dataLocationField.setColumns(20);
		
		dataTypeList.setEditable(false);
		
		GridBagConstraints c = new GridBagConstraints();
		
		basicInfoPanel.setLayout(new GridBagLayout());
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		basicInfoPanel.add(importerExporterName, c);
		
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
	
	public void createView() {
		this.add(importerScrollPane);
		this.add(basicInfoPanel);
	}
	
//	public void addAddImporterButtonListener(MouseListener listener){
//		addImporterButton.addMouseListener(listener);
//	}
	
	public JButton getAddImporterButton (){
		return addImporterButton;
	}
}
