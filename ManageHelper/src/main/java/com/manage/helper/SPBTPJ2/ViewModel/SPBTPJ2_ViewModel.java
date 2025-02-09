package com.manage.helper.SPBTPJ2.ViewModel;

import java.util.List;

import com.manage.helper.DAO.DaoModel.FilePathGroupDto;

import lombok.Data;

@Data
public class SPBTPJ2_ViewModel {
	private List<FilePathGroupDto> filePathGroupList;
	private List<SPBTPJ2_TableInputData> inputGroupList;
}
