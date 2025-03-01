package com.manage.helper.DAO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import com.manage.helper.COMMON.BaseJsonDao;
import com.manage.helper.COMMON.CommonConst;
import com.manage.helper.DAO.DaoModel.FilePathGroupDto;

@Component
public class FilePathGroupDao extends BaseJsonDao<FilePathGroupDto> {
	public FilePathGroupDao() {
		super(StringUtils.concat(CommonConst.FILE_PATH, CommonConst.FILE_NAME_FILE_PATH_GROUP), FilePathGroupDto.class);

	}

	public Map<String, FilePathGroupDto> createFilePathGroupMap(List<FilePathGroupDto> filePathGroupList) {
		return filePathGroupList.stream().collect(Collectors.toMap(FilePathGroupDto::getGroupId, n -> n));
	}

}