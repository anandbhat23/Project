package common;

import static common.YamlLabel.EXPORTER;
import static common.YamlLabel.IMPORTER;
import static common.YamlLabel.TRANSFORMER;
import static common.YamlLabel.TYPE;
import static common.YamlLabel.TRANSFORM_OP;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.google.common.collect.Lists;

import core.ETLJob;
import core.Exporter;
import core.Importer;

public class YamlParser {

	public static List<ETLJob> parse(String yamlFile) {
		Yaml yaml = new Yaml();		
		List<ETLJob> etlJobs = Lists.newArrayList();
		try {
			InputStream is = new FileInputStream(new File(yamlFile));
			@SuppressWarnings("unchecked")
			Map<String, List<Map<String, Object>>> parameters = (Map<String, List<Map<String, Object>>>) yaml.load(is);
			List<Map<String, Object>> importerParamList = parameters.get(IMPORTER.getLabelName());
			//Exporting only to one destination
			String transformation = parameters.get(TRANSFORMER.getLabelName()).get(0).get(TRANSFORM_OP.getLabelName()).toString();
			Map<String, Object> exporterParams = parameters.get(EXPORTER.getLabelName()).get(0);
			
			Parser parser = ParserFactory.getParser((String)exporterParams.get(TYPE.getLabelName()));
			Exporter exporter = parser.createExporter(exporterParams);
			
			for(Map<String, Object> importerParams : importerParamList){
				parser = ParserFactory.getParser((String)importerParams.get(TYPE.getLabelName()));
				Importer importer = parser.createImporter(importerParams);
				ETLJob etlJob = new ETLJob(importer, exporter, transformation);
				etlJobs.add(etlJob);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return etlJobs;
	}
}
