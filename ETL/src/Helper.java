import static common.YamlLabel.*;


import java.io.BufferedReader;
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

import com.google.common.collect.Lists;

public class Helper {

	static Configuration.Builder configuration = Configuration.newBuilder();
	
	@SuppressWarnings("unchecked")
	public static Configuration getReader(String config_file) {

		//Protobuf objects
		Importer.Builder importer = Importer.newBuilder();
		Exporter.Builder exporter = Exporter.newBuilder();
		Transformer.Builder transformer = Transformer.newBuilder();

		Yaml yaml = new Yaml();
		BufferedReader br = null;
		try {
			InputStream is = new FileInputStream(new File(config_file));
			@SuppressWarnings("unchecked")
			Map<String, ArrayList<Map<String, Object>>> map = (Map<String, ArrayList<Map<String, Object>>>) yaml.load(is);
			ArrayList<Map<String, Object>> importers = map.get(IMPORTER
					.getLabelName());
			ArrayList<Map<String, Object>> exporters = map.get(EXPORTER
					.getLabelName());
			ArrayList<Map<String, Object>> transformers = map.get(TRANSFORMER
					.getLabelName());
		
			String source = null;
			String dest = null;
			String username = null;
			String password = null;
			String table = null;
			Integer rowStart = null;
			Integer rowEnd = null;
			List<String> columns = Lists.newArrayList();
			String transformerOps = null;

			//Importer
			Map<String, Object> importerObj = importers.get(0);
			
			source = (String) importerObj.get(SOURCE.getLabelName());
			importer.setSource(source);
			
			//Exporter
			Map<String, Object> exporterObj = exporters.get(0);
			
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
			
			dest = (String) exporterObj.get(DESTINATION.getLabelName());
			exporter.setDestination(dest);
			
			//Transformer
			Map<String, Object> transformerObj = transformers.get(0);
			transformerOps = (String) transformerObj.get(CLASS.getLabelName());
			transformer.setTransformOp(transformerOps);
		
			importer.build();
			transformer.build();
			exporter.build();
			
			
			configuration.setType("HTTP");
			configuration.setImporter(importer);
			configuration.setTransformer(transformer);
			configuration.setExporter(exporter);
			configuration.build();
			
//			String file = null;
//			for (Map<String, Object> inmap : srcmap) {
//				file = (String) inmap.get("src");
//			}
//			br = new BufferedReader(new FileReader(file));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return configuration.build();

	}

}
