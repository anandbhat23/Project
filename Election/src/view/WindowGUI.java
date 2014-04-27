package view;

import java.awt.BorderLayout;
import java.awt.MenuBar;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class WindowGUI extends JFrame{
	private MenuGUI menuBar;
	private Integer windowWidth;
	private Integer windowHeight;
	
	public WindowGUI() {
		menuBar = new MenuGUI();
		windowWidth = Constants.WINDOW_WIDTH;
		windowHeight = Constants.WINDOW_HEIGHT;
		setWindow();
	}
	
	public WindowGUI(Integer width, Integer height) {
		menuBar = new MenuGUI();
		this.windowWidth = width;
		this.windowHeight = height;
		setWindow();
	}
	
	private void setWindow(){	
		this.setTitle(Constants.LABEL_APP);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(this.windowWidth, this.windowHeight);
		this.setMenuBar(menuBar);
		this.setLayout(new BorderLayout());
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	public static void main(String[] args) {
		WindowGUI gui = new WindowGUI();
		gui.setVisible(true);
	}
}
