import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import protobuf.ConfigProtos.Configuration;
import protobuf.ConfigProtos.Exporter;
import protobuf.ConfigProtos.Importer;
import protobuf.ConfigProtos.Transformer;

public class HttpETL implements ETL {

	private static final long serialVersionUID = 1L;
	BufferedReader br = null;
	Configuration config;

	public void setUp(String config_file) {

		//br = Helper.getReader(config_file);
		config = Helper.getReader(config_file);
		String src = config.getImporter().getSource();
		try {
			br = new BufferedReader(new FileReader(src));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// HTTPImporter

	@Override
	public Configuration importer() {

		try {
			String line = br.readLine();
			if (line == null) {
				br.close();
				return null;
			} else {
				// ETLMessage m = new ETLMessage(MessageType.MsgData, line, null);

				config =  parseData(line);

				return config;

			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Configuration transformer(Configuration m) {
		// We are free to do something here!
		return m;
	}

	@Override
	public void exporter(Configuration m) {
		
		// CASE1: HTTP to HTTP 
		if (!m.getExporter().getDestination().equals(null)) {

			int length = (int) m.getImporter().getDataCount();
			int i=0;
			
			while(i<length) {
				String data = (String) m.getImporter().getData(i);
				try {
					File file = new File(m.getExporter().getDestination());

					// if file doesnt exists, then create it
					if (!file.exists()) {
						file.createNewFile();
					}	

					FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(data);
					bw.write("\n");
					bw.close();
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i++;
			}

			
		}

		// Go to any data destination.
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Configuration parseData(String line){

		int length=0, i = 0;

		Importer.Builder importer = config.getImporter().toBuilder();
		Exporter.Builder exporter = config.getExporter().toBuilder();
		Transformer.Builder transformer = config.getTransformer().toBuilder();

		String[] data = line.split("\\s+");
		length = data.length;
		
		importer.clearData();
		
		while(length > i) {

			importer.addData(data[i]);
			i++;
		}

		Configuration.Builder configuration = Configuration.newBuilder();
		configuration.setType("HTTP");
		configuration.setImporter(importer);
		configuration.setTransformer(transformer);
		configuration.setExporter(exporter);
		configuration.build();

		return	configuration.build();
	}
}
