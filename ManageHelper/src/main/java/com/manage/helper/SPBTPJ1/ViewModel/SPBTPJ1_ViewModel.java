package com.manage.helper.SPBTPJ1.ViewModel;

import java.util.List;
import java.util.Map;

import com.manage.helper.Dao.DaoModel.FilePathDto;

import lombok.Data;

@Data
public class SPBTPJ1_ViewModel {
	private Map<String, String> filePathGroupMap;
	private List<FilePathDto> filePathList;

	private String groupId;
	private String name;
	private String path;
}
