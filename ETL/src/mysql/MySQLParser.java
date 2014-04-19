package mysql;

import static common.YamlLabel.COLUMNS;
import static common.YamlLabel.LOCATION;
import static common.YamlLabel.PASSWORD;
import static common.YamlLabel.ROW_END;
import static common.YamlLabel.ROW_START;
import static common.YamlLabel.TABLE;
import static common.YamlLabel.USERNAME;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.google.protobuf.Descriptors.FieldDescriptor;
import common.Parser;

import core.Exporter;
import core.Importer;

public class MySQLParser implements Parser{

	
	@SuppressWarnings("unchecked")
	@Override
	public Importer createImporter(Map<String, Object> params) {
		MySQLData mySQLData = new MySQLData((String)params.get(LOCATION.getLabelName())
								, (String)params.get(USERNAME.getLabelName())
								, (String)params.get(PASSWORD.getLabelName())
								, (String)params.get(TABLE.getLabelName())
								, (List<String>)params.get(COLUMNS.getLabelName())
								, (Integer)params.get(ROW_START.getLabelName())
								, (Integer)params.get(ROW_END.getLabelName()));
		Importer mySQLImporter = new MySQLImporter(mySQLData);
		return mySQLImporter;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Exporter createExporter(Map<String, Object> params) {
		MySQLData mySQLData = new MySQLData((String)params.get(LOCATION.getLabelName())
				, (String)params.get(USERNAME.getLabelName())
				, (String)params.get(PASSWORD.getLabelName())
				, (String)params.get(TABLE.getLabelName())
				, (List<String>)params.get(COLUMNS.getLabelName())
				, 0
				, 0);
		Exporter mySQLExporter = new MySQLExporter(mySQLData);
		return mySQLExporter;
	}

	@Override
	public Importer createImporterFromProtoMessage(
			Map<FieldDescriptor, Object> params) {
		Map<String, Object> importerParams = Maps.newHashMap();
		for(Entry<FieldDescriptor, Object> entry: params.entrySet()){
			importerParams.put(entry.getKey().getName(), entry.getValue());
		}
		return createImporter(importerParams);	}

	@Override
	public Exporter createExporterFromProtoMessage(
			Map<FieldDescriptor, Object> params) {
		Map<String, Object> exporterParams = Maps.newHashMap();
		for(Entry<FieldDescriptor, Object> entry: params.entrySet()){
			exporterParams.put(entry.getKey().getName(), entry.getValue());
		}
		return createExporter(exporterParams);
	}

}