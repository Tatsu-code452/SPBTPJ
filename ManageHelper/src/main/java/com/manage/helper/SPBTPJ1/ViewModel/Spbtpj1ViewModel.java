package com.manage.helper.SPBTPJ1.ViewModel;

import java.util.List;

import org.springframework.stereotype.Component;

import com.manage.helper.Dao.DaoModel.FilePathGroupDto;
import lombok.Data;

@Data
@Component
public class Spbtpj1ViewModel {
	private List<FilePathGroupDto> filePathList;

	private String group;
	private String path;
}
