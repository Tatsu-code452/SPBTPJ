package com.manage.helper.SPBTPJ1.BusinessLogic.Logic;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.manage.helper.COMMON.BaseLogic;
import com.manage.helper.Dao.FilePathDao;
import com.manage.helper.Dao.FilePathGroupDao;
import com.manage.helper.Dao.DaoModel.FilePathGroupDto;
import com.manage.helper.SPBTPJ1.Model.InitBDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InitLogic extends BaseLogic<InitBDto> {
	private final FilePathGroupDao filePathGroupDao;
	private final FilePathDao filePathDao;

	@Override
	protected boolean loadData(InitBDto dto) {
		boolean result = true;
		dto.getViewModel().setFilePathGroupMap(filePathGroupDao.readAll().stream()
				.collect(Collectors.toMap(FilePathGroupDto::getGroupId, FilePathGroupDto::getGroup)));
		dto.getViewModel().setFilePathList(filePathDao.readAll());
		return result;
	}
}
