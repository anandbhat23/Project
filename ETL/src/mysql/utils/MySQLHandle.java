package mysql.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class MySQLHandle {

	private String source;
	private String username;
	private String password;
	private String table;
	private List<String> columns;
	private Connection connection = null;
	private static Statement statement = null;
	private PreparedStatement preparedStatement = null;
	
	public MySQLHandle(MySQLConnection mySQLConnection) {
		super();
		this.source = mySQLConnection.getSource();
		this.username = mySQLConnection.getUsername();
		this.password = mySQLConnection.getPassword();
		this.table = mySQLConnection.getTable();
		this.password = mySQLConnection.getPassword();
		testJDBCDriver();
		getConnection();
	}
	
	public String getSource() {
		return source;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public ResultSet getRow(int row) throws SQLException{
		ResultSet resultSet = null;
		resultSet = statement.executeQuery(String.format("select %s from %s limit %s,1", StringUtils.join(columns, ','), table, row));
		return resultSet;
	}
	
	public void export(String values) throws SQLException{
		preparedStatement = connection.prepareStatement("insert into ? values (?)");
		preparedStatement.setString(1, table);
		preparedStatement.setString(2, values);
		preparedStatement.executeUpdate(); 
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
			connection = DriverManager.getConnection(String.format("jdbc:mysql://%s/","%s", "%s", source, username, password));
			statement = connection.createStatement();
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
		}
	}
}