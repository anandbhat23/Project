package client;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import view.GeneralConfigGUI;
import view.SelectConfigGUI;
import view.WindowGUI;

public class ClientController {
	private WindowGUI gui;
	private GeneralConfigGUI generalConfigGUI;
	private SelectConfigGUI selectConfigGUI;
	
	public ClientController() {
		gui = new WindowGUI();
		generalConfigGUI = new GeneralConfigGUI();
		selectConfigGUI = new SelectConfigGUI();
		gui.add(selectConfigGUI);
		
		/**
		 * handler for clicks on new button to create ETL config file
		 */
		selectConfigGUI.addNewButtonClickListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				selectConfigGUI.setVisible(false);
				gui.remove(selectConfigGUI);
				gui.add(generalConfigGUI);
				gui.setVisible(true);
			}
			
		});
		
		/**
		 * handler for clicks on submit button to submit ETL config file
		 */
		generalConfigGUI.addSubmitButtonListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				saveConfig();
				gui.remove(generalConfigGUI);
				gui.add(selectConfigGUI);
				gui.setVisible(true);
			}
		});
		
	}
	
	/**
	 * method to save user input config parameters into object / save to file
	 */
	public void saveConfig(){
		
	}
	
	public void run() {
		gui.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		ClientController clientController = new ClientController();
		clientController.run();
	}
}
