package com.manage.helper.Dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manage.helper.COMMON.CommonConst;
import com.manage.helper.Dao.DaoModel.FilePathDto;
import com.manage.helper.Dao.DaoModel.FilePathGroupDto;

@Component
public class FilePathDao {
	private static final String FILE_NAME = StringUtils.concat(CommonConst.FILE_PATH, CommonConst.FILE_NAME_FILE_PATH);

	public List<FilePathGroupDto> readAll() {
		List<FilePathGroupDto> result;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			result = objectMapper.readValue(new File(FILE_NAME), new TypeReference<List<FilePathGroupDto>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
			result = new ArrayList<FilePathGroupDto>();
		}
		return result;
	}

	public boolean writeAll(Map<String, List<FilePathDto>> filePathMap) {
		boolean result = true;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(new File(FILE_NAME),
					filePathMap.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(v -> {
						FilePathGroupDto workDto = new FilePathGroupDto();
						workDto.setGroup(v.getKey());
						workDto.setPathList(v.getValue());
						return workDto;
					}).collect(Collectors.toList()));
		} catch (IOException e) {
			e.printStackTrace();
			result = false;
		}

		return result;
	}
}