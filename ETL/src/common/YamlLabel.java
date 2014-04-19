package common;

public enum YamlLabel {
	IMPORTER("importer"),
	EXPORTER("exporter"),
	TYPE("type"),
	TRANSFORMER("transformer"),
	CLASS("class"),
	LOCATION("location"),
	USERNAME("username"),
	PASSWORD("password"),
	TABLE("table"),
	COLUMNS("columns"),
	ROW_START("rowStart"),
  	ROW_END("rowEnd"),
	DATA("data");
	
	private String lableName;

	YamlLabel(String labelName){
		this.lableName= labelName;
	}
	public String getLabelName(){
		return this.lableName;
	}
}
