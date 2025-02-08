package com.manage.helper.DAO.DaoModel;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilePathDto implements Serializable {
	private String groupId;
	private List<FilePathInfoDto> pathList;
}
