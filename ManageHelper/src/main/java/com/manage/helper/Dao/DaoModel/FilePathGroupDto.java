package com.manage.helper.Dao.DaoModel;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class FilePathGroupDto implements Serializable {
	private String group;
	private List<FilePathDto> pathList;
}
