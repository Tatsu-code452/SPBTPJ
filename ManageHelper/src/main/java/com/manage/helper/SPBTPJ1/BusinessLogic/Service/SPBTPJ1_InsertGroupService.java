package com.manage.helper.SPBTPJ1.BusinessLogic.Service;

import org.springframework.stereotype.Service;

import com.manage.helper.SPBTPJ1.BusinessLogic.Logic.SPBTPJ1_InsertGroupLogic;
import com.manage.helper.SPBTPJ1.Model.SPBTPJ1_InsertGroupBDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SPBTPJ1_InsertGroupService {
    private final SPBTPJ1_InsertGroupLogic logic;

    public boolean execute(SPBTPJ1_InsertGroupBDto dto) {
        return logic.execute(dto);
    }
}
