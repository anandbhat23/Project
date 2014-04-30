package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import config.ClientConfigDataType;
import config.ClientConfigExporter;
import config.ClientConfigImporter;

public class GeneralConfigGUI extends JPanel{
	
	private JTextField configFileName;
	private JPanel configBasicPanel;
	private JPanel importerPanel;
	private JPanel transformerPanel;
	private JPanel exporterPanel;
	private JPanel buttonPanel;
	private JButton submitButton;
	private Integer windowWidth;
	private Integer windowHeight;
	private GeneralImportConfigPanel importConfigPanel;
//	private ImporterHttpConfigPanel importerHttpConfigPanel;
//	private ImporterMySqlConfigPanel importerMySqlConfigPanel;
	private GeneralExportConfigPanel exportConfigPanel;
//	private ExporterHttpConfigPanel exportHttpConfigPanel;
//	private ExporterMySqlConfigPanel exporterMySqlConfigPanel;
	private TransformerConfigPanel transformerConfigPanel;
	private DefaultListModel importListModel;
	
	private final String IMPORTER_LABEL = "Importer";
	private final String TRANSFORMER_LABEL = "Transformer";
	private final String EXPORTER_LABEL = "Exporter";
	private final String SUBMIT_BUTTON_TEXT = "Submit";
	
//	
//	public GeneralConfigGUI() {
//		this.windowWidth = Constants.GENERAL_CONFIG_WINDOW_WIDTH;
//		this.windowHeight = Constants.GENERAL_CONFIG_WINDOW_HEIGHT;
//		this.importConfigPanel = new ImporterHttpConfigPanel();
//		this.exportConfigPanel = new ExporterHttpConfigPanel();
//		
//		this.importerPanel = new JPanel();
//		this.transformerPanel = new JPanel();
//		this.exporterPanel = new JPanel();
//		this.submitButton = new JButton(SUBMIT_BUTTON_TEXT);
//		this.buttonPanel = new JPanel();
//		
//		setGeneralConfigGUI();
//		createGeneralConfigGUI();
//	}
	
	public GeneralConfigGUI(DefaultListModel importListModel) {
		this.windowWidth = Constants.GENERAL_CONFIG_WINDOW_WIDTH;
		this.windowHeight = Constants.GENERAL_CONFIG_WINDOW_HEIGHT;
		this.configFileName = new JTextField();
		this.configBasicPanel = new JPanel();
		this.importListModel = importListModel;
		this.importConfigPanel = new ImporterDetailConfigPanel(importListModel);
//		this.importerMySqlConfigPanel = new ImporterMySqlConfigPanel(importListModel);
//		this.importerHttpConfigPanel = new ImporterHttpConfigPanel(importListModel);
//		this.importConfigPanel = importerMySqlConfigPanel;
		this.exportConfigPanel = new ExporterDetailConfigPanel();
		this.transformerConfigPanel = new TransformerConfigPanel();
		this.importerPanel = new JPanel();
		this.transformerPanel = new JPanel();
		this.exporterPanel = new JPanel();
		this.submitButton = new JButton(SUBMIT_BUTTON_TEXT);
		this.buttonPanel = new JPanel();
		
		setGeneralConfigGUI(importListModel);
		createGeneralConfigGUI();
		this.setBorder(BorderFactory.createTitledBorder("tmp"));
	}
		
//	private void setGeneralConfigGUI() {
//		this.setPreferredSize(new Dimension(windowWidth, windowHeight));
//		this.setLayout(new GridBagLayout());
//
//		
//		importerPanel.setBorder(BorderFactory.createTitledBorder(IMPORTER_LABEL));
//		importerPanel.setLayout(new BorderLayout());
//		importerPanel.setPreferredSize(new Dimension(windowWidth, windowHeight / 3 - 20));
//		importerPanel.add(importConfigPanel, BorderLayout.WEST);
//		
//		transformerPanel.setBorder(BorderFactory.createTitledBorder(TRANSFORMER_LABEL));
//		transformerPanel.setPreferredSize(new Dimension(windowWidth, windowHeight / 3 - 20));
//		transformerPanel.add(new TransformerConfigPanel(), BorderLayout.WEST);
//		
//		exporterPanel.setBorder(BorderFactory.createTitledBorder(EXPORTER_LABEL));
//		exporterPanel.setPreferredSize(new Dimension(windowWidth, windowHeight / 3 - 20));
//		exporterPanel.add(exportConfigPanel, BorderLayout.WEST);
//		
//		buttonPanel.setLayout(new FlowLayout());
//		buttonPanel.setPreferredSize(new Dimension(windowWidth, 30));
//		buttonPanel.add(submitButton);
//	}
	
