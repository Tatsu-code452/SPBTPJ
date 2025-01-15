package com.manage.helper.SPBTPJ1.BusinessLogic.Service;

import org.springframework.stereotype.Service;

import com.manage.helper.SPBTPJ1.BusinessLogic.Logic.SPBTPJ1_InitLogic;
import com.manage.helper.SPBTPJ1.Model.SPBTPJ1_InitBDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SPBTPJ1_InitService {
	private final SPBTPJ1_InitLogic logic;

	public boolean execute(SPBTPJ1_InitBDto dto) {
		return logic.execute(dto);
	}
}
