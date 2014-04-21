package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
	
	private final String IMPORTER_LABEL = "Importer";
	private final String TRANSFORMER_LABEL = "Transformer";
	private final String EXPORTER_LABEL = "Exporter";
	private final String SUBMIT_BUTTON_TEXT = "Submit";
	
	public GeneralConfigGUI() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		importerPanel = new JPanel();
		importerPanel.setBorder(BorderFactory.createTitledBorder(IMPORTER_LABEL));
		
		transformerPanel = new JPanel();
		transformerPanel.setBorder(BorderFactory.createTitledBorder(TRANSFORMER_LABEL));
		
		exporterPanel = new JPanel();
		exporterPanel.setBorder(BorderFactory.createTitledBorder(EXPORTER_LABEL));
		
		submitButton = new JButton(SUBMIT_BUTTON_TEXT);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(submitButton);
		
		this.add(importerPanel);
		this.add(transformerPanel);
		this.add(exporterPanel);
		this.add(buttonPanel);
//		this.add(submitButton, );;
	}
	
	public void addSubmitButtonListener(MouseListener listener) {
		submitButton.addMouseListener(listener);
	}
	
	public static void main(String[] args) {
		WindowGUI gui = new WindowGUI();
		gui.add(new GeneralConfigGUI());
		gui.setVisible(true);
	}
}
