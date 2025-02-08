package com.manage.helper.DAO.DaoModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilePathInfoDto {
	private String name;
	private String path;
	private String encodePath;
}
