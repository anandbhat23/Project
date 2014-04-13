package mysql.utils;

import static common.YamlLabel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import protobuf.ConfigProtos.Configuration;
import protobuf.ConfigProtos.Exporter;
import protobuf.ConfigProtos.Importer;
import protobuf.ConfigProtos.Transformer;
import protobuf.ConfigProtos.Transformer;

import com.google.common.collect.Lists;

public class MySQLHelperProtobuf {

	@SuppressWarnings("unchecked")
	public static MySQLTask getMySQLTask(String config_file) {
		
		//Protobuf objects
		Importer.Builder importer = Importer.newBuilder();
		Exporter.Builder exporter = Exporter.newBuilder();
		Transformer.Builder transformer = Transformer.newBuilder();
		
		
		Yaml yaml = new Yaml();
		MySQLConnection sourceConn;
		MySQLConnection destinationConn;
		MySQLTask mySQLTask = null;
		try {
			InputStream is = new FileInputStream(new File(config_file));
			Map<String, ArrayList<Map<String, Object>>> map = (Map<String, ArrayList<Map<String, Object>>>) yaml
					.load(is);
			ArrayList<Map<String, Object>> importers = map.get(IMPORTER
					.getLabelName());
			ArrayList<Map<String, Object>> exporters = map.get(EXPORTER
					.getLabelName());
			ArrayList<Map<String, Object>> transformers = map.get(TRANSFORMER
					.getLabelName());
		
			String source = null;
			String username = null;
			String password = null;
			String table = null;
			Integer rowStart = null;
			Integer rowEnd = null;
			List<String> columns = Lists.newArrayList();
			List<String> transformerOps = Lists.newArrayList();
			
			// TODO Support multiple sources
			// for (Map<String, Object> source : sources) {
			Map<String, Object> importerObj = importers.get(0);
			
			source = (String) importerObj.get(SOURCE.getLabelName());
			importer.setSource(source);
			
			username = (String) importerObj.get(USERNAME.getLabelName());
			importer.setUsername(username);
			
			password = (String) importerObj.get(PASSWORD.getLabelName());
			importer.setPassword(password);
			
			table = (String) importerObj.get(TABLE.getLabelName());
			importer.setTable(table);
			
			columns = (List<String>) importerObj.get(COLUMNS.getLabelName());
			//importer.ad
			
			rowStart = (Integer) importerObj.get(ROW_START.getLabelName());
			importer.setRowStart(rowStart);
			
			rowEnd = (Integer) importerObj.get(ROW_END.getLabelName());
			importer.setRonwEnd(rowEnd);
			
			sourceConn = new MySQLConnection(source, username, password, table, columns, rowStart, rowEnd);
			
			Map<String, Object> exporterObj = exporters.get(0);
			rowStart = null;
			rowEnd = null;

			columns = (List<String>) exporterObj.get(COLUMNS.getLabelName());
			exporter.addAllColumn(columns);
			
			source = (String) exporterObj.get(SOURCE.getLabelName());
			exporter.setSource(source);
			
			username = (String) exporterObj.get(USERNAME.getLabelName());
			exporter.setUsername(username);
			
			password = (String) exporterObj.get(PASSWORD.getLabelName());
			exporter.setPassword(password);
			
			table = (String) exporterObj.get(TABLE.getLabelName());
			exporter.setTable(table);
			
			
			destinationConn = new MySQLConnection(source, username, password, table, columns, rowStart, rowEnd);
			
			Map<String, Object> transformerObj = transformers.get(0);
			transformerOps = (List<String>) transformerObj.get(CLASS.getLabelName());
			transformer.addAllTransformOp(transformerOps);
		
			importer.build();
			transformer.build();
			exporter.build();
			
			Configuration.Builder configuration = Configuration.newBuilder();
			configuration.setType("MySql");
			configuration.setImporter(importer);
			configuration.setTransformer(transformer);
			configuration.setExporter(exporter);
			configuration.build();
			//TODO: Change constructor you can pass configuration object directly
			
			mySQLTask = new MySQLTask(sourceConn, destinationConn, transformerOps.remove(0));
			// TODO Ensure Non null parameters
			// }

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		return mySQLTask;
	}  
}
