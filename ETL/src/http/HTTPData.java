package http;

import common.Data;

public class HTTPData implements Data{

	private String location;

	public HTTPData(String location) {
		super();
		this.location = location;
	}

	public String getLocation() {
		return location;
	}
}
