package com.manage.helper.SPBTPJ1.BusinessLogic.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manage.helper.SPBTPJ1.BusinessLogic.Logic.InitLogic;
import com.manage.helper.SPBTPJ1.Model.InitBDto;

@Service
public class InitService {
	@Autowired
	InitLogic logic;

	public boolean execute(InitBDto dto) {
		return logic.execute(dto);
	}
}
