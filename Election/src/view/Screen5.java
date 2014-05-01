package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.JComponent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import client.ClientController;

import resultViewer.ResultViewer;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class Screen5 extends JFrame {

	
	
	private JPanel contentPane;
	private static JTextPane jTextPane1;
	private JLabel jLabel1;
	private ResultViewer viewResults = new ResultViewer();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Screen5 frame = new Screen5();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public Screen5() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		GroupLayout contentPaneLayout = new GroupLayout((JComponent)contentPane);
		contentPane.setLayout(contentPaneLayout);
		setContentPane(contentPane);
		contentPane.setBackground(Color.WHITE);		
//		{
//			jLabel1 = new JLabel();
//			try {
//				jLabel1.setText("Location of the output :" + viewResults.getData(ClientController.selectedConfigFileName));
//			} catch (SQLException e) {
//				
//				e.printStackTrace();
//			}
//		}
		{
			jTextPane1 = new JTextPane();
			jTextPane1.setText("RESULT:\n");
			jTextPane1.setFont(new java.awt.Font("Century Schoolbook L",1,12));
		}
		contentPaneLayout.setVerticalGroup(contentPaneLayout.createSequentialGroup()
			.addContainerGap()
			.addComponent(jTextPane1, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE)
			.addGap(0, 7, Short.MAX_VALUE));
		contentPaneLayout.setHorizontalGroup(contentPaneLayout.createSequentialGroup()
			.addContainerGap(18, 18)
			.addComponent(jTextPane1, 0, 400, Short.MAX_VALUE)
			.addContainerGap());

		try {
			addText(viewResults.getData(ClientController.selectedConfigFileName));
		} catch (SQLException e) {
			System.out.println("Not able to get data from reviewResults..!");
			e.printStackTrace();
		}
	}
	
	public static void addText(String s) {
		try {
			Document doc = jTextPane1.getDocument();
			doc.insertString(doc.getLength(), s, null);
		} catch(BadLocationException exc) {
			exc.printStackTrace();
		}
	}
	
	
}
