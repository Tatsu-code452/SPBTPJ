package com.manage.helper.SPBTPJ1.BusinessLogic.Logic;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.manage.helper.COMMON.BaseLogic;
import com.manage.helper.DAO.FilePathGroupDao;
import com.manage.helper.DAO.DaoModel.FilePathGroupDto;
import com.manage.helper.SPBTPJ1.Model.SPBTPJ1_InsertGroupBDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SPBTPJ1_InsertGroupLogic extends BaseLogic<SPBTPJ1_InsertGroupBDto> {
	private final FilePathGroupDao filePathGroupDao;

	private List<FilePathGroupDto> filePathGroupList;

	@Override
	protected boolean loadData(SPBTPJ1_InsertGroupBDto dto) {
		boolean result = true;
		filePathGroupList = filePathGroupDao.readAll();
		return result;
	}

	@Override
	protected boolean createData(SPBTPJ1_InsertGroupBDto dto) {
		boolean result = true;
		filePathGroupList.add(new FilePathGroupDto(getGroupId(), dto.getGroup(), filePathGroupList.size() + 1));
		filePathGroupList = createSortedFilePathGroupList();
		return result;
	}

	@Override
	protected boolean saveData(SPBTPJ1_InsertGroupBDto dto) {
		return filePathGroupDao.writeAll(filePathGroupList);
	}

	private List<FilePathGroupDto> createSortedFilePathGroupList() {
		return filePathGroupList.stream().sorted(Comparator.comparing(FilePathGroupDto::getGroupId))
				.collect(Collectors.toList());
	}

	private String getGroupId() {
		return String.valueOf(getMissingGroupId(createSortedGroupIdList()));
	}

	private List<Integer> createSortedGroupIdList() {
		return filePathGroupList.stream().map(n -> Integer.valueOf(n.getGroupId())).sorted()
				.collect(Collectors.toList());
	}

	private Integer getMissingGroupId(List<Integer> sortedGroupIdList) {
		return IntStream.rangeClosed(1, sortedGroupIdList.size() + 1).filter(id -> !sortedGroupIdList.contains(id))
				.findFirst().orElse(1);
	}

}
