package config;

import utils.ClientParser;

public class ClientMySqlExporter extends ClientConfigExporter{
	private String username;
	private String password;
	private String tableName;
	private String[] columns;
	
	public ClientMySqlExporter() {
		this.setExporterType(ClientConfigDataType.MYSQL);
	}
	
	public ClientMySqlExporter(String dataLocation) {
		this.setExporterType(ClientConfigDataType.MYSQL);
		this.setLocation(dataLocation);
	}
	
	public void setUserName(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setTable(String tableName) {
		this.tableName = tableName;
	}
	
	public void setColumns(String columnString) {
		columns = ClientParser.columnStringParser(columns, columnString);
	}
	
	public String getUserName() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getTable() {
		return tableName;
	}
	
	public String[] getColumns() {
		return columns;
	}
	
}
