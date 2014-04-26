package view;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class ImporterMySqlConfigGUI extends DetailConfigPanel{
	private JPanel detailInfoPanel;
	private JPanel detailLeftPanel;
	private JPanel detailRightPanel;
	private JPanel accountInfoPanel;
	private JPanel tableBasicInfoPanel;
	private JPanel tableColumnInfoPanel;
	private JTextField userNameTextField;
	private JTextField passwordTextField;
	private JTextField tableNameTextField;
	private JTextField rowStartTextField;
	private JTextField rowEndTextField;
	private JTextArea columnsTextArea;
		
	public ImporterMySqlConfigGUI() {
		detailInfoPanel = new JPanel();
		detailLeftPanel = new JPanel();
		detailRightPanel = new JPanel();
		accountInfoPanel = new JPanel();
		tableBasicInfoPanel = new JPanel();
		tableColumnInfoPanel = new JPanel();
		
		userNameTextField = new JTextField();
		passwordTextField = new JTextField();
		tableNameTextField = new JTextField();
		rowStartTextField = new JTextField();
		rowEndTextField = new JTextField();
		columnsTextArea = new JTextArea();
		setMySqlDetailConfigGUI();
		createMySqlView();
	}
	
	public void setMySqlDetailConfigGUI() {
		userNameTextField.setBorder(BorderFactory.createTitledBorder(Constants.USER_NAME_LABEL));
		passwordTextField.setBorder(BorderFactory.createTitledBorder(Constants.PASSWORD_LABEL));
		tableNameTextField.setBorder(BorderFactory.createTitledBorder(Constants.TABLE_NAME_LABEL));
		rowStartTextField.setBorder(BorderFactory.createTitledBorder(Constants.ROW_START_LABEL));
		rowEndTextField.setBorder(BorderFactory.createTitledBorder(Constants.ROW_END_LABEL));
		columnsTextArea.setBorder(BorderFactory.createTitledBorder(Constants.COLUMN_LABEL));
		
		accountInfoPanel.setLayout(new BorderLayout());
		accountInfoPanel.add(userNameTextField, BorderLayout.NORTH);
		accountInfoPanel.add(passwordTextField, BorderLayout.SOUTH);
		
		tableBasicInfoPanel.setLayout(new BorderLayout());
		tableBasicInfoPanel.add(tableNameTextField, BorderLayout.NORTH);
		tableBasicInfoPanel.add(rowStartTextField, BorderLayout.CENTER);
		tableBasicInfoPanel.add(rowEndTextField, BorderLayout.SOUTH);
		
		detailLeftPanel.setLayout(new BorderLayout());
		detailLeftPanel.add(accountInfoPanel, BorderLayout.NORTH);
		detailLeftPanel.add(tableBasicInfoPanel, BorderLayout.SOUTH);
		
		detailInfoPanel.setLayout(new BorderLayout());
		detailInfoPanel.add(detailLeftPanel, BorderLayout.WEST);
		detailInfoPanel.add(columnsTextArea, BorderLayout.EAST);
		
	}
	
	public void createMySqlView() {
		this.add(detailInfoPanel, BorderLayout.EAST);
	}
	
	public static void main(String args[]) {
		WindowGUI gui = new WindowGUI();
		gui.setContentPane(new ImporterMySqlConfigGUI());
		gui.setVisible(true);
	}
}
