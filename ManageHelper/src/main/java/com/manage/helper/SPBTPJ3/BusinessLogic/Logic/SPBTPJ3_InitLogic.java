package com.manage.helper.SPBTPJ3.BusinessLogic.Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.manage.helper.COMMON.BaseLogic;
import com.manage.helper.DAO.FilePathDao;
import com.manage.helper.DAO.FilePathGroupDao;
import com.manage.helper.DAO.DaoModel.FilePathDto;
import com.manage.helper.DAO.DaoModel.FilePathGroupDto;
import com.manage.helper.DAO.DaoModel.FilePathInfoDto;
import com.manage.helper.SPBTPJ3.Model.SPBTPJ3_InitBDto;
import com.manage.helper.SPBTPJ3.ViewModel.SPBTPJ3_InnerTableData;
import com.manage.helper.SPBTPJ3.ViewModel.SPBTPJ3_TableData;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SPBTPJ3_InitLogic extends BaseLogic<SPBTPJ3_InitBDto> {
	private final FilePathDao filePathDao;
	private final FilePathGroupDao filePathGroupDao;

	private List<FilePathDto> filePathList;
	private List<FilePathGroupDto> filePathGroupList;

	@Override
	protected boolean loadData(SPBTPJ3_InitBDto dto) {
		filePathGroupList = filePathGroupDao.readAll();
		filePathList = filePathDao.readAll();
		return true;
	}

	@Override
	protected boolean createResponse(SPBTPJ3_InitBDto dto) {
		dto.getViewModel().setTableDataList(createTableDataList());
		return true;
	}

	private List<SPBTPJ3_TableData> createTableDataList() {
		return filePathGroupList.stream().map(this::createTableData).collect(Collectors.toList());
	}

	private SPBTPJ3_TableData createTableData(FilePathGroupDto filePathGroup) {
		return new SPBTPJ3_TableData(false, filePathGroup, createInnerTableDataList(filePathGroup.getGroupId()));
	}

	private List<SPBTPJ3_InnerTableData> createInnerTableDataList(String groupId) {
		List<FilePathInfoDto> filePathInfoList = createFilePathInfoList(groupId, filePathList);

		return IntStream.range(0, filePathInfoList.size())
				.mapToObj(index -> new SPBTPJ3_InnerTableData(false, index, filePathInfoList.get(index)))
				.collect(Collectors.toList());
	}

	private List<FilePathInfoDto> createFilePathInfoList(String groupId, List<FilePathDto> filePathList) {
		return filePathList.stream().filter(n -> groupId.equals(n.getGroupId())).map(FilePathDto::getPathList)
				.findFirst().orElse(new ArrayList<FilePathInfoDto>());
	}

}
