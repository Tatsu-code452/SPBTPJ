package com.manage.helper.SPBTPJ2.BusinessLogic.Logic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.manage.helper.COMMON.BaseLogic;
import com.manage.helper.DAO.FilePathGroupDao;
import com.manage.helper.DAO.DaoModel.FilePathGroupDto;
import com.manage.helper.SPBTPJ2.Model.SPBTPJ2_UpdateBDto;
import com.manage.helper.SPBTPJ2.ViewModel.SPBTPJ2_TableInputData;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SPBTPJ2_UpdateLogic extends BaseLogic<SPBTPJ2_UpdateBDto> {
	private final FilePathGroupDao filePathGroupDao;

	private List<FilePathGroupDto> filePathGroupList;

	@Override
	protected boolean loadData(SPBTPJ2_UpdateBDto dto) {
		boolean result = true;
		filePathGroupList = filePathGroupDao.readAll();
		return result;
	}

	@Override
	protected boolean createData(SPBTPJ2_UpdateBDto dto) {
		boolean result = true;
		filePathGroupList = createSaveFilePathGroupList(dto);
		return result;
	}

	@Override
	protected boolean saveData(SPBTPJ2_UpdateBDto dto) {
		boolean result = true;
		result = filePathGroupDao.writeAll(filePathGroupList);
		return result;
	}

	private List<FilePathGroupDto> createSaveFilePathGroupList(SPBTPJ2_UpdateBDto dto) {
		List<FilePathGroupDto> result = new ArrayList<FilePathGroupDto>();

		Map<String, FilePathGroupDto> filePathGroupMap = createFilePathGroupMap();

		List<SPBTPJ2_TableInputData> sortedInputOrderList = getSortedInputOrderList(dto.getInputGroupList());

		// 入力後の順番で、削除対象外のデータをリストに追加
		sortedInputOrderList.stream().forEach(n -> {
			if (!n.isChecked()) {
				FilePathGroupDto work = filePathGroupMap.get(n.getGroupId());
				work.setOrder(result.size() + 1);
				result.add(work);
			}

			// リスト追加後、マップから削除
			filePathGroupMap.remove(n.getGroupId());
		});

		// マップの残りをリストに追加
		if (!filePathGroupMap.isEmpty()) {
			filePathGroupMap.values().stream().forEach(n -> {
				n.setOrder(result.size() + 1);
				result.add(n);
			});
		}

		return result;
	}

	private Map<String, FilePathGroupDto> createFilePathGroupMap() {
		return filePathGroupList.stream().collect(Collectors.toMap(FilePathGroupDto::getGroupId, n -> n));

	}

	private List<SPBTPJ2_TableInputData> getSortedInputOrderList(List<SPBTPJ2_TableInputData> inputGroupList) {
		return inputGroupList.stream().sorted(Comparator.comparing(SPBTPJ2_TableInputData::getOrder))
				.collect(Collectors.toList());
	}
}
