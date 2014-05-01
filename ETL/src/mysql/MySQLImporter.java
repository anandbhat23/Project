package mysql;

import static common.DataTypes.MYSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import protobuf.ProtoMessageConfig;
import protobuf.ProtoMessageConfig.Importer.Builder;
import protobuf.ProtoMessageConfig.ProtoMessage;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import core.Importer;

public class MySQLImporter implements Importer{

	private MySQLData mySQLData;
	private Connection connection = null;
	private static Statement statement = null;
	private static final int MAX_ROWS_PER_QUERY = 100;
	private static int currentRow;
	
	public MySQLImporter(MySQLData mySQLData) {
		super();
		currentRow = mySQLData.getRowStart();
		this.mySQLData = mySQLData;
	}

	@Override
	public boolean buildMessage(ProtoMessage.Builder protoMessage) {
		
		int diff = (mySQLData.getRowEnd() - currentRow);
		int rowEnd = 0;
		if(diff < 0){
			return false;
		}else if(diff > MAX_ROWS_PER_QUERY){
			rowEnd = currentRow + MAX_ROWS_PER_QUERY;
		}else{
			rowEnd = mySQLData.getRowEnd();
		}
		Builder importer = ProtoMessageConfig.Importer.newBuilder();
		importer.setType(MYSQL.name());
		importer.setLocation(mySQLData.getLocation());
		importer.setUsername(mySQLData.getUsername());
		importer.setPassword(mySQLData.getPassword());
		importer.setTable(mySQLData.getTable());
		importer.addAllColumns(mySQLData.getColumns());
		importer.setRowStart(currentRow);
		importer.setRowEnd(rowEnd);
		protoMessage.setImporter(importer);
		currentRow += MAX_ROWS_PER_QUERY;
		return true;
		
	}

	@Override
	public List<Map<String, String>> importData(ProtoMessage protoMessage) throws SQLException {
		List<Map<String, String>> dataList = Lists.newArrayList();
		try {
			testJDBCDriver();
			getConnection();
			ResultSet resultSet = getRows(mySQLData.getRowStart(), mySQLData.getRowEnd());
			while(resultSet.next()){
				Map<String, String> dataMap = Maps.newLinkedHashMap();
				for(String col:mySQLData.getColumns()){
					dataMap.put(col, resultSet.getString(col));
				}
				dataList.add(dataMap);
			}
			
		} finally{
			connection.close();
		}
		return dataList;
	}
	
	private void testJDBCDriver() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return;
		}
		System.out.println("MySQL JDBC Driver Registered!");
	}
	
	private void getConnection() {
		try {
			connection = DriverManager.getConnection(String.format("jdbc:mysql://%s/", mySQLData.getLocation()), mySQLData.getUsername(), mySQLData.getPassword());
			statement = connection.createStatement();
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
		}
	}
	
	private ResultSet getRows(int rowStart, int rowEnd) throws SQLException{
		ResultSet resultSet = null;
		resultSet = statement.executeQuery(String.format("select * from %s", mySQLData.getTable()));
		return resultSet;
	}
}
