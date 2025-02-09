package com.manage.helper.SPBTPJ2.BusinessLogic.Service;

import org.springframework.stereotype.Service;

import com.manage.helper.SPBTPJ2.BusinessLogic.Logic.SPBTPJ2_UpdateLogic;
import com.manage.helper.SPBTPJ2.Model.SPBTPJ2_UpdateBDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SPBTPJ2_UpdateService {
	private final SPBTPJ2_UpdateLogic logic;

	public boolean execute(SPBTPJ2_UpdateBDto dto) {
		return logic.execute(dto);
	}
}
