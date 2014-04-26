package view;

import java.awt.BorderLayout;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class SelectConfigButtonPanel extends JPanel{
	private JButton newButton;
	private JButton removeButton;
	private JButton chooseButton;
	private JButton startButton;
	private JPanel topButtonPanel;
	private Integer panelWidth;
	
	public SelectConfigButtonPanel() {
		this.newButton = new JButton(Constants.BUTTON_NEW_TEXT);
		this.removeButton = new JButton(Constants.BUTTON_REMOVE_TEXT);
		this.chooseButton = new JButton(Constants.BUTTON_CHOOSE_TEXT);
		this.startButton = new JButton(Constants.BUTTON_START_TEXT);
		this.topButtonPanel = new JPanel();
		this.panelWidth = Constants.CONFIG_LIST_WIDTH;
		setConfigButtonPanel();
		createConfigButtonPanel();
	}
	
	public SelectConfigButtonPanel(Integer panelWidth) {
		this.newButton = new JButton(Constants.BUTTON_NEW_TEXT);
		this.removeButton = new JButton(Constants.BUTTON_REMOVE_TEXT);
		this.chooseButton = new JButton(Constants.BUTTON_CHOOSE_TEXT);
		this.startButton = new JButton(Constants.BUTTON_START_TEXT);
		this.topButtonPanel = new JPanel();
		this.panelWidth = panelWidth;
		setConfigButtonPanel();
		createConfigButtonPanel();
	}
	
	private void setConfigButtonPanel() {
		this.setLayout(new BorderLayout());
		setNewButton();
		setRemoveButton();
		setChooseButton();
		setStartButton();
		this.topButtonPanel.setLayout(new BorderLayout());
		topButtonPanel.add(newButton, BorderLayout.WEST);
		topButtonPanel.add(removeButton, BorderLayout.EAST);
	}
	
	private void createConfigButtonPanel() {
		this.add(topButtonPanel, BorderLayout.NORTH);
		this.add(chooseButton, BorderLayout.CENTER);
		this.add(startButton, BorderLayout.SOUTH);
	}
	
	private void setNewButton() {
		newButton.setSize(panelWidth / 2, Constants.NEW_BUTTON_HEIGHT);
	}
	
	private void setRemoveButton() {
		removeButton.setSize(panelWidth / 2, Constants.REMOVE_BUTTON_HEIGHT);
	}
	
	private void setChooseButton() {
		chooseButton.setSize(panelWidth, Constants.CHOOSE_BUTTON_HEIGHT);
	}
	
	private void setStartButton() {
		startButton.setSize(panelWidth, Constants.START_BUTTON_HEIGHT);
	}
	
	private void setConfigFileChooser() {
//		FileNameExtensionFilter filter = new FileNameExtensionFilter(
//	    "YAML Config File", "yaml");
//	configChooser.setFileFilter(filter);
	}
	
	public void addNewButtonClickListener(MouseListener listener) {
		newButton.addMouseListener(listener);
	}
}
