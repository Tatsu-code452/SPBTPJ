package com.manage.helper.SPBTPJB1.BusinessLogic.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manage.helper.SPBTPJB1.BusinessLogic.Logic.SPBTPJB1_UpdateLogic;
import com.manage.helper.SPBTPJB1.Model.SPBTPJB1_UpdateBDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SPBTPJB1_UpdateService {
	private final SPBTPJB1_UpdateLogic logic;

	public boolean execute(SPBTPJB1_UpdateBDto dto) {
		return logic.execute(dto);
	}
}
