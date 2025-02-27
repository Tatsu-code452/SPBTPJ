package com.manage.helper.SPBTPJB1.BusinessLogic.Logic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import com.manage.helper.COMMON.BaseLogic;
import com.manage.helper.DAO.TaskDao;
import com.manage.helper.DAO.DaoModel.TaskDto;
import com.manage.helper.SPBTPJB1.Model.SPBTPJB1_UpdateBDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SPBTPJB1_UpdateLogic extends BaseLogic<SPBTPJB1_UpdateBDto> {
	private final TaskDao taskDao;

	private List<TaskDto> taskList;

	@Override
	protected boolean createData(SPBTPJB1_UpdateBDto dto) {
		taskList = dto.getViewModel().getTaskList().stream()
				.filter(n -> !StringUtils.isEmptyOrWhitespace(n.getTaskName())).collect(Collectors.toList());
		taskList.stream().forEach(n -> {
			if (StringUtils.isEmptyOrWhitespace(n.getTaskId())) {
				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
				n.setTaskId(now.format(formatter));
			}
		});
		return true;
	}

	@Override
	protected boolean saveData(SPBTPJB1_UpdateBDto dto) {
		taskDao.getRepository().saveAll(taskList);
		taskDao.export();
		return true;
	}

}
