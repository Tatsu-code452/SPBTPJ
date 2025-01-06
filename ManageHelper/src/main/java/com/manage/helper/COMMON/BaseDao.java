package com.manage.helper.COMMON;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseDao<T> {
	protected String FILE_NAME;
	protected TypeReference<T> typeRef;
	protected TypeReference<List<T>> typeRefList;

	public void setFileName(String fileName) {
		this.FILE_NAME = fileName;
	}

	public void setTypeRef(TypeReference<T> typeRef) {
		this.typeRef = typeRef;
	}

	public void setTypeRefList(TypeReference<List<T>> typeRefList) {
		this.typeRefList = typeRefList;
	}

	public List<T> readAll() {
		List<T> result;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			result = objectMapper.readValue(new File(FILE_NAME), typeRefList);
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
			objectMapper.writeValue(new File(FILE_NAME), t);
		} catch (IOException e) {
			e.printStackTrace();
			result = false;
		}

		return result;
	}

}
