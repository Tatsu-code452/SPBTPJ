package com.manage.helper.SPBTPJ3.BusinessLogic.Service;

import org.springframework.stereotype.Service;

import com.manage.helper.SPBTPJ3.BusinessLogic.Logic.SPBTPJ3_InitLogic;
import com.manage.helper.SPBTPJ3.Model.SPBTPJ3_InitBDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SPBTPJ3_InitService {
	private final SPBTPJ3_InitLogic logic;

	public boolean execute(SPBTPJ3_InitBDto dto) {
		return logic.execute(dto);
	}
}
