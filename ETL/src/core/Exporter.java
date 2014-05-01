package core;

import java.util.List;
import java.util.Map;

import protobuf.ProtoMessageConfig.ProtoMessage;

public interface Exporter {
	
	public void buildMessage(ProtoMessage.Builder protoMessage);
	public void export(List<Map<String, String>> data) throws Exception;
}
