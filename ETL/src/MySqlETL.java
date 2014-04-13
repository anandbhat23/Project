import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import mysql.utils.MySQLHandle;
import mysql.utils.MySQLHelper;
import mysql.utils.MySQLTask;

public class MySqlETL implements ETL {

	private static final long serialVersionUID = 1L;
	private MySQLTask mySQLTask;
	private static int MAX_PARTS = 2;//Should be Max number of alive slaves
	private int part;
	private MySQLHandle sourceHandle = null;
	private MySQLHandle destHandle = null;
	private int rowStart = 0;
	private int rowEnd = 0;
	private int currentRow = 0;
	

	@Override
	public void setUp(String conf) {
		part = 0;
		mySQLTask = MySQLHelper.getMySQLTask(conf);
		
	}
	
	public void setUpSlave(MySQLTask mySQLTask) {
		this.mySQLTask = mySQLTask;
		part = mySQLTask.getPart();
		sourceHandle = new MySQLHandle(mySQLTask.getSource());
		destHandle = new MySQLHandle(mySQLTask.getDestination());
		rowStart = mySQLTask.getSource().getRowStart();
		rowEnd = mySQLTask.getSource().getRowEnd();
		int partSize = (rowEnd - rowStart)/MAX_PARTS;
		rowStart = partSize * part;
		currentRow = rowStart;
		rowEnd = rowStart + partSize;
	}
	
	@Override
	public ETLMessage importer() {
		if(part < MAX_PARTS){
			mySQLTask.setPart(part);
			ETLMessage m = new ETLMessage(MessageType.MsgData, mySQLTask, null);
			part++;
			return m;
		}
		return null;
	}
	
	public ETLMessage importerSlave() {
		if(currentRow < rowEnd){
			try {
				ResultSet result = sourceHandle.getRow(currentRow++);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			return null;
		}
		return null;
	}

	@Override
	public ETLMessage transformer(ETLMessage m) {
		// TODO Implement transformer if necessary
		System.out.println("LocalImporting the data");
		return m;
	}

	@Override
	public void exporter(ETLMessage m) {
		// TODO Generate values String
		System.out.println("Exporting the data");
	}
}