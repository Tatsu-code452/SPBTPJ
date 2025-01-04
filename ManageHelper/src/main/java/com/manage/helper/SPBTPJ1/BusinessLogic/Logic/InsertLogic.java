package com.manage.helper.SPBTPJ1.BusinessLogic.Logic;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.manage.helper.COMMON.BaseLogic;
import com.manage.helper.COMMON.Dao.FilePathDao;
import com.manage.helper.SPBTPJ1.Model.FilePathDto;
import com.manage.helper.SPBTPJ1.Model.InsertBDto;

@Component
public class InsertLogic extends BaseLogic<InsertBDto> {
	@Autowired
	FilePathDao filePathDao;

	@Override
	protected boolean loadData(InsertBDto dto) {
		boolean result = true;
		dto.setFilePathMap(filePathDao.readAll().stream()
				.collect(Collectors.toMap(FilePathDto::getGroup, FilePathDto::getPathList)));
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
		result = filePathDao.writeAll(dto.getFilePathMap());
		return result;
	}
}
