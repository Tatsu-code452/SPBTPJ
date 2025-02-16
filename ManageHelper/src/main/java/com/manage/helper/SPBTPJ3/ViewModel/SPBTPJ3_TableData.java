package com.manage.helper.SPBTPJ3.ViewModel;

import java.util.List;
import java.util.Objects;

import com.manage.helper.DAO.DaoModel.FilePathGroupDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SPBTPJ3_TableData {
	private boolean checked;
	private FilePathGroupDto filePathGroup;
	private List<SPBTPJ3_InnerTableData> filePathInfoList;

	public Integer getRowSpan() {
		return Objects.nonNull(filePathInfoList) ? filePathInfoList.size() : 0;
	};

}
