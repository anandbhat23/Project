package common;

import java.util.Map;

import com.google.protobuf.Descriptors.FieldDescriptor;

import core.Exporter;
import core.Importer;

public interface Parser {

	public Importer createImporter(Map<String, Object> params) throws Exception;
	public Importer createImporterFromProtoMessage(Map<FieldDescriptor, Object> params) throws Exception;
	public Exporter createExporter(Map<String, Object> params);
	public Exporter createExporterFromProtoMessage(Map<FieldDescriptor, Object> params);
}
