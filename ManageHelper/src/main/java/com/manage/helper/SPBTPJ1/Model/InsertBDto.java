package com.manage.helper.SPBTPJ1.Model;

import java.util.List;
import java.util.Map;

import com.manage.helper.Dao.DaoModel.FilePathDto;

import lombok.Data;

@Data
public class InsertBDto {
	private String group;
	private String path;
	private Map<String, List<FilePathDto>> filePathMap;
}
