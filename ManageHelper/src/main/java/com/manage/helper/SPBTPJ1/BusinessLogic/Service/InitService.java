package com.manage.helper.SPBTPJ1.BusinessLogic.Service;

import org.springframework.stereotype.Service;

import com.manage.helper.SPBTPJ1.BusinessLogic.Logic.InitLogic;
import com.manage.helper.SPBTPJ1.Model.InitBDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InitService {
	private final InitLogic logic;

	public boolean execute(InitBDto dto) {
		return logic.execute(dto);
	}
}
