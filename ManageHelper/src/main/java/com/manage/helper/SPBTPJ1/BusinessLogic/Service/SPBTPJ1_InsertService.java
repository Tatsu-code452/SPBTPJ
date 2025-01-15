package com.manage.helper.SPBTPJ1.BusinessLogic.Service;

import org.springframework.stereotype.Service;

import com.manage.helper.SPBTPJ1.BusinessLogic.Logic.SPBTPJ1_InsertLogic;
import com.manage.helper.SPBTPJ1.Model.SPBTPJ1_InsertBDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SPBTPJ1_InsertService {
	private final SPBTPJ1_InsertLogic logic;

	public boolean execute(SPBTPJ1_InsertBDto dto) {
		return logic.execute(dto);
	}
}
