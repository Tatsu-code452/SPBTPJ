package com.manage.helper.SPBTPJ2.BusinessLogic.Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.manage.helper.COMMON.BaseLogic;
import com.manage.helper.DAO.FilePathGroupDao;
import com.manage.helper.DAO.DaoModel.FilePathGroupDto;
import com.manage.helper.SPBTPJ2.Model.SPBTPJ2_UpdateBDto;
import com.manage.helper.SPBTPJ2.ViewModel.SPBTPJ2_TableInputData;
import com.manage.helperUtil.ComparatorUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SPBTPJ2_UpdateLogic extends BaseLogic<SPBTPJ2_UpdateBDto> {
	private final FilePathGroupDao filePathGroupDao;

	private List<FilePathGroupDto> filePathGroupList;

	@Override
	protected boolean loadData(SPBTPJ2_UpdateBDto dto) {
		filePathGroupList = filePathGroupDao.readAll();
		return true;
	}

	@Override
	protected boolean createData(SPBTPJ2_UpdateBDto dto) {
		filePathGroupList = createSaveFilePathGroupList(dto);
		return true;
	}

	@Override
	protected boolean saveData(SPBTPJ2_UpdateBDto dto) {
		return filePathGroupDao.writeAll(filePathGroupList);
	}

	private List<FilePathGroupDto> createSaveFilePathGroupList(SPBTPJ2_UpdateBDto dto) {
		List<FilePathGroupDto> result = new ArrayList<FilePathGroupDto>();

		// ファイルグループのマップを取得
		Map<String, FilePathGroupDto> filePathGroupMap = filePathGroupDao.createFilePathGroupMap(filePathGroupList);

		// 画面で指定した順にソートした入力情報を取得
		List<SPBTPJ2_TableInputData> sortedInputOrderList = getSortedInputOrderList(dto.getInputGroupList());

		// 入力後の順番で、削除対象外のデータをリストに追加
		sortedInputOrderList.stream().forEach(n -> {
			if (!n.isChecked()) {
				result.add(createFilePathGroup(filePathGroupMap.get(n.getGroupId()), result.size() + 1, n.getGroup()));
			}

			// リスト追加後、マップから削除
			filePathGroupMap.remove(n.getGroupId());
		});

		// マップの残りをリストに追加
		if (!filePathGroupMap.isEmpty()) {
			filePathGroupMap.values().stream()
					.forEach(n -> result.add(createFilePathGroup(n, result.size() + 1, n.getGroup())));
		}

		return result;
	}

	private List<SPBTPJ2_TableInputData> getSortedInputOrderList(List<SPBTPJ2_TableInputData> inputGroupList) {
		return inputGroupList.stream()
				.sorted(ComparatorUtils.comparingNaturalOrderNullsLast(SPBTPJ2_TableInputData::getOrder))
				.collect(Collectors.toList());
	}

	private FilePathGroupDto createFilePathGroup(FilePathGroupDto base, Integer order, String group) {
		base.setOrder(order);
		base.setGroup(group);
		return base;
	}
}
