package com.manage.helper.COMMON;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class BaseJsonDao<T> {
	protected String fileName;
	protected Class<T> clazz;

	private final ObjectMapper objectMapper = new ObjectMapper()
			.setSerializationInclusion(JsonInclude.Include.NON_NULL);

	public List<T> readAll() {
		try {
			return objectMapper.readValue(new File(fileName),
					objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
		} catch (IOException | ClassCastException e) {
			e.printStackTrace();
			return new ArrayList<T>();
		}
	}

	public boolean writeAll(List<T> t) {
		try {
			objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), t);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}
