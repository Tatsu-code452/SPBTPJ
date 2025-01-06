package com.manage.helper.Dao;

import java.util.List;

import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.manage.helper.COMMON.BaseDao;
import com.manage.helper.COMMON.CommonConst;
import com.manage.helper.Dao.DaoModel.FilePathGroupDto;

import jakarta.annotation.PostConstruct;

@Component
public class FilePathGroupDao extends BaseDao<FilePathGroupDto> {
	@PostConstruct
	public void setUp() {
		super.setFileName(StringUtils.concat(CommonConst.FILE_PATH, CommonConst.FILE_NAME_FILE_PATH_GROUP));
		super.setTypeRef(new TypeReference<FilePathGroupDto>() {
		});
		super.setTypeRefList(new TypeReference<List<FilePathGroupDto>>() {
		});
	}

}