package com.manage.helper.SPBTPJ1.BusinessLogic.Logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.manage.helper.COMMON.BaseLogic;
import com.manage.helper.COMMON.Dao.FilePathDao;
import com.manage.helper.SPBTPJ1.Model.InitBDto;

@Component
public class InitLogic extends BaseLogic<InitBDto> {
	@Autowired
	FilePathDao filePathDao;

	@Override
	protected boolean createResponse(InitBDto dto) {
		boolean result = true;
		dto.getViewModel().setFilePathList(filePathDao.readAll());
		return result;
	}

}
