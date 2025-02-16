package com.manage.helper.SPBTPJ3.BusinessLogic.Logic;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.manage.helper.COMMON.BaseLogic;
import com.manage.helper.DAO.FilePathDao;
import com.manage.helper.DAO.DaoModel.FilePathDto;
import com.manage.helper.DAO.DaoModel.FilePathInfoDto;
import com.manage.helper.SPBTPJ3.Model.SPBTPJ3_UpdateBDto;
import com.manage.helper.SPBTPJ3.ViewModel.SPBTPJ3_InnerTableData;
import com.manage.helper.SPBTPJ3.ViewModel.SPBTPJ3_TableData;
import com.manage.helperUtil.ComparatorUtils;

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
		filePathList = createFilePathList(dto.getViewModel().getTableDataList());
		return true;
	}

	@Override
	protected boolean saveData(SPBTPJ3_UpdateBDto dto) {
		return filePathDao.writeAll(filePathList);
	}

	private List<FilePathDto> createFilePathList(List<SPBTPJ3_TableData> tableDataList) {
		return tableDataList.stream().filter(n -> !n.isChecked()).map(n -> createFilePath(n))
				.collect(Collectors.toList());
	}

	private FilePathDto createFilePath(SPBTPJ3_TableData tableData) {
		return new FilePathDto(tableData.getFilePathGroup().getGroupId(),
				createSortedFilePathInfoList(tableData.getFilePathInfoList()));
	}

	private List<FilePathInfoDto> createSortedFilePathInfoList(List<SPBTPJ3_InnerTableData> filePathInfoList) {
		return filePathInfoList.stream().filter(n -> !n.isChecked())
				.sorted(ComparatorUtils.comparingNaturalOrderNullsLast(SPBTPJ3_InnerTableData::getOrder))
				.map(n -> n.getFilePathInfo()).collect(Collectors.toList());
	}

}
