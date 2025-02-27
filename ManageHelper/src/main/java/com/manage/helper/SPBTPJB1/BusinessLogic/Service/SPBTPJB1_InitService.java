package com.manage.helper.SPBTPJB1.BusinessLogic.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manage.helper.SPBTPJB1.BusinessLogic.Logic.SPBTPJB1_InitLogic;
import com.manage.helper.SPBTPJB1.Model.SPBTPJB1_InitBDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SPBTPJB1_InitService {
	private final SPBTPJB1_InitLogic logic;

	public boolean execute(SPBTPJB1_InitBDto dto) {
		return logic.execute(dto);
	}
}
