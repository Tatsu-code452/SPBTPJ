package com.manage.helper.SPBTPJ2.Model;

import java.util.List;

import com.manage.helper.SPBTPJ2.ViewModel.SPBTPJ2_TableInputData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SPBTPJ2_UpdateBDto {
	private List<SPBTPJ2_TableInputData> inputGroupList;
}
