package common;

public enum YamlLabel {
	IMPORTER("importer"),
	EXPORTER("exporter"),
	TRANSFORMER("transformer"),
	CLASS("class"),
	SOURCE("src"),
	USERNAME("username"),
	PASSWORD("password"),
	TABLE("table"),
	COLUMNS("columns"),
	ROW_START("rowStart"),
  	ROW_END("rowEnd");

	private String lableName;

	YamlLabel(String labelName){
		this.lableName= labelName;
	}
	public String getLabelName(){
		return this.lableName;
	}
}
