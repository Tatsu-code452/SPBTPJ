package com.manage.helper.SPBTPJ2.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.manage.helper.COMMON.Pages;
import com.manage.helper.SPBTPJ2.BusinessLogic.Service.SPBTPJ2_InitService;
import com.manage.helper.SPBTPJ2.BusinessLogic.Service.SPBTPJ2_UpdateService;
import com.manage.helper.SPBTPJ2.Model.SPBTPJ2_InitBDto;
import com.manage.helper.SPBTPJ2.Model.SPBTPJ2_UpdateBDto;
import com.manage.helper.SPBTPJ2.ViewModel.SPBTPJ2_ViewModel;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/spbtpj2")
@RequiredArgsConstructor
public class SPBTPJ2_Controller {
	private final SPBTPJ2_InitService initService;
	private final SPBTPJ2_UpdateService updateService;

	@GetMapping("/init")
	public String init(Model model) {
		SPBTPJ2_InitBDto dto = new SPBTPJ2_InitBDto();
		dto.setViewModel(new SPBTPJ2_ViewModel());
		if (initService.execute(dto)) {
			model.addAttribute("viewModel", dto.getViewModel());
		}
		return Pages.PAGE_SPBTPJ2;
	}

	@PostMapping("/update")
	public String insert(Model model, @ModelAttribute SPBTPJ2_ViewModel viewModel) {
		SPBTPJ2_UpdateBDto dto = new SPBTPJ2_UpdateBDto(viewModel.getInputGroupList());
		updateService.execute(dto);
		return Pages.PAGE_SPBTPJ1_REDIRECT;
	}

}
