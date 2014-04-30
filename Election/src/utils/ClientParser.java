package utils;

import java.util.Arrays;

public class ClientParser {
	public static String[] columnStringParser(String[] columns, String columnString) {
		//Split on "\n" to store column names
				String eol = System.getProperty("line.separator");
				System.out.printf("After Split - %s%n", Arrays.toString(columnString.split(eol)));
				return columnString.split(eol);
	}
}
