package com.manage.helper.SPBTPJ1.BusinessLogic.Logic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manage.helper.COMMON.BaseLogic;
import com.manage.helper.SPBTPJ1.Model.FilePathDto;
import com.manage.helper.SPBTPJ1.Model.InsertBDto;

@Component
public class InsertLogic extends BaseLogic<InsertBDto> {

	@Override
	protected boolean loadData(InsertBDto dto) {
		boolean result = true;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			dto.setFilePathMap(objectMapper.readValue(new File("C:\\workspace\\web\\SPBTPJ\\data\\filePath.json"),
					new TypeReference<List<FilePathDto>>() {
					}).stream().collect(Collectors.toMap(FilePathDto::getGroup, FilePathDto::getPathList)));
		} catch (IOException e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	@Override
	protected boolean createData(InsertBDto dto) {
		boolean result = true;

		String group = dto.getGroup();
		String path = dto.getPath();

		if (!dto.getFilePathMap().containsKey(group)) {
			dto.getFilePathMap().put(group, new ArrayList<String>());
		}
		dto.getFilePathMap().get(group).add(path);

		return result;
	}

	@Override
	protected boolean saveData(InsertBDto dto) {
		boolean result = true;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.writeValue(new File("C:\\workspace\\web\\SPBTPJ\\data\\filePath.json"),
					dto.getFilePathMap().entrySet().stream().sorted(Map.Entry.comparingByKey()).map(v -> {
						FilePathDto workDto = new FilePathDto();
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
