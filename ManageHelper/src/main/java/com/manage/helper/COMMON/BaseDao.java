package com.manage.helper.COMMON;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public abstract class BaseDao<T> {
	@NonNull
	protected String fileName;
	@NonNull
	protected TypeReference<T> typeRef;
	@NonNull
	protected TypeReference<List<T>> typeRefList;

	protected Class<T> type;

	public <U> void load(JpaRepository<T, U> repository) {
		try {
			CsvMapper csvMapper = new CsvMapper();
			CsvSchema csvSchema = CsvSchema.emptySchema().withHeader();
			MappingIterator<T> listIterator = csvMapper.readerFor(typeRef).with(csvSchema)
					.readValues(new File(fileName));
			List<T> dataList = listIterator.readAll();
			repository.saveAll(dataList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public <U> void export(JpaRepository<T, U> repository) {
		try {
			CsvMapper csvMapper = new CsvMapper();
			CsvSchema.Builder schemaBuilder = CsvSchema.builder();
			for (Field field : type.getDeclaredFields()) {
				schemaBuilder.addColumn(field.getName());
			}
			CsvSchema csvSchema = schemaBuilder.build().withHeader();
			List<T> dataList = repository.findAll();
			csvMapper.writerFor(typeRefList).with(csvSchema).writeValue(new File(fileName), dataList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
