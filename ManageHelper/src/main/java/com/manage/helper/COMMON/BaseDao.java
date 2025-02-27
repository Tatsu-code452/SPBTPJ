package com.manage.helper.COMMON;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
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

	public List<T> readAll() {
		List<T> result;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			result = objectMapper.readValue(new File(fileName), typeRefList);
		} catch (IOException | ClassCastException e) {
			e.printStackTrace();
			result = new ArrayList<T>();
		}
		return result;
	}

	public boolean writeAll(List<T> t) {
		boolean result = true;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), t);
		} catch (IOException e) {
			e.printStackTrace();
			result = false;
		}

		return result;
	}

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
