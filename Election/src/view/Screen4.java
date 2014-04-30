package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;


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
public class Screen4 extends JFrame implements ActionListener {

	{
		//Set Look & Feel
		try {
			//javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	private JPanel contentPane;
	private AbstractAction cancelAction;

	private JButton masterButton;
	private static ArrayList<JButton> slaves = new ArrayList<JButton>();
	private static GroupLayout contentPaneLayout;

	private AbstractAction viewResult;
	private JButton View;
	private JTextPane jTextPane1;
	private JMenu help;
	private JMenu Start;
	private JMenuItem exitMenuItem;
	private JMenuItem newMenuItem;
	private JMenuBar Menu;
	private JMenu jMenu1;

	private  Timer timer1 = new Timer(500, this);
	private  Timer timer2 = new Timer(550, this);
	private  Timer timer3 = new Timer(500, this);
	private  Timer timer4 = new Timer(600, this);

	//private static Screen4 frame;
	private static JScrollPane scroll;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Screen4 frame = new Screen4();
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
	public Screen4() {

		setTitle("Distributed ETL");
		this.setSize(2000,2000);
		this.pack();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 700);
		{
			Menu = new JMenuBar();
			setJMenuBar(Menu);
			Menu.add(getJMenu1());
			Menu.add(getStart());
			Menu.add(getHelp());
		}

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.WHITE);

		contentPaneLayout = new GroupLayout((JComponent)contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setVerticalGroup(contentPaneLayout.createSequentialGroup()
				.addContainerGap(19, 19)
				.addGroup(contentPaneLayout.createParallelGroup()
						.addComponent(getJTextPane1(), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 349, GroupLayout.PREFERRED_SIZE)
						.addGroup(GroupLayout.Alignment.LEADING, contentPaneLayout.createSequentialGroup()
								.addGap(106)
								.addComponent(getMasterButton(), GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
								.addGap(183)))
								.addGap(146)
								.addComponent(getView(), GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(87, Short.MAX_VALUE));
		contentPaneLayout.setHorizontalGroup(contentPaneLayout.createSequentialGroup()
				.addContainerGap(27, 27)
				.addComponent(getMasterButton(), GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
				.addGap(0, 118, Short.MAX_VALUE)
				.addComponent(getView(), GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addComponent(getJTextPane1(), GroupLayout.PREFERRED_SIZE, 339, GroupLayout.PREFERRED_SIZE)
				.addContainerGap());

		setContentPane(contentPane);
		contentPane.setPreferredSize(new java.awt.Dimension(480, 327));

		timer1.start();
		timer2.start();
		timer3.start();
		timer4.start();
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();


		if(src == timer1) {
			addText("Distributed ETL Tool\n");
		}
		if(src == timer2) {
			addText("Distributed ETL Tool\n");
		}
	}

	private void addText(String s) {
		try {
			Document doc = jTextPane1.getDocument();
			doc.insertString(doc.getLength(), s, null);
		} catch(BadLocationException exc) {
			exc.printStackTrace();
		}
	}

	private JButton getMasterButton() {
		if(masterButton == null) {
			masterButton = new JButton();
			masterButton.setText("MASTER");
			//masterButton.setEnabled(false);
			masterButton.setFont(new java.awt.Font("Elephant",1,12));
		}
		return masterButton;
	}

	private JMenu getJMenu1() {
		if(jMenu1 == null) {
			jMenu1 = new JMenu();
			jMenu1.setText("File");
			jMenu1.add(getNewMenuItem());
			jMenu1.add(getExitMenuItem());
		}
		return jMenu1;
	}

	private JMenuItem getNewMenuItem() {
		if(newMenuItem == null) {
			newMenuItem = new JMenuItem();
			newMenuItem.setText("New");
		}
		return newMenuItem;
	}

	private JMenuItem getExitMenuItem() {
		if(exitMenuItem == null) {
			exitMenuItem = new JMenuItem();
			exitMenuItem.setText("Exit");
		}
		return exitMenuItem;
	}

	private JMenu getStart() {
		if(Start == null) {
			Start = new JMenu();
			Start.setText("Start");
		}
		return Start;
	}

	private JMenu getHelp() {
		if(help == null) {
			help = new JMenu();
			help.setText("Help");
		}
		return help;
	}

	private JTextPane getJTextPane1() {
		if(jTextPane1 == null) {
			jTextPane1 = new JTextPane();
			jTextPane1.setText("DISTRIBUTED ETL");
			jTextPane1.setBounds(20,	20, 50, 50);
			jTextPane1.setSize(50, 150);

			//				  JScrollPane scrolltxt = new JScrollPane(jTextPane1);
		}
		return jTextPane1;
	}

	private JButton getView() {
		if(View == null) {
			View = new JButton();
			View.setText("View Results");
			View.setAction(getViewResult());
		}
		return View;
	}

	private AbstractAction getViewResult() {
		if(viewResult == null) {
			viewResult = new AbstractAction("viewResult", null) {
				public void actionPerformed(ActionEvent evt) {
					Screen5 frame5 = new Screen5();
					frame5.setVisible(true);
				}
			};
		}
		return viewResult;
	}

	public static void addSlave(int slaveNo) {
		
		slaves.add(new JButton());
		slaves.get(slaveNo-1).setText("Slave " + Integer.toString(slaveNo));

		contentPaneLayout.setVerticalGroup(contentPaneLayout.createSequentialGroup()
				.addContainerGap(10 +((slaveNo-1)*45), 10 +((slaveNo-1)*45))
				.addComponent(slaves.get(slaveNo-1), GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
				.addGap(90)
				.addContainerGap(210, Short.MAX_VALUE));
		contentPaneLayout.setHorizontalGroup(contentPaneLayout.createSequentialGroup()
				.addContainerGap(250, 250)
				.addComponent(slaves.get(slaveNo-1), GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(209, Short.MAX_VALUE));

	}

	public static void removeSlave(int slaveNo) {

		JButton slave = slaves.get(slaveNo-1);
		slave.setBackground(Color.RED);
	}
}
