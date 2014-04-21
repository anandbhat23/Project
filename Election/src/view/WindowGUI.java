package view;

import java.awt.BorderLayout;
import java.awt.MenuBar;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class WindowGUI extends JFrame{
	private MenuGUI menuBar;
	
	public WindowGUI() {
		menuBar = new MenuGUI();
		this.setTitle(Constants.LABEL_APP);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		this.setMenuBar(menuBar);
		this.setLayout(new BorderLayout());
//		this.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		WindowGUI gui = new WindowGUI();
		gui.setVisible(true);
	}
}
