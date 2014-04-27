package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class GeneralConfigGUI extends JPanel{
	
	private JPanel importerPanel;
	private JPanel transformerPanel;
	private JPanel exporterPanel;
	private JPanel buttonPanel;
	private JButton submitButton;
	private Integer windowWidth;
	private Integer windowHeight;
	private GeneralImportConfigPanel importConfigPanel;
	private GeneralExportConfigPanel exportConfigPanel;
	
	private final String IMPORTER_LABEL = "Importer";
	private final String TRANSFORMER_LABEL = "Transformer";
	private final String EXPORTER_LABEL = "Exporter";
	private final String SUBMIT_BUTTON_TEXT = "Submit";
	
	
	public GeneralConfigGUI() {
		this.windowWidth = Constants.GENERAL_CONFIG_WINDOW_WIDTH;
		this.windowHeight = Constants.GENERAL_CONFIG_WINDOW_HEIGHT;
		this.importConfigPanel = new ImporterHttpConfigPanel();
		this.exportConfigPanel = new ExporterHttpConfigPanel();
		this.setPreferredSize(new Dimension(windowWidth, windowHeight));
		this.setLayout(new GridBagLayout());
		
		importerPanel = new JPanel();
		importerPanel.setBorder(BorderFactory.createTitledBorder(IMPORTER_LABEL));
		importerPanel.setLayout(new BorderLayout());
		importerPanel.setPreferredSize(new Dimension(windowWidth, windowHeight / 3 - 12));
		importerPanel.add(importConfigPanel, BorderLayout.WEST);
		
		transformerPanel = new JPanel();
		transformerPanel.setBorder(BorderFactory.createTitledBorder(TRANSFORMER_LABEL));
		transformerPanel.setPreferredSize(new Dimension(windowWidth, windowHeight / 3 - 12));
		transformerPanel.add(new TransformerConfigPanel(), BorderLayout.WEST);
		
		exporterPanel = new JPanel();
		exporterPanel.setBorder(BorderFactory.createTitledBorder(EXPORTER_LABEL));
		exporterPanel.setPreferredSize(new Dimension(windowWidth, windowHeight / 3 - 12));
		exporterPanel.add(exportConfigPanel, BorderLayout.WEST);
		
		submitButton = new JButton(SUBMIT_BUTTON_TEXT);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.setPreferredSize(new Dimension(windowWidth, 30));
		buttonPanel.add(submitButton);
		
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		this.add(importerPanel, c);
		
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 1;
		c.gridheight = 1;
		this.add(transformerPanel, c);
		
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 2;
		c.gridheight = 1;
		this.add(exporterPanel, c);
		
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 3;
		c.gridheight = 1;
		this.add(buttonPanel, c);
	}
	
	public void addSubmitButtonListener(MouseListener listener) {
		submitButton.addMouseListener(listener);
	}
	
	public void addAddImporterButtonListener(MouseListener listener) {
		importConfigPanel.getAddImporterButton().addMouseListener(listener);
	}
	
	public static void main(String[] args) {
		WindowGUI gui = new WindowGUI();
		gui.add(new GeneralConfigGUI());
		gui.pack();
		gui.setVisible(true);
	}
}
