package com.manage.helper.SPBTPJB1.BusinessLogic.Logic;

import java.util.List;

import org.springframework.stereotype.Component;

import com.manage.helper.COMMON.BaseLogic;
import com.manage.helper.DAO.DaoModel.TaskDto;
import com.manage.helper.DAO.Repository.TaskRepository;
import com.manage.helper.SPBTPJB1.Model.SPBTPJB1_InitBDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SPBTPJB1_InitLogic extends BaseLogic<SPBTPJB1_InitBDto> {
	private final TaskRepository taskRepository;

	private List<TaskDto> taskList;

	@Override
	protected boolean loadData(SPBTPJB1_InitBDto dto) {
		taskList = taskRepository.findAll();
		return true;
	}

	@Override
	protected boolean createResponse(SPBTPJB1_InitBDto dto) {
		dto.getViewModel().setTaskList(taskList);
		dto.getViewModel().getTaskList().add(new TaskDto());
		return true;
	}
}
