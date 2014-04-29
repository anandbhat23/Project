package mysql;

import static common.DataTypes.MYSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import protobuf.ProtoMessageConfig;
import protobuf.ProtoMessageConfig.Exporter.Builder;
import protobuf.ProtoMessageConfig.ProtoMessage;
import core.Exporter;

public class MySQLExporter implements Exporter {

	private PreparedStatement preparedStatement = null;
	private Connection connection = null;
	private MySQLData mySQLData;

	public MySQLExporter(MySQLData mySQLData) {
		super();
		this.mySQLData = mySQLData;
	}

	@Override
	public void buildMessage(ProtoMessage.Builder protoMessage) {
		Builder exporter = ProtoMessageConfig.Exporter.newBuilder();
		exporter.setType(MYSQL.name());
		exporter.setLocation(mySQLData.getLocation());
		exporter.setUsername(mySQLData.getUsername());
		exporter.setPassword(mySQLData.getPassword());
		exporter.setTable(mySQLData.getTable());
		exporter.addAllColumns(mySQLData.getColumns());
		protoMessage.setExporter(exporter);
	}

	@Override
	public void export(List<Map<String, String>> dataList) {
		try {

			testJDBCDriver();
			getConnection();
			for (Map<String, String> data : dataList) {
				Collection<String> values = data.values();
				preparedStatement = connection.prepareStatement(String.format(
						"insert ignore into %s values (%s)", mySQLData.getTable(),
						StringUtils.repeat("?", ", ", data.size())));
				int i = 1;
				for (String value : values) {
					preparedStatement.setString(i, value);
					i++;
				}
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
			connection = DriverManager.getConnection(
					String.format("jdbc:mysql://%s/", mySQLData.getLocation()),
					mySQLData.getUsername(), mySQLData.getPassword());
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
		}
	}

}