	private void setGeneralConfigGUI(DefaultListModel importListModel) {
		this.setPreferredSize(new Dimension(windowWidth, windowHeight));
		this.setLayout(new GridBagLayout());
		
		configFileName.setColumns(30);
		configBasicPanel.setBorder(BorderFactory.createTitledBorder(Constants.CONFIG_FILE_NAME_LABEL));
		configBasicPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
//		configBasicPanel.setPreferredSize(new Dimension(windowWidth, 60));
		configBasicPanel.add(configFileName);
		
		importerPanel.setBorder(BorderFactory.createTitledBorder(IMPORTER_LABEL));
		importerPanel.setLayout(new BorderLayout());
//		importerPanel.setPreferredSize(new Dimension(windowWidth, windowHeight / 3 - 40 ));
		importerPanel.add(importConfigPanel, BorderLayout.WEST);
//		importerPanel.add(importerHttpConfigPanel, BorderLayout.WEST);

		transformerPanel.setBorder(BorderFactory.createTitledBorder(TRANSFORMER_LABEL));
//		transformerPanel.setPreferredSize(new Dimension(windowWidth, windowHeight / 3 -40 ));
		transformerPanel.add(transformerConfigPanel, BorderLayout.WEST);
		
		exporterPanel.setBorder(BorderFactory.createTitledBorder(EXPORTER_LABEL));
//		exporterPanel.setPreferredSize(new Dimension(windowWidth, windowHeight / 3-40));
		exporterPanel.add(exportConfigPanel, BorderLayout.WEST);
		
		buttonPanel.setLayout(new FlowLayout());
//		buttonPanel.setPreferredSize(new Dimension(windowWidth, 60));
		buttonPanel.add(submitButton);
	}
		
	public void createGeneralConfigGUI() {
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.weighty = 0.01;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.PAGE_START;
//		c.gridwidth = 10;
		this.add(configBasicPanel, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 0.01;
		c.gridx = 0;
		c.gridy = 1;
		c.gridheight =2;
//		c.gridwidth = 10;
		this.add(importerPanel, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 4;
		c.gridheight = 3;
		c.gridwidth = 10;
		this.add(transformerPanel, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 7;
		c.gridheight = 3;
		c.gridwidth = 10;
		this.add(exporterPanel, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 10;
		c.gridheight = 1;
		c.gridwidth = 10;
		c.anchor = GridBagConstraints.PAGE_END;
		this.add(buttonPanel, c);
	}
	
	
	public void addSubmitButtonListener(MouseListener listener) {
		submitButton.addMouseListener(listener);
	}
	
	public void addAddImporterButtonListener(MouseListener listener) {
		importConfigPanel.getAddImporterButton().addMouseListener(listener);
	}
	
	public JList getImporterList(){
		return importConfigPanel.getImporterList();
	}
	
	public ClientConfigDataType getImporterDataType(){
		return importConfigPanel.getDataType();
	}
	
	public ClientConfigDataType getExporterDataType(){
		return exportConfigPanel.getDataType();
	}
	
	public ClientConfigImporter getClientConfigImporter(){
		importConfigPanel.setClientConfigImporter();
		return importConfigPanel.getClientConfigImporter();
	}
	
	public ClientConfigExporter getClientConfigExporter() {
		exportConfigPanel.setClientConfigExporter();
		return exportConfigPanel.getClientConfigExporter();
	}
	
	public String getConfigFileName() {
		return configFileName.getText();
	}
	
	public String getTransformerScript() {
		return transformerConfigPanel.getTransformerScript();
	}
	
	public void resetImporterConfigPanel(){
		importConfigPanel.resetImporterConfigGUI();
	}
	
	public void resetExporterConfigPanel(){
		exportConfigPanel.resetExporterConfigGUI();
	}
	
	public void addImportDataTypeListActionListener(ItemListener listener) {
		importConfigPanel.addDataTypeListActionListener(listener);
	}
	
	public void addExportDataTypeListActionListener(ItemListener listener) {
		exportConfigPanel.addDataTypeListActionListener(listener);
	}
	
	public void updateImporterDataType(ClientConfigDataType dataType) {
		importConfigPanel.setDataType(dataType);
	}
	
	public void updateExporterDataType(ClientConfigDataType dataType) {
		exportConfigPanel.setDataType(dataType);
	}
	
	public void reset() {
		importConfigPanel.resetImporterConfigGUI();
		exportConfigPanel.resetExporterConfigGUI();
		transformerConfigPanel.resetTransformerConfigGUI();
	}
	
//	public void switchImportConfigPanel(ClientConfigDataType dataType){
//		if (!dataType.equals(importConfigPanel.getDataType())) {
//			if (dataType.equals(ClientConfigDataType.HTTP)) {
////				importerPanel.remove(importConfigPanel);
////				importerPanel.invalidate();
////				importerPanel.repaint();
////				importerPanel.removeAll();
////				importerMySqlConfigPanel.setVisible(false);
////				importerHttpConfigPanel.setVisible(true);
//				importConfigPanel = importerHttpConfigPanel;
////				importConfigPanel = new ImporterHttpConfigPanel(importListModel);
//				importerPanel.add(importConfigPanel, BorderLayout.WEST);
//				System.out.println(importerPanel.getComponents()[0]);
////				importerPanel.setVisible(true);
//				invalidate();
//				repaint();
//				printAll(getGraphics());
//			} else if (dataType.equals(ClientConfigDataType.MYSQL)) {
////				importerPanel.remove(importConfigPanel);
////				importerPanel.invalidate();
////				importerPanel.repaint();
//				importerPanel.removeAll();
//				importConfigPanel = new ImporterMySqlConfigPanel(importListModel);
//				importerPanel.add(importConfigPanel, BorderLayout.WEST);
//				repaint();
//				printAll(getGraphics());
//			}
//			importConfigPanel.setVisible(true);
//			
//			
//		}
//	}
	public static void main(String[] args) {
		WindowGUI gui = new WindowGUI();
		DefaultListModel tmpModel = new DefaultListModel();
		gui.setLayout(new BorderLayout());
		gui.add(new GeneralConfigGUI(tmpModel), BorderLayout.CENTER);
//		gui.pack();
		gui.setVisible(true);
	}
}
