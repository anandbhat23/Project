package view;

public class Constants {
	// CONSTANT
	public final static Integer WINDOW_WIDTH = 1200;
	public final static Integer WINDOW_HEIGHT = 800;
	public final static Integer CENTER_HEIGHT = 1100;
	public final static Integer BOTTOM_HEIGHT = WINDOW_HEIGHT - CENTER_HEIGHT;
	public final static Integer SELECTP_WIDTH = 300;
	public final static Integer SELECTP_HEIGHT = WINDOW_HEIGHT;
	public final static Integer DETAILP_WIDTH = WINDOW_WIDTH - SELECTP_WIDTH;
	public final static Integer DETAILP_HEIGHT = WINDOW_HEIGHT;
	public final static Integer IMEXP_WIDTH = DETAILP_WIDTH;
	public final static Integer IMEXP_HEIGHT = CENTER_HEIGHT;
	public final static Integer STARTBUTP_WIDTH = DETAILP_WIDTH;
	public final static Integer STARTBUTP_HEIGHT = BOTTOM_HEIGHT;
	public final static Integer IMPORTP_WIDTH = DETAILP_WIDTH;
	public final static Integer IMPORTP_HEIGHT = IMEXP_HEIGHT/2;
	public final static Integer EXPORTP_WIDTH = DETAILP_WIDTH;
	public final static Integer EXPORTP_HEIGHT = IMEXP_HEIGHT/2;
	public final static Integer START_BUTTON_WIDTH = 200;
	public final static Integer START_BUTTON_HEIGHT = 100;
	public final static Integer NEW_BUTTON_WIDTH = 200;
	public final static Integer NEW_BUTTON_HEIGHT = 80;
	public final static Integer REMOVE_BUTTON_WIDTH = 200;
	public final static Integer REMOVE_BUTTON_HEIGHT = 80;
	public final static Integer CHOOSE_BUTTON_WIDTH = 200;
	public final static Integer CHOOSE_BUTTON_HEIGHT = 80;
	public final static Integer CONFIG_LIST_WIDTH = WINDOW_WIDTH;
	public final static Integer CONFIG_LIST_HEIGHT = WINDOW_HEIGHT/3;
	public final static Integer TOP_PADDING_HEIGHT = 50;
	public final static Integer CONFIGLIST_VISIBLE_ROW_COUNT = 11;
	
	public final static String LABEL_APP = "Distributed ETL";
	public final static String[] DATATYPE_OPTION = { "HTTP", "MYSQL", "HBASE" };
	public final static String DATATYPE_SRC_LABEL = "Src Type";
	public final static String DATATYPE_DEST_LABEL = "Dest Type";
	public final static String BUTTON_START_TEXT = "Start";
	public final static String BUTTON_NEW_TEXT = "New";
	public final static String BUTTON_REMOVE_TEXT = "Remove";
	public final static String BUTTON_CHOOSE_TEXT = "Choose from file";
	public final static String LABEL_IMPORT = "Import";
	public final static String LABEL_EXPORT = "Export";
	public final static String MENU_FILE = "File";
	public final static String MENU_FILE_NEW = "New";
	public final static String MENU_HELP = "Help";
	public final static String MENU_HELP_ABOUT = "About";
	public final static String MENU_HELP_HELP = "Help";
	public final static String LIST_CONFIG_LABEL = "Config File List";
}
