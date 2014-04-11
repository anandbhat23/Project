package mysql.utils;

import java.io.Serializable;
import java.util.List;

public class MySQLConnection implements Serializable{

	private static final long serialVersionUID = 4698184193912926844L;
	private String source;
	private String username;
	private String password;
	private String table;
	private List<String> columns;
	private int rowStart;
	private int rowEnd;
	public MySQLConnection(String source, String username, String password,
			String table, List<String> columns, int rowStart, int rowEnd) {
		super();
		this.source = source;
		this.username = username;
		this.password = password;
		this.table = table;
		this.columns = columns;
		this.rowStart = rowStart;
		this.rowEnd = rowEnd;
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
