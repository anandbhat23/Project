package http;

import static common.DataTypes.HTTP;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
<<<<<<< HEAD
import java.io.InputStreamReader;
=======
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
>>>>>>> e8a92d69c40ede578ead20d916ba6f2c631c7b9b
import java.net.URL;
import java.util.List;
import java.util.Map;

import protobuf.ProtoMessageConfig;
import protobuf.ProtoMessageConfig.Importer.Builder;
import protobuf.ProtoMessageConfig.ProtoMessage;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import core.Importer;

public class HTTPImporter implements Importer{

	private BufferedReader br;
	private HTTPData httpData;
	
	public HTTPImporter(HTTPData httpData) throws Exception {
		super();
		this.httpData = httpData;
		try {
<<<<<<< HEAD
			br = new BufferedReader(new InputStreamReader( new URL ("http://"+httpData.getLocation()).openStream()));
		} catch (Exception e) {
=======
			InputStream in = new URL("http://" + httpData.getLocation()).openStream();
			br = new BufferedReader(new InputStreamReader(in));
		} catch (FileNotFoundException e) {
>>>>>>> e8a92d69c40ede578ead20d916ba6f2c631c7b9b
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean buildMessage(ProtoMessage.Builder protoMessage) {
		Builder importer = ProtoMessageConfig.Importer.newBuilder();
		importer.setType(HTTP.name());
		importer.setLocation(httpData.getLocation());
		try {
			int length=0, i = 0;
			String line = br.readLine();
			if (line == null) {
				br.close();
				return false;
			} else {
				String[] data = line.split("\\s+");
				length = data.length;
				
				while(length > i) {
					importer.addData(data[i]);
					i++;
				}
				protoMessage.setImporter(importer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public List<Map<String, String>> importData(ProtoMessage protoMessage) {
		List<Map<String, String>> dataList = Lists.newArrayList();
		Map<String, String> dataMap = Maps.newLinkedHashMap();
		int length = (int) protoMessage.getImporter().getDataCount();
		int i=0;		
		while(i<length) {
			String data = (String) protoMessage.getImporter().getData(i);
			dataMap.put(((Integer)i).toString(), data);
			i++;
		}
		dataList.add(dataMap);
		return dataList;
	}
}
