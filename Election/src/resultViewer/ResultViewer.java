package resultViewer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class ResultViewer {
	
	private Connection connection = null;
	private static Statement statement = null;
	
	@SuppressWarnings("unchecked")
	public String getData(String yamlFile) throws SQLException{
		Yaml yaml = new Yaml();
		String displayString = null;
		boolean MySqlFlag=false;
		String path = "/home/madhuri/workspace_DS/Project/ETL/resources/";
		String path1= "/home/madhuri/workspace_DS/Project/ETL/";
		
		try {
			InputStream is = new FileInputStream(new File(path + yamlFile));
			Map<String, List<Map<String, Object>>> parameters = (Map<String, List<Map<String, Object>>>) yaml.load(is);
			Map<String, Object> exporterParams = parameters.get("exporter").get(0);
			
			if(exporterParams.get("type").toString().equalsIgnoreCase("HTTP")){
				String location = (String)exporterParams.get("location");
				BufferedReader br = new BufferedReader(new FileReader(path1 + location));
				displayString = createHTTPDisplayString(br, location);
			}else{
				MySqlFlag=true;
				String location = (String)exporterParams.get("location");
				String username = (String)exporterParams.get("username");
				String password = (String)exporterParams.get("password");
				String table = (String)exporterParams.get("table");
				List<String> columns = (List<String>)exporterParams.get("columns");
				testJDBCDriver();
				getConnection(location, username, password);
				ResultSet resultSet = getRows(table);
				displayString = createMySQLDisplayString(resultSet, columns);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(MySqlFlag==true)
				connection.close();
		}
		return displayString;
	}
	
	private String createHTTPDisplayString(BufferedReader br, String location) throws IOException {
		StringBuilder builder = new StringBuilder();
		String line = null;
		builder.append("\nLocation of file: " + location + "\n");
		while((line = br.readLine()) != null){
			builder.append(line);
			builder.append("\n");
		}
		br.close();
		return builder.toString();
	}

	private String createMySQLDisplayString(ResultSet resultSet, List<String> columns) throws SQLException {
		StringBuilder builder = new StringBuilder();
		for(String column :columns){
			builder.append(String.format("%15s\t", column));
		}
		builder.append("\n");
		while(resultSet.next()){
			for(String column :columns){
				builder.append(String.format("%15s\t", resultSet.getString(column)));
			}
			builder.append("\n");
		}
		return builder.toString();
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
	
	private void getConnection(String location, String username, String password) {
		try {
			connection = DriverManager.getConnection(String.format("jdbc:mysql://%s/", location), username, password);
			statement = connection.createStatement();
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
		}
	}
	
	private ResultSet getRows(String table) throws SQLException{
		ResultSet resultSet = null;
		resultSet = statement.executeQuery(String.format("select * from %s", table));
		return resultSet;
	}
}
