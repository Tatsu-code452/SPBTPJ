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
import com.manage.helper.Dao.DaoModel.FilePathGroupDto;
import com.manage.helper.SPBTPJ1.Model.InsertBDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InsertLogic extends BaseLogic<InsertBDto> {
	private final FilePathDao filePathDao;

	private Map<String, List<FilePathDto>> filePathMap;

	@Override
	protected boolean loadData(InsertBDto dto) {
		boolean result = true;
		filePathMap = filePathDao.readAll().stream()
				.collect(Collectors.toMap(FilePathGroupDto::getGroup, FilePathGroupDto::getPathList));
		return result;
	}

	@Override
	protected boolean createData(InsertBDto dto) {
		boolean result = true;

		String group = dto.getGroup();
		FilePathDto path = new FilePathDto();
		path.setName(dto.getPath());
		path.setPath(dto.getPath());
		path.setEncodePath(URLEncoder.encode(dto.getPath(), StandardCharsets.UTF_8));
		if (!filePathMap.containsKey(group)) {
			filePathMap.put(group, new ArrayList<FilePathDto>());
		}
		filePathMap.get(group).add(path);

		return result;
	}

	@Override
	protected boolean saveData(InsertBDto dto) {
		boolean result = true;
		result = filePathDao.writeAll(filePathMap);
		return result;
	}
}
