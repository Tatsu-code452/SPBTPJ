package com.manage.helper.SPBTPJ1.BusinessLogic.Service;

import org.springframework.stereotype.Service;

import com.manage.helper.SPBTPJ1.BusinessLogic.Logic.InsertLogic;
import com.manage.helper.SPBTPJ1.Model.InsertBDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InsertService {
	private final InsertLogic logic;

	public boolean execute(InsertBDto dto) {
		return logic.execute(dto);
	}
}
