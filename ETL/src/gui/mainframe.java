package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;


public class mainframe extends JFrame {

	private JPanel contentPane, pane1;

	public void xyz() {
		
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainframe frame = new mainframe();
					frame.mainframe1(frame);
		
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public mainframe() {
	
		
		
	};
	/**
	 * Create the frame.
	 */
	public void mainframe1(final mainframe frame) {
		
		setTitle("Distributed ETL");
		setSize(1000,1000);
		setLocationRelativeTo(null);
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		pane1 = new JPanel();
		pane1.setBorder(new EmptyBorder(5, 5, 5, 5));
		pane1.setLayout(new BorderLayout(0, 0));
		frame.getContentPane().add(pane1);
		
		getContentPane().setLayout(new BorderLayout());
		final JLabel background=new JLabel(new ImageIcon("src/image.jpg"));
		getContentPane().add(background, BorderLayout.SOUTH);
		background.setLayout(new FlowLayout());
		
		JLabel l1=new JLabel("Distributed ETL");
		JButton b1=new JButton("Sign In");
		
		//b1.setBounds(40, 100, 100, 60);
		b1.setLocation(10, 10);
		
		background.add(l1);
		background.add(b1);
		
		frame.setVisible(true);
		
		
		b1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				contentPane.setVisible(false);
				setPane1();

			}
		});

	}
	
	public void setPane1() {

		setContentPane(pane1);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu config = new JMenu("File");
		menuBar.add(config);
		JMenuItem newconfig = new JMenuItem("New");
		config.add(newconfig);
		JMenuItem exit = new JMenuItem("exit");
		config.add(exit);
		
		
		JMenu start = new JMenu("Start");
		menuBar.add(start);
		
		JMenu History = new JMenu("History");
		menuBar.add(History);
		
		JMenu Help = new JMenu("Help");
		menuBar.add(Help);
		
		pane1.setBackground(Color.WHITE);
		
		class exitAction implements ActionListener {
			public void actionPerformed (ActionEvent e) {
				System.exit(0);
			}
		}
		
		class configAction implements ActionListener {
			public void actionPerformed (ActionEvent e) {
				
			//	pane1.setLayout(new GridLayout(10, 10, 5, 5));
				
				JLabel lb1 = new JLabel("Hello");
				JLabel lb2 = new JLabel("Hello000000");
				
				pane1.setComponentOrientation(ComponentOrientation.UNKNOWN);
				
				pane1.add(lb1);
				pane1.add(lb2);
			}
		}
		
		newconfig.addActionListener(new configAction());
		exit.addActionListener(new exitAction());
	}
}
