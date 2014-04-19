package common;

import http.HTTPParser;

import java.util.Collections;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import mysql.MySQLParser;

import com.google.common.collect.Maps;

public class ParserFactory {

	private static final Map<String, Parser> parsers;

	static {
		Map<String, Parser> myParsers = Maps.newHashMap();
		myParsers.put("MYSQL", new MySQLParser());
		myParsers.put("HTTP", new HTTPParser());
		parsers = Collections.unmodifiableMap(myParsers);
	}

	public static Parser getParser(String type) {
		// TODO Auto-generated method stub
		return  parsers.get(StringUtils.upperCase(type));
	}
}
