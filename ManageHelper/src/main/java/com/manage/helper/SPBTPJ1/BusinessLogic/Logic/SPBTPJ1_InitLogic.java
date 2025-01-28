package com.manage.helper.SPBTPJ1.BusinessLogic.Logic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.manage.helper.COMMON.BaseLogic;
import com.manage.helper.Dao.FilePathDao;
import com.manage.helper.Dao.FilePathGroupDao;
import com.manage.helper.Dao.DaoModel.FilePathDto;
import com.manage.helper.Dao.DaoModel.FilePathGroupDto;
import com.manage.helper.SPBTPJ1.Model.SPBTPJ1_InitBDto;

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
		boolean result = true;
		filePathGroupList = filePathGroupDao.readAll();
		filePathList = filePathDao.readAll();
		return result;
	}

	@Override
	protected boolean createResponse(SPBTPJ1_InitBDto dto) {
		boolean result = true;
		dto.getViewModel()
				.setFilePathGroupMap(filePathGroupList.stream()
						.sorted(Comparator.comparing(FilePathGroupDto::getOrder,
								Comparator.nullsLast(Comparator.naturalOrder())))
						.collect(Collectors.toMap(FilePathGroupDto::getGroupId, FilePathGroupDto::getGroup,
								(e1, e2) -> e1, LinkedHashMap::new)));

		List<String> orderedGroupIdList = dto.getViewModel().getFilePathGroupMap().keySet().stream()
				.collect(Collectors.toList());
		dto.getViewModel().setFilePathList(
				filePathList.stream().sorted((n1, n2) -> Integer.compare(orderedGroupIdList.indexOf(n1.getGroupId()),
						orderedGroupIdList.indexOf(n2.getGroupId()))).collect(Collectors.toList()));

		return result;
	}

}
