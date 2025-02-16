package com.manage.helper.SPBTPJ3.BusinessLogic.Service;

import org.springframework.stereotype.Service;

import com.manage.helper.SPBTPJ3.BusinessLogic.Logic.SPBTPJ3_UpdateLogic;
import com.manage.helper.SPBTPJ3.Model.SPBTPJ3_UpdateBDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SPBTPJ3_UpdateService {
	private final SPBTPJ3_UpdateLogic logic;

	public boolean execute(SPBTPJ3_UpdateBDto dto) {
		return logic.execute(dto);
	}
}
