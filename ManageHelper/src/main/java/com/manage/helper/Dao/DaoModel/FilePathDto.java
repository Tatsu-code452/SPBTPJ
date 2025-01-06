package com.manage.helper.Dao.DaoModel;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class FilePathDto implements Serializable {
	private String groupId;
	private List<FilePathInfoDto> pathList;

	@Data
	public static class FilePathInfoDto {
		private String name;
		private String path;
		private String encodePath;
	}
}
