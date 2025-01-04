package com.manage.helper.SPBTPJ1.BusinessLogic.Logic;

import org.springframework.stereotype.Component;

import com.manage.helper.COMMON.BaseLogic;
import com.manage.helper.Dao.FilePathDao;
import com.manage.helper.SPBTPJ1.Model.InitBDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InitLogic extends BaseLogic<InitBDto> {
	private final FilePathDao filePathDao;

	@Override
	protected boolean createResponse(InitBDto dto) {
		boolean result = true;
		dto.getViewModel().setFilePathList(filePathDao.readAll());
		return result;
	}

}
