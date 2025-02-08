package com.manage.helper.DAO.DaoModel;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilePathGroupDto implements Serializable {
	private String groupId;
	private String group;
	private Integer order;
}
