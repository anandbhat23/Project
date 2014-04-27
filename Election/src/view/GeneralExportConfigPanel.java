package view;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import config.ClientConfigDataType;

public abstract class GeneralExportConfigPanel extends JPanel{
	private JPanel basicInfoPanel;
	private JComboBox dataTypeList;
	private JTextField dataLocationField;
	private Integer panelWidth;
	private Integer panelHeight;
	
	
	public GeneralExportConfigPanel() {
		panelWidth = super.getWidth();
		panelHeight = super.getHeight();
		basicInfoPanel = new JPanel();
		dataTypeList = new JComboBox(ClientConfigDataType.values());
		dataLocationField = new JTextField();
		setDetailConfigGUI();
		createView();
	}
	
	public GeneralExportConfigPanel(Integer panelWidth, Integer panelHeight) {
		this.panelWidth = panelWidth;
		this.panelHeight = panelHeight;
		basicInfoPanel = new JPanel();
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
		dataTypeList.setBorder(BorderFactory.createTitledBorder(Constants.DATA_TYPE_LABEL));
		dataLocationField.setBorder(BorderFactory.createTitledBorder(Constants.DATA_LOCATION_LABEL));
		
		dataLocationField.setColumns(20);
		
		dataTypeList.setEditable(false);
		
		GridBagConstraints c = new GridBagConstraints();
		basicInfoPanel.setLayout(new GridBagLayout());
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		basicInfoPanel.add(dataLocationField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.gridheight = 1;
		basicInfoPanel.add(dataTypeList,c);
		
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
	}
	
	public void createView() {
		this.add(basicInfoPanel);
	}
	
}
