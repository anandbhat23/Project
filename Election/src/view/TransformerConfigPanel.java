package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TransformerConfigPanel extends JPanel{
	private JTextArea transformerScriptArea;
	private JScrollPane transformerScriptPane;
	
	public TransformerConfigPanel() {
		transformerScriptArea = new JTextArea();
		transformerScriptPane = new JScrollPane(transformerScriptArea);
		setTransformerConfigPanel();
		createTransformerConfigPanel();
	}
	
	public void setTransformerConfigPanel() {
//		transformerScriptArea.setPreferredSize(new Dimension(super.getWidth(), super.getHeight() / 3));
		transformerScriptArea.setRows(10);
		transformerScriptArea.setColumns(90);
		
		transformerScriptPane.setBorder(BorderFactory.createTitledBorder(Constants.TRANSFORMER_SCRIPT_LABEL));
	}
	
	public void createTransformerConfigPanel(){
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 10;
		c.gridwidth = 10;
		this.add(transformerScriptPane, c);
	}
	
	public String getTransformerScript() {
		return transformerScriptArea.getText();
	}
	
	public void resetTransformerConfigGUI() {
		transformerScriptArea.setText("");
	}
	
	public static void main(String args[]) {
		WindowGUI gui = new WindowGUI();
		gui.add(new TransformerConfigPanel());
		gui.pack();
		gui.setVisible(true);
	}
}
