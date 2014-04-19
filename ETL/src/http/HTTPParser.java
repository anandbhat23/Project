package http;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.google.protobuf.Descriptors.FieldDescriptor;

import common.Parser;
import core.Exporter;
import core.Importer;
import static common.YamlLabel.LOCATION;

public class HTTPParser implements Parser{

	@Override
	public Importer createImporter(Map<String, Object> params) {
		HTTPData httpData = new HTTPData((String)params.get(LOCATION.getLabelName()));
		Importer httpImporter = new HTTPImporter(httpData);
		return httpImporter;
	}

	@Override
	public Exporter createExporter(Map<String, Object> params) {
		HTTPData httpData = new HTTPData((String)params.get(LOCATION.getLabelName()));
		Exporter httpExporter = new HTTPExporter(httpData);
		return httpExporter;
	}

	@Override
	public Importer createImporterFromProtoMessage(
			Map<FieldDescriptor, Object> params) {
		Map<String, Object> importerParams = Maps.newHashMap();
		for(Entry<FieldDescriptor, Object> entry: params.entrySet()){
			importerParams.put(entry.getKey().getName(), entry.getValue());
		}
		return createImporter(importerParams);
	}

	@Override
	public Exporter createExporterFromProtoMessage(
			Map<FieldDescriptor, Object> params) {
		Map<String, Object> exporterParams = Maps.newHashMap();
		for(Entry<FieldDescriptor, Object> entry: params.entrySet()){
			exporterParams.put(entry.getKey().getFullName(), entry.getValue());
		}
		return createExporter(exporterParams);
	}

}
