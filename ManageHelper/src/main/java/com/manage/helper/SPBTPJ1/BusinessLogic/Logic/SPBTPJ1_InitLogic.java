package com.manage.helper.SPBTPJ1.BusinessLogic.Logic;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.manage.helper.COMMON.BaseLogic;
import com.manage.helper.DAO.FilePathDao;
import com.manage.helper.DAO.FilePathGroupDao;
import com.manage.helper.DAO.DaoModel.FilePathDto;
import com.manage.helper.DAO.DaoModel.FilePathGroupDto;
import com.manage.helper.SPBTPJ1.Model.SPBTPJ1_InitBDto;
import com.manage.helperUtil.CollectorUtils;
import com.manage.helperUtil.ComparatorUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SPBTPJ1_InitLogic extends BaseLogic<SPBTPJ1_InitBDto> {
	private final FilePathDao filePathDao;
	private final FilePathGroupDao filePathGroupDao;

	private List<FilePathDto> filePathList;
	private List<FilePathGroupDto> filePathGroupList;

	@Override
	protected boolean loadData(SPBTPJ1_InitBDto dto) {
		filePathGroupList = filePathGroupDao.readAll();
		filePathList = filePathDao.readAll();
		return true;
	}

	@Override
	protected boolean createResponse(SPBTPJ1_InitBDto dto) {
		dto.getViewModel().setFilePathGroupMap(createFilePathGroupMap());
		dto.getViewModel().setFilePathList(createFilePathList(dto.getViewModel().getFilePathGroupMap()));
		return true;
	}

	private Map<String, String> createFilePathGroupMap() {
		return filePathGroupList.stream()
				.sorted(ComparatorUtils.comparingNaturalOrderNullsLast(FilePathGroupDto::getOrder))
				.collect(CollectorUtils.collectLinkedHashMap(FilePathGroupDto::getGroupId, FilePathGroupDto::getGroup));
	}

	private List<FilePathDto> createFilePathList(Map<String, String> filePathGroupMap) {
		return filePathList.stream().sorted((n1, n2) -> ComparatorUtils
				.comparingByOrderdList(createOrderedGroupIdList(filePathGroupMap), n1.getGroupId(), n2.getGroupId()))
				.collect(Collectors.toList());
	}

	private List<String> createOrderedGroupIdList(Map<String, String> filePathGroupMap) {
		return filePathGroupMap.keySet().stream().collect(Collectors.toList());
	}
}
