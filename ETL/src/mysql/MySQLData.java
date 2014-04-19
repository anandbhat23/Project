package mysql;

import java.util.List;

import common.Data;

public class MySQLData implements Data{

	private String location;
	private String username;
	private String password;
	private String table;
	private List<String> columns;
	private int rowStart;
	private int rowEnd;
	
	public MySQLData(String location, String username, String password,
			String table, List<String> columns, int rowStart, int rowEnd) {
		super();
		this.location = location;
		this.username = username;
		this.password = password;
		this.table = table;
		this.columns = columns;
		this.rowStart = rowStart;
		this.rowEnd = rowEnd;
	}

	public String getLocation() {
		return location;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getTable() {
		return table;
	}

	public List<String> getColumns() {
		return columns;
	}

	public int getRowStart() {
		return rowStart;
	}

	public int getRowEnd() {
		return rowEnd;
	}	
}
