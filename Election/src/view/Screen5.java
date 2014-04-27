package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.GroupLayout;
import javax.swing.JComponent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;
import javax.swing.border.EmptyBorder;


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
	private JTextPane jTextPane1;
	private JLabel jLabel1;

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
		{
			jLabel1 = new JLabel();
			jLabel1.setText("Location of the output :");
		}
		{
			jTextPane1 = new JTextPane();
			jTextPane1.setText("jTextPane1");
		}
		contentPaneLayout.setVerticalGroup(contentPaneLayout.createSequentialGroup()
			.addContainerGap()
			.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
			.addGap(27)
			.addComponent(jTextPane1, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
			.addContainerGap(58, Short.MAX_VALUE));
		contentPaneLayout.setHorizontalGroup(contentPaneLayout.createSequentialGroup()
			.addContainerGap(18, 18)
			.addGroup(contentPaneLayout.createParallelGroup()
			    .addGroup(GroupLayout.Alignment.LEADING, contentPaneLayout.createSequentialGroup()
			        .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
			        .addGap(0, 174, Short.MAX_VALUE))
			    .addGroup(contentPaneLayout.createSequentialGroup()
			        .addComponent(jTextPane1, GroupLayout.PREFERRED_SIZE, 321, GroupLayout.PREFERRED_SIZE)
			        .addGap(0, 0, Short.MAX_VALUE)))
			.addContainerGap(85, 85));
	}

}
