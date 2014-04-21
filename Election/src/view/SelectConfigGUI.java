package view;

import java.awt.Dimension;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import utils.General;

public class SelectConfigGUI extends JPanel{
	private JButton newButton;
	private JButton startButton;
	private JList configList;
	private JFileChooser configChooser;
	private MouseListener listener; 
	
	public SelectConfigGUI() {
		newButton = new JButton(Constants.BUTTON_NEW_TEXT);
		newButton.setSize(Constants.NEW_BUTTON_WIDTH, Constants.NEW_BUTTON_HEIGHT);
		startButton = new JButton(Constants.BUTTON_START_TEXT);
		startButton.setSize(Constants.START_BUTTON_WIDTH, Constants.START_BUTTON_HEIGHT);
		configList = new JList(Constants.DATATYPE_OPTION);
		configList.setSize(Constants.CONFIG_LIST_WIDTH, Constants.CONFIG_LIST_HEIGHT);
		configList.setBorder(BorderFactory.createTitledBorder(Constants.LIST_CONFIG_LABEL));
		configChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
		    "YAML Config File", "yaml");
		configChooser.setFileFilter(filter);

//		newButton.addMouseListener(new MouseAdapter() {
//
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				// TODO Auto-generated method stub
//				super.mouseClicked(e);
//				SelectConfigGUI.this.setVisible(false);
//				SelectConfigGUI.this.parent.add(new GeneralConfigGUI());
//			}
//		});
		

		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(newButton);
		this.add(configList);
		this.add(startButton);
		this.add(Box.createRigidArea(new Dimension(0, Constants.CENTER_HEIGHT*2/3)));
	}
	
	public void addNewButtonClickListener(MouseListener listener) {
		newButton.addMouseListener(listener);
	}
	
//	public void temp() {
//		int returnVal = configChooser.showOpenDialog(this);
//		if(returnVal == JFileChooser.APPROVE_OPTION) {
//		   General.log("You chose to open this file: " +
//		        configChooser.getSelectedFile().getName());
//		}
//	}
	
	public static void main(String[] args) {
		WindowGUI gui = new WindowGUI();
		gui.add(new SelectConfigGUI());
		gui.setVisible(true);
	}
}
