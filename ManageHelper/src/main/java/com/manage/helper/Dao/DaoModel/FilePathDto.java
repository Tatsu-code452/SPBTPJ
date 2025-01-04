package com.manage.helper.Dao.DaoModel;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class FilePathDto implements Serializable {
	private String name;
	private String path;
	private String encodePath;
}
