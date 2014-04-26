package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class SelectConfigGUI extends JPanel{
	private SelectConfigButtonPanel selectConfigButtonPanel;
	private JList configList;
	private JScrollPane configScrollPane;
	private DefaultListModel model;
	private Integer windowWidth;
	private Integer windowHeight;
	
	public SelectConfigGUI() {
		this.selectConfigButtonPanel = new SelectConfigButtonPanel();
		this.model = new DefaultListModel();
		this.configList = new JList(model);
		this.configScrollPane = new JScrollPane(configList);
		this.windowWidth = Constants.SELECTP_WIDTH;
		this.windowHeight = Constants.SELECTP_HEIGHT;
		setComponents();
		createView();
	}
	
	@SuppressWarnings("unchecked")
	public SelectConfigGUI(DefaultListModel model, Integer windowWidth, Integer windowHeight) {
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		this.selectConfigButtonPanel = new SelectConfigButtonPanel(windowWidth);
		this.model = model;
		this.configList = new JList(model);
		this.configScrollPane = new JScrollPane(configList);
		
		setComponents();
		createView();
	}
	
	private void setConfigList() {
		configList.setVisibleRowCount(Constants.CONFIGLIST_VISIBLE_ROW_COUNT);
		configList.setBorder(BorderFactory.createTitledBorder(Constants.LIST_CONFIG_LABEL));
	}
	
	public void setConfigFileList(String[] configFileList) {
		configList.setListData(configFileList);
	}
	
	public void addConfigFileList(String configFileName) {
		model.addElement(configFileName);
	}
	
	private void setComponents() {
		setConfigList();
	}
	
	private void createView() {
		this.setLayout(new BorderLayout());
		this.add(configScrollPane, BorderLayout.NORTH);
		this.add(selectConfigButtonPanel, BorderLayout.SOUTH);
	}
	
	public SelectConfigButtonPanel getSelectConfigButtonPanel(){
		return selectConfigButtonPanel;
	}
	
	
	public static void main(String[] args) {
		WindowGUI gui = new WindowGUI(client.Constants.SELECT_CONFIG_GUI_WIDTH, client.Constants.SELECT_CONFIG_GUI_HEIGHT);
		gui.add(new SelectConfigGUI());
		gui.setVisible(true);
	}
	
}
