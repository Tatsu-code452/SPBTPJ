package com.manage.helper.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.manage.helper.COMMON.BaseDao;
import com.manage.helper.COMMON.CommonConst;
import com.manage.helper.DAO.DaoModel.TaskDto;
import com.manage.helper.DAO.Repository.TaskRepository;

import jakarta.annotation.PostConstruct;

@Repository
public class TaskDao extends BaseDao<TaskDto> {
	private final TaskRepository taskRepository;

	@Autowired
	public TaskDao(TaskRepository taskRepository) {
		super(StringUtils.concat(CommonConst.FILE_PATH, CommonConst.FILE_NAME_TASK), new TypeReference<TaskDto>() {
		}, new TypeReference<List<TaskDto>>() {
		}, TaskDto.class);
		this.taskRepository = taskRepository;
	}

	@PostConstruct
	public void init() {
		super.load(taskRepository);
	}

	public TaskRepository getRepository() {
		return taskRepository;
	}

	public void export() {
		super.export(taskRepository);
	}
}
