package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.MenuBar;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class WindowGUI extends JFrame{
	private MenuGUI menuBar;
	private Integer windowWidth;
	private Integer windowHeight;
	private JPanel cards;
	
	public WindowGUI() {
		menuBar = new MenuGUI();
		windowWidth = Constants.WINDOW_WIDTH;
		windowHeight = Constants.WINDOW_HEIGHT;
		cards = new JPanel();
		setWindow();
	}
	
	public WindowGUI(Integer width, Integer height) {
		menuBar = new MenuGUI();
		this.windowWidth = width;
		this.windowHeight = height;
		cards = new JPanel();
		setWindow();
		createWindow();
	}
	
	private void setWindow(){	
		this.setTitle(Constants.LABEL_APP);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(this.windowWidth, this.windowHeight);
		this.setMenuBar(menuBar);
		this.setLayout(new BorderLayout());
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		cards.setLayout(new CardLayout());
	}
	
	private void createWindow(){
		this.add(cards);
	}
	
	public JPanel getCards() {
		return cards;
	}
	
	public void addCard(JPanel panel, String panelKey) {
		cards.add(panel, panelKey);
	}
	
	public static void main(String[] args) {
		WindowGUI gui = new WindowGUI();
		gui.setVisible(true);
	}
}
