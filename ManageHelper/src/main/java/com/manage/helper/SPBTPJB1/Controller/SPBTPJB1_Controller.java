package com.manage.helper.SPBTPJB1.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.manage.helper.COMMON.Pages;
import com.manage.helper.SPBTPJB1.BusinessLogic.Service.SPBTPJB1_InitService;
import com.manage.helper.SPBTPJB1.BusinessLogic.Service.SPBTPJB1_UpdateService;
import com.manage.helper.SPBTPJB1.Model.SPBTPJB1_InitBDto;
import com.manage.helper.SPBTPJB1.Model.SPBTPJB1_UpdateBDto;
import com.manage.helper.SPBTPJB1.ViewModel.SPBTPJB1_ViewModel;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/spbtpjb1")
@RequiredArgsConstructor
public class SPBTPJB1_Controller {
	private final SPBTPJB1_InitService initService;
	private final SPBTPJB1_UpdateService updateService;

	@GetMapping("/init")
	public String init(Model model) {
		SPBTPJB1_InitBDto dto = new SPBTPJB1_InitBDto();
		dto.setViewModel(new SPBTPJB1_ViewModel());
		if (initService.execute(dto)) {
			model.addAttribute("viewModel", dto.getViewModel());
		}
		return Pages.PAGE_SPBTPJ_B1;
	}

	@PostMapping("/update")
	public String update(Model model, @ModelAttribute SPBTPJB1_ViewModel viewModel) {
		SPBTPJB1_UpdateBDto dto = new SPBTPJB1_UpdateBDto(viewModel);
		updateService.execute(dto);
		return Pages.PAGE_SPBTPJ_B1_REDIRECT;
	}

}
