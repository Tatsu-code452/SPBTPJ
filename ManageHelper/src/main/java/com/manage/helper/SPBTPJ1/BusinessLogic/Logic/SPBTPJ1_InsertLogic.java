package com.manage.helper.SPBTPJ1.BusinessLogic.Logic;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.manage.helper.COMMON.BaseLogic;
import com.manage.helper.Dao.FilePathDao;
import com.manage.helper.Dao.DaoModel.FilePathDto;
import com.manage.helper.SPBTPJ1.Model.SPBTPJ1_InsertBDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SPBTPJ1_InsertLogic extends BaseLogic<SPBTPJ1_InsertBDto> {
	private final FilePathDao filePathDao;

	private Map<String, List<FilePathDto.FilePathInfoDto>> filePathMap;

	@Override
	protected boolean loadData(SPBTPJ1_InsertBDto dto) {
		boolean result = true;
		filePathMap = filePathDao.readAll().stream()
				.collect(Collectors.toMap(FilePathDto::getGroupId, FilePathDto::getPathList));
		return result;
	}

	@Override
	protected boolean createData(SPBTPJ1_InsertBDto dto) {
		boolean result = true;

		FilePathDto.FilePathInfoDto path = new FilePathDto.FilePathInfoDto();
		path.setName(dto.getName());
		path.setPath(dto.getPath());
		path.setEncodePath(URLEncoder.encode(dto.getPath(), StandardCharsets.UTF_8));
		if (!filePathMap.containsKey(dto.getGroupId())) {
			filePathMap.put(dto.getGroupId(), new ArrayList<FilePathDto.FilePathInfoDto>());
		}
		filePathMap.get(dto.getGroupId()).add(path);

		return result;
	}

	@Override
	protected boolean saveData(SPBTPJ1_InsertBDto dto) {
		boolean result = true;
		result = filePathDao.writeAll(filePathMap.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(v -> {
			FilePathDto workDto = new FilePathDto();
			workDto.setGroupId(v.getKey());
			workDto.setPathList(v.getValue());
			return workDto;
		}).collect(Collectors.toList()));
		return result;
	}
}
