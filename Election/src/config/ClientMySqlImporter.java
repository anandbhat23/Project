package config;

import utils.ClientParser;

public class ClientMySqlImporter extends ClientConfigImporter{
	private String username;
	private String password;
	private String tableName;
	private String[] columns;
	private Integer rowStart;
	private Integer rowEnd;
	
	public ClientMySqlImporter() {
		this.setImporterType(ClientConfigDataType.MYSQL);
	}
	
	public ClientMySqlImporter(String dataLocation) {
		this.setImporterType(ClientConfigDataType.MYSQL);
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
	
	public void setRowStart(Integer rowStart) {
		this.rowStart = rowStart;
	}
	
	public void setRowEnd(Integer rowEnd) {
		this.rowEnd = rowEnd;
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
	
	public Integer getRowStart() {
		return rowStart;
	}
	
	public Integer getRowEnd() {
		return rowEnd;
	}

}
