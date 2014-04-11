package mysql.utils;

import java.io.Serializable;

public class MySQLTask implements Serializable{
	
	private static final long serialVersionUID = 7726256276155429354L;
	
	private MySQLConnection source;
	private MySQLConnection destination;
	private String transformer;
	private int part;
	
	public MySQLTask(MySQLConnection source, MySQLConnection destination,
			String transformer) {
		super();
		this.source = source;
		this.destination = destination;
		this.transformer = transformer;
	}
	public MySQLConnection getSource() {
		return source;
	}
	public MySQLConnection getDestination() {
		return destination;
	}
	public String getTransformer() {
		return transformer;
	}
	public int getPart() {
		return part;
	}
	public void setPart(int part) {
		this.part = part;
	}
}
