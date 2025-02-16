package com.manage.helper.SPBTPJ3.ViewModel;

import com.manage.helper.DAO.DaoModel.FilePathInfoDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SPBTPJ3_InnerTableData {
	private boolean checked;
	private Integer order;
	private FilePathInfoDto filePathInfo;

}
