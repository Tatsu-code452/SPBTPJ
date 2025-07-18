package com.manage.helper.SPBTPJ2.BusinessLogic.Logic;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.manage.helper.COMMON.BaseLogic;
import com.manage.helper.DAO.FilePathGroupDao;
import com.manage.helper.DAO.DaoModel.FilePathGroupDto;
import com.manage.helper.SPBTPJ2.Model.SPBTPJ2_InitBDto;
import com.manage.helperUtil.ComparatorUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SPBTPJ2_InitLogic extends BaseLogic<SPBTPJ2_InitBDto> {
	private final FilePathGroupDao filePathGroupDao;

	private List<FilePathGroupDto> filePathGroupList;

	@Override
	protected boolean loadData(SPBTPJ2_InitBDto dto) {
		filePathGroupList = filePathGroupDao.readAll();
		return true;
	}

	@Override
	protected boolean createResponse(SPBTPJ2_InitBDto dto) {
		dto.getViewModel().setFilePathGroupList(createFilePathGroupList());
		return true;
	}

	private List<FilePathGroupDto> createFilePathGroupList() {
		return filePathGroupList.stream().sorted(ComparatorUtils.ascendingWithNullsLast(FilePathGroupDto::getOrder))
				.collect(Collectors.toList());
	}
}
