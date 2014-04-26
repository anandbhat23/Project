package view;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

public abstract class DetailConfigPanel extends JPanel{
	private JPanel basicInfoPanel;
	private JTextField dataTypeField;
	private JTextField dataLocationField;
	private Integer panelWidth;
	private Integer panelHeight;
	
	
	public DetailConfigPanel() {
		panelWidth = super.getWidth();
		panelHeight = super.getHeight();
		basicInfoPanel = new JPanel();
		dataTypeField = new JTextField();
		dataLocationField = new JTextField();
		setDetailConfigGUI();
		createView();
	}
	
	public DetailConfigPanel(Integer panelWidth, Integer panelHeight) {
		this.panelWidth = panelWidth;
		this.panelHeight = panelHeight;
		basicInfoPanel = new JPanel();
		dataTypeField = new JTextField();
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
	
	public JTextField getDataTypeField() {
		return dataTypeField;
	}
	
	public JTextField getDataLocationField() {
		return dataLocationField;
	}
	
	public void setDetailConfigGUI() {
		dataTypeField.setBorder(BorderFactory.createTitledBorder(Constants.DATA_TYPE_LABEL));
		dataLocationField.setBorder(BorderFactory.createTitledBorder(Constants.DATA_LOCATION_LABEL));
		
		basicInfoPanel.setLayout(new BorderLayout(Constants.GAP_PADDING_HEIGHT, Constants.GAP_PADDING_WIDTH));
		basicInfoPanel.add(dataTypeField, BorderLayout.NORTH);
		basicInfoPanel.add(dataLocationField, BorderLayout.SOUTH);
		this.setLayout(new BorderLayout());
	}
	
	public void createView() {
		this.add(basicInfoPanel, BorderLayout.WEST);
	}
}
