package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import org.omg.CORBA.INTERNAL;

import utils.General;

public class ClientGUI {
	private JFrame frame;
	private Container container;
	private JPanel selectPanel;
	private JPanel detailPanel;
	private JPanel imexPanel;
	private JPanel importPanel;
	private JPanel exportPanel;
	private JPanel startButtonPanel;
	
	private JButton startButton;

	// CONSTANT
	private final Integer WINDOW_WIDTH = 1200;
	private final Integer WINDOW_HEIGHT = 800;
	private final Integer CENTER_HEIGHT = 1100;
	private final Integer BOTTOM_HEIGHT = WINDOW_HEIGHT - CENTER_HEIGHT;
	private final Integer SELECTP_WIDTH = 300;
	private final Integer SELECTP_HEIGHT = WINDOW_HEIGHT;
	private final Integer DETAILP_WIDTH = WINDOW_WIDTH - SELECTP_WIDTH;
	private final Integer DETAILP_HEIGHT = WINDOW_HEIGHT;
	private final Integer IMEXP_WIDTH = DETAILP_WIDTH;
	private final Integer IMEXP_HEIGHT = CENTER_HEIGHT;
	private final Integer STARTBUTP_WIDTH = DETAILP_WIDTH;
	private final Integer STARTBUTP_HEIGHT = BOTTOM_HEIGHT;
	private final Integer IMPORTP_WIDTH = DETAILP_WIDTH;
	private final Integer IMPORTP_HEIGHT = IMEXP_HEIGHT/2;
	private final Integer EXPORTP_WIDTH = DETAILP_WIDTH;
	private final Integer EXPORTP_HEIGHT = IMEXP_HEIGHT/2;
	private final Integer START_BUTTON_WIDTH = 100;
	private final Integer START_BUTTON_HEIGHT = 40;
	
	
	private final String lABEL_APP = "Distributed ETL";
	private final String[] DATATYPE_OPTION = { "HTTP", "MYSQL", "HBASE" };
	private final String DATATYPE_SRC_LABEL = "Src Type";
	private final String DATATYPE_DEST_LABEL = "Dest Type";
	private final String BUTTON_START_TEXT = "Start";
	private final String LABEL_IMPORT = "Import";
	private final String LABEL_EXPORT = "Export";

	public ClientGUI() {
		General.log("Initialting: client app");

		// TODO: instantiate client class

		frame = new JFrame(lABEL_APP);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		container = frame.getContentPane();
		BoxLayout containerLayout = new BoxLayout(container, BoxLayout.X_AXIS);
		container.setLayout(containerLayout);

		selectPanel = new JPanel();
		selectPanel.setOpaque(true);
		selectPanel.setBackground(Color.WHITE);
		selectPanel.setBorder(BorderFactory.createTitledBorder("Select Data Types"));
		selectPanel.setSize(SELECTP_WIDTH, SELECTP_HEIGHT);
		selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.Y_AXIS));

		// final JPanel comboPanel = new JPanel();
//		JLabel comboDatatypeSrc = new JLabel(DATATYPE_SRC_LABEL);
		JComboBox dataTypesSrc = new JComboBox(DATATYPE_OPTION);
		dataTypesSrc.setBorder(BorderFactory.createTitledBorder(DATATYPE_SRC_LABEL));
//		JLabel comboDatatypeDest = new JLabel(DATATYPE_DEST_LABEL);
		JComboBox dataTypesDest = new JComboBox(DATATYPE_OPTION);
		dataTypesDest.setBorder(BorderFactory.createTitledBorder(DATATYPE_DEST_LABEL));

		detailPanel = new JPanel();
		detailPanel.setOpaque(true);
		detailPanel.setBackground(Color.WHITE);
        detailPanel.setBorder(
            BorderFactory.createTitledBorder("Detail Configuration"));
		detailPanel.setSize(DETAILP_WIDTH,DETAILP_HEIGHT);
		detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
		
		imexPanel = new JPanel();
		imexPanel.setOpaque(true);
		imexPanel.setBackground(Color.WHITE);
		imexPanel.setBorder(BorderFactory.createTitledBorder("Import And Export"));
		imexPanel.setSize(IMEXP_WIDTH, IMEXP_HEIGHT);
		imexPanel.setLayout(new BorderLayout());
		
		startButtonPanel = new JPanel();
		startButtonPanel.setOpaque(true);
		startButtonPanel.setBackground(Color.WHITE);
		startButtonPanel.setBorder(BorderFactory.createTitledBorder("Execute"));
		startButtonPanel.setSize(STARTBUTP_WIDTH,STARTBUTP_HEIGHT);
		startButtonPanel.setLayout(new BorderLayout());
		
		
		importPanel = new JPanel();
		importPanel.setOpaque(true);
		importPanel.setBackground(Color.WHITE);
		importPanel.setBorder(BorderFactory.createTitledBorder("Import Config"));
		importPanel.setSize(IMPORTP_WIDTH, IMEXP_HEIGHT);
		importPanel.setLayout(new BoxLayout(importPanel, BoxLayout.Y_AXIS));
		
		exportPanel = new JPanel();
		exportPanel.setOpaque(true);
		exportPanel.setBackground(Color.WHITE);
		exportPanel.setBorder(BorderFactory.createTitledBorder("Export Config"));
		exportPanel.setSize(EXPORTP_WIDTH, EXPORTP_HEIGHT);
		exportPanel.setLayout(new BoxLayout(exportPanel, BoxLayout.Y_AXIS));
		
		startButton = new JButton(BUTTON_START_TEXT);
		startButton.setSize(START_BUTTON_WIDTH, START_BUTTON_HEIGHT);
		startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		JLabel importLabel = new JLabel(LABEL_IMPORT);
		JLabel exportLabel = new JLabel(LABEL_EXPORT);
		
		
		
//		selectPanel.add(comboDatatypeSrc);
		selectPanel.add(dataTypesSrc);
//		selectPanel.add(comboDatatypeDest);
		selectPanel.add(dataTypesDest);
		
		importPanel.add(importLabel);
		exportPanel.add(exportLabel);
		
		imexPanel.add(importPanel, BorderLayout.NORTH);
		imexPanel.add(exportPanel, BorderLayout.CENTER);
		imexPanel.add(startButtonPanel, BorderLayout.SOUTH);
		
//		startButtonPanel.add(startButton, BorderLayout.EAST);
		
		detailPanel.add(imexPanel, BorderLayout.NORTH);
		detailPanel.add(startButtonPanel, BorderLayout.SOUTH);
				
		container.add(selectPanel);
		container.add(detailPanel);

	}

	public static void main(String[] args) {
		new ClientGUI();
	}
}
