package transformer;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

public class TransformerFactory {

	public static String execute(String oper, String[] cols, List<Pair<String, String>> pairWiseData) {
		if(oper.equalsIgnoreCase("avg")){
			return calculateAverage(cols, pairWiseData);
		}else if(oper.equalsIgnoreCase("sum")){
			return calculateSum(cols, pairWiseData);
		}
		return null;
	}
	
	private static String calculateAverage(String[] cols, List<Pair<String, String>> pairWiseData){
		Integer sum = (Integer.parseInt(calculateSum(cols, pairWiseData))/cols.length); 
		return sum.toString();
	}
	
	private static String calculateSum(String[] cols, List<Pair<String, String>> pairWiseData){
		int sum = 0;
		for(String col:cols){
			Pair<String, String> colPair = pairWiseData.get(Integer.parseInt(col));
			sum += Integer.parseInt(colPair.getValue());
		}
		return Integer.toString(sum);
	}

}
