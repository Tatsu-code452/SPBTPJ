package com.manage.helper.DAO.DaoModel;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto implements Serializable {
	@Id
	private String taskId;
	private String taskName;
	private String expectedStartDate;
	private String expectedEndDate;
	private String actualStartDate;
	private String actualEndDate;
	private BigDecimal progressRate;
}
