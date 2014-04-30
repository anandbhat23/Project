package core;

import static common.DataTypes.MYSQL;
import protobuf.ProtoMessageConfig;
import protobuf.ProtoMessageConfig.ProtoMessage;
import protobuf.ProtoMessageConfig.Importer.Builder;

public class ETLJob {

	private Importer importer;
	private String transformation;
	private Exporter exporter;
	boolean moreMessages;
	
	public ETLJob(Importer importer, Exporter exporter, String transformation) {
		super();
		this.importer = importer;
		this.transformation = transformation;
		this.exporter = exporter;
	}

	public Importer getImporter() {
		return importer;
	}

	public Exporter getExporter() {
		return exporter;
	}
	
	public String getTransformation() {
		return transformation;
	}
	
	public ProtoMessage getNextMessage(){
		ProtoMessage.Builder protoMessage = ProtoMessage.newBuilder();
		moreMessages = importer.buildMessage(protoMessage);
		protobuf.ProtoMessageConfig.Transformer.Builder transformer = ProtoMessageConfig.Transformer.newBuilder();
		transformer.setTransformOp(transformation);
		protoMessage.setTransformer(transformer);
		if(moreMessages){
			exporter.buildMessage(protoMessage);
			return protoMessage.build();
		}
		return null;
	}
}
