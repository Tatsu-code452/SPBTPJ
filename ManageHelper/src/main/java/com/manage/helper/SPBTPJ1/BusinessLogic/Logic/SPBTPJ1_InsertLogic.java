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
		boolean result = true;
		filePathList = filePathDao.readAll();
		return result;
	}

	@Override
	protected boolean createData(SPBTPJ1_InsertBDto dto) {
		boolean result = true;

		Map<String, List<FilePathInfoDto>> filePathMap = filePathList.stream()
				.collect(Collectors.toMap(FilePathDto::getGroupId, FilePathDto::getPathList));
		FilePathInfoDto path = new FilePathInfoDto(dto.getName(), dto.getPath(),
				URLEncoder.encode(dto.getPath(), StandardCharsets.UTF_8));
		if (!filePathMap.containsKey(dto.getGroupId())) {
			filePathMap.put(dto.getGroupId(), new ArrayList<FilePathInfoDto>());
		}
		filePathMap.get(dto.getGroupId()).add(path);
		filePathList = filePathMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
				.map(v -> new FilePathDto(v.getKey(), v.getValue())).collect(Collectors.toList());
		return result;
	}

	@Override
	protected boolean saveData(SPBTPJ1_InsertBDto dto) {
		boolean result = true;
		result = filePathDao.writeAll(filePathList);
		return result;
	}
}
