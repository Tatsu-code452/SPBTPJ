package com.manage.helper.COMMON.Dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manage.helper.SPBTPJ1.Model.FilePathDto;

@Component
public class FilePathDao {
	private static final String FILE_PATH = "C:\\workspace\\web\\SPBTPJ\\data\\";
	private static final String FILE_NAME = "filePath.json";

	public List<FilePathDto> readAll() {
		List<FilePathDto> result;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			result = objectMapper.readValue(new File(StringUtils.concat(FILE_PATH, FILE_NAME)),
					new TypeReference<List<FilePathDto>>() {
					});
		} catch (IOException e) {
			e.printStackTrace();
			result = new ArrayList<FilePathDto>();
		}
		return result;
	}
}