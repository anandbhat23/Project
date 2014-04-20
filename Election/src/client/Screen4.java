package client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;

import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
import javax.swing.text.StyledDocument;


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
			javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	private JPanel contentPane;
	private AbstractAction cancelAction;
	
	private JButton slaveButton5;
	private JButton slaveButton4;
	private JButton slaveButton3;
	private JButton slaveButton2;
	private JButton slaveButton1;
	private JButton masterButton;
	
	
	private JButton LButton1;
	private JTextPane jTextPane1;
	private JButton TButton1;
	private JButton EButton1;
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
		
		GroupLayout contentPaneLayout = new GroupLayout((JComponent)contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setVerticalGroup(contentPaneLayout.createSequentialGroup()
			.addContainerGap(28, 28)
			.addGroup(contentPaneLayout.createParallelGroup()
			    .addGroup(GroupLayout.Alignment.LEADING, contentPaneLayout.createSequentialGroup()
			        .addComponent(getSlaveButton1(), GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
			        .addGap(29))
			    .addComponent(getEButton1(), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE))
			.addGap(18)
			.addGroup(contentPaneLayout.createParallelGroup()
			    .addGroup(GroupLayout.Alignment.LEADING, contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			        .addComponent(getMasterButton(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
			        .addComponent(getSlaveButton2(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
			        .addComponent(getJButton1(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
			    .addComponent(getTButton1(), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
			.addGap(19)
			.addGroup(contentPaneLayout.createParallelGroup()
			    .addGroup(GroupLayout.Alignment.LEADING, contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			        .addComponent(getSlaveButton3(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
			        .addComponent(getSlaveButton4(), GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
			    .addComponent(getJButton1x(), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE))
			.addGap(93)
			.addComponent(getJTextPane1(), GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE)
			.addContainerGap(40, Short.MAX_VALUE));
		contentPaneLayout.setHorizontalGroup(contentPaneLayout.createSequentialGroup()
			.addContainerGap(65, 65)
			.addGroup(contentPaneLayout.createParallelGroup()
			    .addGroup(GroupLayout.Alignment.LEADING, contentPaneLayout.createSequentialGroup()
			        .addComponent(getSlaveButton2(), GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
			        .addGap(45)
			        .addGroup(contentPaneLayout.createParallelGroup()
			            .addComponent(getMasterButton(), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
			            .addGroup(GroupLayout.Alignment.LEADING, contentPaneLayout.createSequentialGroup()
			                .addGap(18)
			                .addComponent(getSlaveButton1(), GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
			                .addGap(16)))
			        .addGap(42)
			        .addComponent(getJButton1(), GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE))
			    .addGroup(contentPaneLayout.createSequentialGroup()
			        .addPreferredGap(getSlaveButton2(), getJTextPane1(), LayoutStyle.ComponentPlacement.INDENT)
			        .addGroup(contentPaneLayout.createParallelGroup()
			            .addComponent(getJTextPane1(), GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 340, GroupLayout.PREFERRED_SIZE)
			            .addGroup(GroupLayout.Alignment.LEADING, contentPaneLayout.createSequentialGroup()
			                .addGap(73)
			                .addComponent(getSlaveButton3(), GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
			                .addGap(59)
			                .addComponent(getSlaveButton4(), GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
			                .addGap(42)))
			        .addGap(28)))
			.addGap(206)
			.addGroup(contentPaneLayout.createParallelGroup()
			    .addGroup(contentPaneLayout.createSequentialGroup()
			        .addComponent(getJButton1x(), GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE))
			    .addGroup(contentPaneLayout.createSequentialGroup()
			        .addComponent(getTButton1(), GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
			    .addGroup(contentPaneLayout.createSequentialGroup()
			        .addComponent(getEButton1(), GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)))
			.addContainerGap(6, Short.MAX_VALUE));

		setContentPane(contentPane);
		contentPane.setPreferredSize(new java.awt.Dimension(480, 327));
		
//		scroll = new JScrollPane (jTextPane1); 
//		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		

		timer1.start();
		timer2.start();
		timer3.start();
		timer4.start();
	}
	
	private AbstractAction getCancelAction() {
		if(cancelAction == null) {
			cancelAction = new AbstractAction("Cancel", null) {
				public void actionPerformed(ActionEvent evt) {
					System.exit(0);
				}
			};
		}
		return cancelAction;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		  Object src = e.getSource();
		  

		  if(src == timer1) {
			  slaveButton1.setBackground(Color.DARK_GRAY);
			  slaveButton2.setBackground(Color.LIGHT_GRAY);
			  slaveButton3.setBackground(Color.DARK_GRAY);
			  slaveButton4.setBackground(Color.LIGHT_GRAY);
			  slaveButton5.setBackground(Color.DARK_GRAY);
		  }
		  if(src == timer2) {
			  slaveButton1.setBackground(Color.LIGHT_GRAY);
			  slaveButton2.setBackground(Color.DARK_GRAY);
			  slaveButton3.setBackground(Color.LIGHT_GRAY);
			  slaveButton4.setBackground(Color.DARK_GRAY);
			  slaveButton5.setBackground(Color.LIGHT_GRAY);
		  }
		  if(src == timer3) {
			  EButton1.setBackground(Color.RED);
			 		  }
		  if(src == timer4) {
			  EButton1.setBackground(Color.GREEN);
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
	
	private JButton getSlaveButton1() {
		if(slaveButton1 == null) {
			slaveButton1 = new JButton();
			slaveButton1.setText("slave1");
			//slaveButton1.setEnabled(false);
		}
		return slaveButton1;
	}
	
	private JButton getSlaveButton2() {
		if(slaveButton2 == null) {
			slaveButton2 = new JButton();
			slaveButton2.setText("slave2");
			//slaveButton2.setEnabled(false);
		}
		return slaveButton2;
	}
	
	private JButton getSlaveButton3() {
		if(slaveButton3 == null) {
			slaveButton3 = new JButton();
			slaveButton3.setText("slave3");
			//slaveButton3.setEnabled(false);
		}
		return slaveButton3;
	}
	
	private JButton getSlaveButton4() {
		if(slaveButton4 == null) {
			slaveButton4 = new JButton();
			slaveButton4.setText("slave4");
			//slaveButton4.setEnabled(false);
		}
		return slaveButton4;
	}

	  private JButton getJButton1() {
		  if(slaveButton5 == null) {
			  slaveButton5 = new JButton();
			  slaveButton5.setText("slave5");
		  }
		  return slaveButton5;
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
	  
	  private JButton getEButton1() {
		  if(EButton1 == null) {
			  EButton1 = new JButton();
			  EButton1.setText("Extract");
			  EButton1.setFont(new java.awt.Font("Aharoni",1,20));
		  }
		  return EButton1;
	  }
	  
	  private JButton getTButton1() {
		  if(TButton1 == null) {
			  TButton1 = new JButton();
			  TButton1.setText("Transform");
			  TButton1.setFont(new java.awt.Font("Aharoni",1,20));
		  }
		  return TButton1;
	  }
	  
	  private JButton getJButton1x() {
		  if(LButton1 == null) {
			  LButton1 = new JButton();
			  LButton1.setText("Load");
			  LButton1.setFont(new java.awt.Font("Aharoni",1,20));
		  }
		  return LButton1;
	  }
	  
	  private JTextPane getJTextPane1() {
		  if(jTextPane1 == null) {
			  jTextPane1 = new JTextPane();
			  jTextPane1.setText("DISTRIBUTED ETL");
			  jTextPane1.setBounds(20,	20, 50, 50);
			  
//			  JScrollPane scrolltxt = new JScrollPane(jTextPane1);
		  }
		  return jTextPane1;
	  }

	 
}
