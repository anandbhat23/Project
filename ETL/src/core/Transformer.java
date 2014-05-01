package core;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import protobuf.ProtoMessageConfig.ProtoMessage.Builder;
import transformer.TransformerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Transformer {

	private static final String CON_TRANS = "cat(1,2);plus(3,4,5)";
	private static final String REGEX = "";
	private static Pattern pattern = Pattern.compile("([a-z]+)\\((.+)\\)");
	
	public static List<Map<String, String>> applyTransformations(List<Map<String, String>> data, String transformation){
		
		List<Map<String, String>> output = Lists.newArrayList();
		String[] tokens = StringUtils.split(transformation, ";");
		int i = 0;
		
		for(Map<String, String> entry: data){
			List<Pair<String,String>> pairWiseData = convertEntry(entry);
			Map<String, String> transformedEntry = Maps.newLinkedHashMap();
			for(String token:tokens){
				Matcher matcher = pattern.matcher(token);
				if(StringUtils.isNumeric(token)){
					Pair<String,String> colPair = pairWiseData.get(Integer.parseInt(token));
					transformedEntry.put(colPair.getKey(), colPair.getValue());
				}else if (matcher.find()){
				    String oper = (matcher.group(1));
				    String[] cols = StringUtils.split(matcher.group(2), ",");
				    transformedEntry.put(matcher.group(0), TransformerFactory.execute(oper, cols, pairWiseData));
				}
			}
			output.add(transformedEntry);
		}
		return output;
	}

	private static List<Pair<String, String>> convertEntry(
			Map<String, String> entry) {
		List<Pair<String,String>> pairWiseData = Lists.newArrayList();
		for(Entry<String, String> e :entry.entrySet()){
			pairWiseData.add(Pair.of(e.getKey(), e.getValue()));
		}
		return  pairWiseData;
	}

	public static void buildMessage(Builder protoMessage) {
		// TODO Auto-generated method stub
		
	}
}
