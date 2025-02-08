package com.manage.helper.DAO;

import java.util.List;

import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.manage.helper.COMMON.BaseDao;
import com.manage.helper.COMMON.CommonConst;
import com.manage.helper.DAO.DaoModel.FilePathGroupDto;

@Component
public class FilePathGroupDao extends BaseDao<FilePathGroupDto> {
	public FilePathGroupDao() {
		super(StringUtils.concat(CommonConst.FILE_PATH, CommonConst.FILE_NAME_FILE_PATH_GROUP),
				new TypeReference<FilePathGroupDto>() {
				}, new TypeReference<List<FilePathGroupDto>>() {
				});

	}
}