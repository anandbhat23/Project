package utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import config.Configuration;
import config.ConnectionParameters;

public class YamlParser {

	private static final String CONNECTIONS = "Configuration";
	private static final String NAME = "Name";
	private static final String IP = "IP";
	private static final String PORT = "Port";
	private static final String DEFAULT_MASTER = "DefaultMaster";
	
	@SuppressWarnings("unchecked")
	public static Configuration readYaml(String fileUrl) throws IOException {
		Yaml yaml = new Yaml();
		String filecontents = Fileserver.getFile(fileUrl);
		InputStream in = new ByteArrayInputStream(filecontents.getBytes());
		try {
			Map<String, Object> yamlData = (Map<String, Object>) yaml.load(in);
			Configuration configuration = new Configuration();
			// 1. Extracting connection parameters
			List<Map<String, Object>> connections = (List<Map<String, Object>>) yamlData.get(CONNECTIONS);
			for (Map<String, Object> connection : connections) {
				ConnectionParameters connectionParameters = new ConnectionParameters(
						connection.get(IP), connection.get(PORT));
				configuration.addConnections((String) connection.get(NAME),
						connectionParameters);
			}
			return configuration;

		} catch (Exception e) {
			throw new IOException("Yaml is incorrectly defined");
		}
	}
}