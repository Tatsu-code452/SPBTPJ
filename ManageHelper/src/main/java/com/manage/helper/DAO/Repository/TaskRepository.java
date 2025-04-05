package com.manage.helper.DAO.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manage.helper.DAO.DaoModel.TaskDto;

public interface TaskRepository extends JpaRepository<TaskDto, String> {

}
