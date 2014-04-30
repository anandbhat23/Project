package client;

import java.awt.CardLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import bridge.Server;

import view.GeneralConfigGUI;
import view.SelectConfigGUI;
import view.WindowGUI;
import config.ClientConfigDataType;
import config.ClientConfigExporter;
import config.ClientConfigFile;
import config.ClientConfigImporter;
import config.ClientMySqlExporter;
import config.ClientMySqlImporter;

public class ClientController implements Runnable {
	private WindowGUI gui;
	private GeneralConfigGUI generalConfigGUI;
	private SelectConfigGUI selectConfigGUI;
	private ClientModel clientModel;
	private ClientConfigFile currentConfigFile;
	private Map<String, ClientConfigFile> configFileList;
	private ArrayList<String> importerNames = new ArrayList<String>();
	
	public ClientController() {
		gui = new WindowGUI(Constants.SELECT_CONFIG_GUI_WIDTH, Constants.SELECT_CONFIG_GUI_HEIGHT);
		currentConfigFile = new ClientConfigFile();
		clientModel = new ClientModel();
		generalConfigGUI = new GeneralConfigGUI(clientModel.getImportListModel());
		selectConfigGUI = new SelectConfigGUI(clientModel.getConfigListModel(), Constants.SELECT_CONFIG_GUI_WIDTH,  Constants.SELECT_CONFIG_GUI_HEIGHT);
		configFileList = new HashMap<String, ClientConfigFile>();
		gui.addCard(selectConfigGUI, Constants.SELECT_CONFIG_PANEL_KEY);
		gui.addCard(generalConfigGUI, Constants.GENERAL_CONFIG_PANEL_KEY);
		
		new Thread(new Server()).start();
	}
	
	/**
	 * method to save user input config parameters into object / save to file
	 */
	public void saveConfig(){
		//TODO: implement config save
		setCurrentConfigFile();
		addConfigFile(currentConfigFile);
		//Store config on server
				try {
					storeConfigFile(currentConfigFile);
				} catch (IOException e) {
					System.out.println("Error while creating a new config file");
				}
		resetCurrentConfigFile();
	}
	
	public void storeConfigFile(ClientConfigFile currentConfigFile) throws IOException{

		int length = 0, i=0;
		
		File file = new File("src/" + currentConfigFile.getConfigFileName());
		file.createNewFile();

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		
		//Write importers
		length = importerNames.size();
		
		bw.write("importer :\n");
		
		while(i<length){
		
		ClientConfigImporter currentImporter = currentConfigFile.getClientImporter(importerNames.get(i));
		
		if (currentImporter.getImporterType().getType().equalsIgnoreCase("HTTP")) {

			bw.write("- type : HTTP\n");
			bw.write("  location : " + currentImporter.getLocation() + "\n");
			bw.write("\n");
		}
		else {
			ClientMySqlImporter mysqlImporter = (ClientMySqlImporter) currentImporter;
			
			bw.write("- type : MySQL\n" );
			bw.write("  location : " + mysqlImporter.getLocation() + "\n");
			bw.write("  username : " + mysqlImporter.getUserName() + "\n");
			bw.write("  password : " + mysqlImporter.getPassword() + "\n");
			bw.write("  table : " + mysqlImporter.getTable() + "\n");
			bw.write("  columns : \n");
			
			
			int len = mysqlImporter.getColumns().length, j=0;
			String columns[] = mysqlImporter.getColumns();
			
			while(j<len){
				bw.write("    - " + columns[j] + "\n");
				j++;
			}
			
			bw.write(" rowStart : " + mysqlImporter.getRowStart() + "\n");
			bw.write(" rowEnd : " + mysqlImporter.getRowEnd() + "\n");
		}
		i++;
		}
		
		bw.write("\n\n");
		
		//Write transformer
		bw.write("transformer : \n");
		bw.write(" - transformop : " + currentConfigFile.getTransformerScript() + "\n\n");
		
		//Write exporter
		bw.write("exporter : \n");
		
		ClientConfigExporter currentExporter = currentConfigFile.getClientExporter();
		
		if (currentExporter.getExporterType().getType().equalsIgnoreCase("HTTP")) {

			bw.write("- type : HTTP\n");
			bw.write("  location : " + currentExporter.getLocation() + "\n");
			bw.write("\n");
		}
		else {
			
			ClientMySqlExporter mysqlExporter = (ClientMySqlExporter) currentExporter;
			bw.write("- type : MySQL\n");
			bw.write("  location : " + mysqlExporter.getLocation() + "\n");
			bw.write("  username : " + mysqlExporter.getUserName() + "\n");
			bw.write("  password : " + mysqlExporter.getPassword() + "\n");
			bw.write("  table : " + mysqlExporter.getTable() + "\n");
			bw.write("  columns : \n");
			
			System.out.println("length = " +  mysqlExporter.getColumns().length);
			int len = mysqlExporter.getColumns().length, j=0;
			String columns[] = mysqlExporter.getColumns();
			
			while(j<len){
				bw.write("    - " + columns[j] + "\n");
				j++;
			}
		}
		
		//close file
		bw.close();
		
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
				
//				selectConfigGUI.setVisible(false);
//				gui.setContentPane(generalConfigGUI);
//				generalConfigGUI.setBorder(BorderFactory.createTitledBorder("abc"));
//				gui.add(generalConfigGUI);
//				gui.remove(selectConfigGUI);
				
//				gui.add(generalConfigGUI);
				// adjust window size
				
//				gui.setResizable(true);
				// add new content
				
				JPanel cards = gui.getCards();
				CardLayout cardLayout = (CardLayout) cards.getLayout();
				cardLayout.show(cards, Constants.GENERAL_CONFIG_PANEL_KEY);
				gui.setSize(Constants.GENERAL_CONFIG_GUI_WIDTH, Constants.GENERAL_CONFIG_GUI_HEIGHT);
				gui.setResizable(true);
				gui.setLocationRelativeTo(null);
				//gui.revalidate();
				gui.repaint();
				
//				gui.pack();
				// make screen visible
//				generalConfigGUI.setVisible(true);
//				gui.setVisible(true);
				
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
//				generalConfigGUI.setVisible(false);
				
//				gui.setContentPane(selectConfigGUI);
				JPanel cards = gui.getCards();
				CardLayout cardLayout = (CardLayout) cards.getLayout();
				cardLayout.show(cards, Constants.SELECT_CONFIG_PANEL_KEY);
				gui.setSize(Constants.SELECT_CONFIG_GUI_WIDTH, Constants.SELECT_CONFIG_GUI_HEIGHT);
				gui.setLocationRelativeTo(null);
				//gui.revalidate();
				gui.repaint();
//				gui.pack();
//				selectConfigGUI.setVisible(true);
			}
		});
		
		
		generalConfigGUI.addAddImporterButtonListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				
				ClientConfigImporter currentImporter = generalConfigGUI.getClientConfigImporter();
				importerNames.add(currentImporter.getImporterName());
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
//		gui.setContentPane(selectConfigGUI);
		gui.setSize(Constants.SELECT_CONFIG_GUI_WIDTH, Constants.SELECT_CONFIG_GUI_HEIGHT);
		JPanel cards = gui.getCards(); 
		CardLayout cardLayout = (CardLayout) cards.getLayout();
		cardLayout.show(cards, Constants.SELECT_CONFIG_PANEL_KEY);
//		gui.pack();
		gui.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new ClientController());
	}
}
