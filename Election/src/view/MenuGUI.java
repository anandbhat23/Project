package view;

import java.awt.Menu;
import java.awt.MenuBar;
import javax.swing.JFrame;

public class MenuGUI extends MenuBar{
	private Menu fileMenu;
	private Menu helpMenu;
	
	public MenuGUI() {
		fileMenu = new Menu(Constants.MENU_FILE);
		fileMenu.add(Constants.MENU_FILE_NEW);
		helpMenu = new Menu(Constants.MENU_HELP);
		helpMenu.add(Constants.MENU_HELP_HELP);
		helpMenu.add(Constants.MENU_HELP_ABOUT);
		this.add(fileMenu);
		this.add(helpMenu);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setMenuBar(new MenuGUI());
		frame.setVisible(true);
	}
}
