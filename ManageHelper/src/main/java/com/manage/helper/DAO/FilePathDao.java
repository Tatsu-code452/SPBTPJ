package com.manage.helper.DAO;

import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import com.manage.helper.COMMON.BaseJsonDao;
import com.manage.helper.COMMON.CommonConst;
import com.manage.helper.DAO.DaoModel.FilePathDto;

@Component
public class FilePathDao extends BaseJsonDao<FilePathDto> {
	public FilePathDao() {
		super(StringUtils.concat(CommonConst.FILE_PATH, CommonConst.FILE_NAME_FILE_PATH), FilePathDto.class);
	}

}