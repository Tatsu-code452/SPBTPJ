package com.manage.helper.SPBTPJ1.BusinessLogic.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manage.helper.SPBTPJ1.BusinessLogic.Logic.InsertLogic;
import com.manage.helper.SPBTPJ1.Model.InsertBDto;

@Service
public class InsertService {
	@Autowired
	InsertLogic logic;

	public boolean execute(InsertBDto dto) {
		return logic.execute(dto);
	}
}
