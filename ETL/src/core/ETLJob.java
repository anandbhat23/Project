package core;

import protobuf.ProtoMessageConfig.ProtoMessage;

public class ETLJob {

	private Importer importer;
	private Exporter exporter;
	boolean moreMessages;
	
	public ETLJob(Importer importer, Exporter exporter) {
		super();
		this.importer = importer;
		this.exporter = exporter;
	}

	public Importer getImporter() {
		return importer;
	}

	public Exporter getExporter() {
		return exporter;
	}
	
	public ProtoMessage getNextMessage(){
		ProtoMessage.Builder protoMessage = ProtoMessage.newBuilder();
		moreMessages = importer.buildMessage(protoMessage);		
		if(moreMessages){
			exporter.buildMessage(protoMessage);
			return protoMessage.build();
		}
		return null;
	}
}
