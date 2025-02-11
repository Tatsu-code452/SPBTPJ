package com.manage.helper.SPBTPJ1.BusinessLogic.Logic;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.manage.helper.COMMON.BaseLogic;
import com.manage.helper.DAO.FilePathDao;
import com.manage.helper.DAO.DaoModel.FilePathDto;
import com.manage.helper.DAO.DaoModel.FilePathInfoDto;
import com.manage.helper.SPBTPJ1.Model.SPBTPJ1_InsertBDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SPBTPJ1_InsertLogic extends BaseLogic<SPBTPJ1_InsertBDto> {
	private final FilePathDao filePathDao;

	private List<FilePathDto> filePathList;

	@Override
	protected boolean loadData(SPBTPJ1_InsertBDto dto) {
		filePathList = filePathDao.readAll();
		return true;
	}

	@Override
	protected boolean createData(SPBTPJ1_InsertBDto dto) {
		setFilePathListToSave(addFilePathMap(dto, createFilePathMap()));
		return true;
	}

	@Override
	protected boolean saveData(SPBTPJ1_InsertBDto dto) {
		return filePathDao.writeAll(filePathList);
	}

	private Map<String, List<FilePathInfoDto>> createFilePathMap() {
		return filePathList.stream().collect(Collectors.toMap(FilePathDto::getGroupId, FilePathDto::getPathList));
	}

	private Map<String, List<FilePathInfoDto>> addFilePathMap(SPBTPJ1_InsertBDto dto,
			Map<String, List<FilePathInfoDto>> filePathMap) {
		filePathMap.computeIfAbsent(dto.getGroupId(), k -> new ArrayList<>()).add(new FilePathInfoDto(dto.getName(),
				dto.getPath(), URLEncoder.encode(dto.getPath(), StandardCharsets.UTF_8)));
		return filePathMap;
	}

	private void setFilePathListToSave(Map<String, List<FilePathInfoDto>> filePathMap) {
		filePathList = filePathMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
				.map(v -> new FilePathDto(v.getKey(), v.getValue())).collect(Collectors.toList());
	}
}
