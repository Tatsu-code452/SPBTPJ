package com.manage.helper.SPBTPJ1.ViewModel;

import java.util.List;

import org.springframework.stereotype.Component;

import com.manage.helper.SPBTPJ1.Model.FilePathDto;

import lombok.Data;

@Data
@Component
public class Spbtpj1ViewModel {
	private List<FilePathDto> filePathList;

	private String group;
	private String path;

}
