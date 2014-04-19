package core;

import java.util.List;
import java.util.Map;

import protobuf.ProtoMessageConfig.ProtoMessage;

public interface Importer {
	public boolean buildMessage(ProtoMessage.Builder protoMessage);
	public List<Map<String, String>> importData(ProtoMessage protoMessage);
}
