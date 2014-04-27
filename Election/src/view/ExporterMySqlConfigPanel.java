package view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ExporterMySqlConfigPanel extends GeneralExportConfigPanel{
	private JPanel detailInfoPanel;
	private JPanel detailLeftPanel;
	private JPanel accountInfoPanel;
	private JPanel tableBasicInfoPanel;
	private JTextField userNameTextField;
	private JTextField passwordTextField;
	private JTextField tableNameTextField;
	private JTextArea columnsTextArea;
	private JScrollPane columnScrollPane;
		
	public ExporterMySqlConfigPanel() {
		detailInfoPanel = new JPanel();
		detailLeftPanel = new JPanel();
		accountInfoPanel = new JPanel();
		tableBasicInfoPanel = new JPanel();
		
		userNameTextField = new JTextField();
		passwordTextField = new JTextField();
		tableNameTextField = new JTextField();
		columnsTextArea = new JTextArea();
		columnScrollPane = new JScrollPane(columnsTextArea);
		setMySqlDetailConfigGUI();
		createMySqlView();
	}
	
	public void setMySqlDetailConfigGUI() {
		userNameTextField.setBorder(BorderFactory.createTitledBorder(Constants.USER_NAME_LABEL));
		passwordTextField.setBorder(BorderFactory.createTitledBorder(Constants.PASSWORD_LABEL));
		tableNameTextField.setBorder(BorderFactory.createTitledBorder(Constants.TABLE_NAME_LABEL));
		columnScrollPane.setBorder(BorderFactory.createTitledBorder(Constants.COLUMN_LABEL));
		userNameTextField.setColumns(20);
		
		passwordTextField.setColumns(20);
		tableNameTextField.setColumns(20);
		
		columnsTextArea.setRows(11);
		columnsTextArea.setColumns(20);
		
		GridBagConstraints c = new GridBagConstraints();
		
		accountInfoPanel.setLayout(new BorderLayout());
		accountInfoPanel.add(userNameTextField, BorderLayout.NORTH);
		accountInfoPanel.add(passwordTextField, BorderLayout.CENTER);
		
		tableBasicInfoPanel.setLayout(new BorderLayout());
		tableBasicInfoPanel.add(tableNameTextField, BorderLayout.CENTER);
		
		detailLeftPanel.setLayout(new BorderLayout());
		detailLeftPanel.add(accountInfoPanel, BorderLayout.NORTH);
		detailLeftPanel.add(tableBasicInfoPanel, BorderLayout.CENTER);
		
		detailInfoPanel.setLayout(new GridBagLayout());
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		detailInfoPanel.add(detailLeftPanel,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		detailInfoPanel.add(columnScrollPane, c);
		
		
	}
	
	public void createMySqlView() {
		this.add(detailInfoPanel);
	}
	
	public static void main(String args[]) {
		WindowGUI gui = new WindowGUI();
		gui.setContentPane(new ExporterMySqlConfigPanel());
		gui.setVisible(true);
	}
}
