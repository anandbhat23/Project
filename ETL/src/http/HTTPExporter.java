package http;

import static common.DataTypes.HTTP;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import protobuf.ProtoMessageConfig;
import protobuf.ProtoMessageConfig.ProtoMessage.Builder;
import core.Exporter;

public class HTTPExporter implements Exporter {

	private HTTPData httpData;

	public HTTPExporter(HTTPData httpData) {
		super();
		this.httpData = httpData;
	}

	@Override
	public void export(List<Map<String, String>> dataList) {
		BufferedWriter bw = null;
		try {
			File file = new File("resources/final");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);
			for (Map<String, String> data : dataList) {
				for (Entry<String, String> entry : data.entrySet()) {
					bw.write(entry.getValue());
				}
				bw.write("\n");
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void buildMessage(Builder protoMessage) {
		
		protobuf.ProtoMessageConfig.Exporter.Builder exporter = ProtoMessageConfig.Exporter.newBuilder();
		exporter.setType(HTTP.name());
		exporter.setDestination(httpData.getLocation());
		protoMessage.setExporter(exporter);
	}
}
