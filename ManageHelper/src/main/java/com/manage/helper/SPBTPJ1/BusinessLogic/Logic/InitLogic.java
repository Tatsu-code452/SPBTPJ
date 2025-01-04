package com.manage.helper.SPBTPJ1.BusinessLogic.Logic;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manage.helper.COMMON.BaseLogic;
import com.manage.helper.SPBTPJ1.Model.FilePathDto;
import com.manage.helper.SPBTPJ1.Model.InitBDto;

@Component
public class InitLogic extends BaseLogic<InitBDto> {

	@Override
	protected boolean createResponse(InitBDto dto) {
		boolean result = true;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			dto.getViewModel()
					.setFilePathList(objectMapper.readValue(new File("C:\\workspace\\web\\SPBTPJ\\data\\filePath.json"),
							new TypeReference<List<FilePathDto>>() {
							}));
		} catch (IOException e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

}
