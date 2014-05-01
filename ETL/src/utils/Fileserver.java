package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Fileserver {
	public static BufferedReader getFile(String fileUrl) throws IOException
	{
		URL url = new URL(fileUrl);
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		return new BufferedReader(new InputStreamReader(in, encoding));
	}
}