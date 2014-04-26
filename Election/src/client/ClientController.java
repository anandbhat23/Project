package client;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import com.sun.corba.se.impl.orbutil.closure.Constant;

import view.GeneralConfigGUI;
import view.SelectConfigGUI;
import view.WindowGUI;

public class ClientController implements Runnable {
	private WindowGUI gui;
	private GeneralConfigGUI generalConfigGUI;
	private SelectConfigGUI selectConfigGUI;
	private ClientModel clientModel;
	
	public ClientController() {
		gui = new WindowGUI(Constants.SELECT_CONFIG_GUI_WIDTH, Constants.SELECT_CONFIG_GUI_HEIGHT);
		clientModel = new ClientModel();
		generalConfigGUI = new GeneralConfigGUI();
		selectConfigGUI = new SelectConfigGUI(clientModel.getConfigListModel(), Constants.SELECT_CONFIG_GUI_WIDTH,  Constants.SELECT_CONFIG_GUI_HEIGHT);
		
	}
	
	/**
	 * method to save user input config parameters into object / save to file
	 */
	public void saveConfig(){
		
	}
	
	public void addListeners() {
		/**
		 * handler for clicks on new button to create ETL config file
		 */
		selectConfigGUI.getSelectConfigButtonPanel().addNewButtonClickListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				selectConfigGUI.setVisible(false);
				
				// adjust window size
				gui.setSize(Constants.GENERAL_CONFIG_GUI_WIDTH, Constants.GENERAL_CONFIG_GUI_HEIGHT);
				// add new content
				gui.setContentPane(generalConfigGUI);
				gui.setLocationRelativeTo(null);
				// make screen visible
				generalConfigGUI.setVisible(true);
				
			}
			
		});
		
		/**
		 * handler for clicks on submit button to submit ETL config file
		 */
		generalConfigGUI.addSubmitButtonListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				saveConfig();
				generalConfigGUI.setVisible(false);
				gui.setSize(Constants.SELECT_CONFIG_GUI_WIDTH, Constants.SELECT_CONFIG_GUI_HEIGHT);
				gui.setContentPane(selectConfigGUI);
				selectConfigGUI.setVisible(true);
			}
		});
	}
	
	public void run() {
		addListeners();
		gui.setContentPane(selectConfigGUI);
		gui.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new ClientController());
	}
}
