package com.manage.helper.SPBTPJ3.BusinessLogic.Logic;

import java.util.List;

import org.springframework.stereotype.Component;

import com.manage.helper.COMMON.BaseLogic;
import com.manage.helper.DAO.FilePathDao;
import com.manage.helper.DAO.DaoModel.FilePathDto;
import com.manage.helper.SPBTPJ3.Model.SPBTPJ3_UpdateBDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SPBTPJ3_UpdateLogic extends BaseLogic<SPBTPJ3_UpdateBDto> {
	private final FilePathDao filePathDao;

	private List<FilePathDto> filePathList;

	@Override
	protected boolean loadData(SPBTPJ3_UpdateBDto dto) {
		filePathList = filePathDao.readAll();
		return true;
	}

	@Override
	protected boolean createData(SPBTPJ3_UpdateBDto dto) {
		return true;
	}

	@Override
	protected boolean saveData(SPBTPJ3_UpdateBDto dto) {
		return filePathDao.writeAll(filePathList);
	}

}
