package com.manage.helper.SPBTPJ2.BusinessLogic.Service;

import org.springframework.stereotype.Service;

import com.manage.helper.SPBTPJ2.BusinessLogic.Logic.SPBTPJ2_InitLogic;
import com.manage.helper.SPBTPJ2.Model.SPBTPJ2_InitBDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SPBTPJ2_InitService {
	private final SPBTPJ2_InitLogic logic;

	public boolean execute(SPBTPJ2_InitBDto dto) {
		return logic.execute(dto);
	}
}
