package mysql.utils;

import static common.YamlLabel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.google.common.collect.Lists;

public class MySQLHelper {

	@SuppressWarnings("unchecked")
	public static MySQLTask getMySQLTask(String config_file) {
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
			String transformerClass;
			
			// TODO Support multiple sources
			// for (Map<String, Object> source : sources) {
			Map<String, Object> importer = importers.get(0);
			rowStart = (Integer) importer.get(ROW_START.getLabelName());
			rowEnd = (Integer) importer.get(ROW_END.getLabelName());
			columns = (List<String>) importer.get(COLUMNS.getLabelName());
			source = (String) importer.get(SOURCE.getLabelName());
			username = (String) importer.get(USERNAME.getLabelName());
			password = (String) importer.get(PASSWORD.getLabelName());
			table = (String) importer.get(TABLE.getLabelName());
			sourceConn = new MySQLConnection(source, username, password, table, columns, rowStart, rowEnd);
			
			Map<String, Object> exporter = exporters.get(0);
			rowStart = null;
			rowEnd = null;
			columns = (List<String>) exporter.get(COLUMNS.getLabelName());
			source = (String) exporter.get(SOURCE.getLabelName());
			username = (String) exporter.get(USERNAME.getLabelName());
			password = (String) exporter.get(PASSWORD.getLabelName());
			table = (String) importer.get(TABLE.getLabelName());
			destinationConn = new MySQLConnection(source, username, password, table, columns, rowStart, rowEnd);
			
			Map<String, Object> transformer = transformers.get(0);
			transformerClass = (String) transformer.get(CLASS.getLabelName());
			
			mySQLTask = new MySQLTask(sourceConn, destinationConn, transformerClass);
			// TODO Ensure Non null parameters
			// }

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mySQLTask;
	}  
}
