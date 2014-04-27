package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JList;
import javax.swing.SwingUtilities;

import com.sun.corba.se.impl.orbutil.closure.Constant;
import com.sun.media.sound.ModelAbstractChannelMixer;

import config.ClientConfigDataType;
import config.ClientConfigFile;
import config.ClientConfigFileFactory;
import config.ClientConfigImporter;
import view.GeneralConfigGUI;
import view.SelectConfigGUI;
import view.WindowGUI;

public class ClientController implements Runnable {
	private WindowGUI gui;
	private GeneralConfigGUI generalConfigGUI;
	private SelectConfigGUI selectConfigGUI;
	private ClientModel clientModel;
	private ClientConfigFile currentConfigFile;
	private Map<String, ClientConfigFile> configFileList;
	
	public ClientController() {
		gui = new WindowGUI(Constants.SELECT_CONFIG_GUI_WIDTH, Constants.SELECT_CONFIG_GUI_HEIGHT);
		currentConfigFile = new ClientConfigFile();
		clientModel = new ClientModel();
		generalConfigGUI = new GeneralConfigGUI(clientModel.getImportListModel());
		selectConfigGUI = new SelectConfigGUI(clientModel.getConfigListModel(), Constants.SELECT_CONFIG_GUI_WIDTH,  Constants.SELECT_CONFIG_GUI_HEIGHT);
		configFileList = new HashMap<String, ClientConfigFile>();
	}
	
	/**
	 * method to save user input config parameters into object / save to file
	 */
	public void saveConfig(){
		//TODO: implement config save
		setCurrentConfigFile();
		addConfigFile(currentConfigFile);
		resetCurrentConfigFile();
	}
	
	public void clearCache() {
		clientModel.getImportListModel().clear();
		generalConfigGUI.reset();
	}
	
	public void addConfigFile(ClientConfigFile configFile) {
		String configFileName = configFile.getConfigFileName();
		configFileList.put(configFileName, configFile);
		clientModel.getConfigListModel().addElement(configFileName);
	}
	
	public void removeConfigFile(String configFileName){
		configFileList.remove(configFileName);
		clientModel.getConfigListModel().removeElement(configFileName);
	}
	
	public void setCurrentConfigFile(){
		currentConfigFile.setConfigFileName(generalConfigGUI.getConfigFileName());
		currentConfigFile.setTransformerScript(generalConfigGUI.getTransformerScript());
		currentConfigFile.setClientExporter(generalConfigGUI.getClientConfigExporter());
	}
	
	public void resetCurrentConfigFile() {
		currentConfigFile = new ClientConfigFile();
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
				gui.pack();
				// make screen visible
				generalConfigGUI.setVisible(true);
				
			}
			
		});
		
		selectConfigGUI.getSelectConfigButtonPanel().addRemoveButtonClickListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				removeConfigFile(selectConfigGUI.getSelectedConfigFileName());
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
				clearCache();
				generalConfigGUI.setVisible(false);
				gui.setSize(Constants.SELECT_CONFIG_GUI_WIDTH, Constants.SELECT_CONFIG_GUI_HEIGHT);
				gui.setContentPane(selectConfigGUI);
				gui.setLocationRelativeTo(null);
				gui.pack();
				selectConfigGUI.setVisible(true);
			}
		});
		
		
		generalConfigGUI.addAddImporterButtonListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				
				ClientConfigImporter currentImporter = generalConfigGUI.getClientConfigImporter();
				currentConfigFile.addClientImporter(currentImporter.getImporterName(), currentImporter);
				clientModel.getImportListModel().addElement(currentImporter.getImporterName());
				generalConfigGUI.resetImporterConfigPanel();
			}
		});
		
		generalConfigGUI.addImportDataTypeListActionListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == ItemEvent.SELECTED) {
					// change pane content 
//					generalConfigGUI.switchImportConfigPanel(ClientConfigDataType.getDataType(e.getItem().toString()));
					generalConfigGUI.updateImporterDataType((ClientConfigDataType) e.getItem());
				}
			}
		});
		
		generalConfigGUI.addExportDataTypeListActionListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == ItemEvent.SELECTED) {
					generalConfigGUI.updateExporterDataType((ClientConfigDataType) e.getItem());
				}
			}
		});
		
	}
	
	public void run() {
		addListeners();
		gui.setContentPane(selectConfigGUI);
		gui.pack();
		gui.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new ClientController());
	}
}
