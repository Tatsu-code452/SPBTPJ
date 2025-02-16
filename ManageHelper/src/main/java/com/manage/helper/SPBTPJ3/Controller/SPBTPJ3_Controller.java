package com.manage.helper.SPBTPJ3.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.manage.helper.COMMON.Pages;
import com.manage.helper.SPBTPJ3.BusinessLogic.Service.SPBTPJ3_InitService;
import com.manage.helper.SPBTPJ3.BusinessLogic.Service.SPBTPJ3_UpdateService;
import com.manage.helper.SPBTPJ3.Model.SPBTPJ3_InitBDto;
import com.manage.helper.SPBTPJ3.Model.SPBTPJ3_UpdateBDto;
import com.manage.helper.SPBTPJ3.ViewModel.SPBTPJ3_ViewModel;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/spbtpj3")
@RequiredArgsConstructor
public class SPBTPJ3_Controller {
	private final SPBTPJ3_InitService initService;
	private final SPBTPJ3_UpdateService updateService;

	@GetMapping("/init")
	public String init(Model model) {
		SPBTPJ3_InitBDto dto = new SPBTPJ3_InitBDto();
		dto.setViewModel(new SPBTPJ3_ViewModel());
		if (initService.execute(dto)) {
			model.addAttribute("viewModel", dto.getViewModel());
		}
		return Pages.PAGE_SPBTPJ3;
	}

	@PostMapping("/update")
	public String insert(Model model, @ModelAttribute SPBTPJ3_ViewModel viewModel) {
		SPBTPJ3_UpdateBDto dto = new SPBTPJ3_UpdateBDto(viewModel);
		updateService.execute(dto);
		return Pages.PAGE_SPBTPJ1_REDIRECT;
	}

}
