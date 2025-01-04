package com.manage.helper.SPBTPJ1.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.manage.helper.SPBTPJ1.BusinessLogic.Service.InitService;
import com.manage.helper.SPBTPJ1.BusinessLogic.Service.InsertService;
import com.manage.helper.SPBTPJ1.Model.InitBDto;
import com.manage.helper.SPBTPJ1.Model.InsertBDto;
import com.manage.helper.SPBTPJ1.ViewModel.Spbtpj1ViewModel;

@Controller
@RequestMapping("/spbtpj1")
public class Spbtpj1Controller {
	@Autowired
	private InitService initService;
	@Autowired
	private InsertService insertService;

	@GetMapping("/init")
	public String init(Model model) {
		InitBDto dto = new InitBDto();
		dto.setViewModel(new Spbtpj1ViewModel());
		if (initService.execute(dto)) {
			model.addAttribute("viewModel", dto.getViewModel());
		}
		return "views/spbtpj1";
	}

	@PostMapping("/insert")
	public String insert(Model model, @ModelAttribute Spbtpj1ViewModel viewModel) {
		InsertBDto dto = new InsertBDto();
		dto.setGroup(viewModel.getGroup());
		dto.setPath(viewModel.getPath());
		insertService.execute(dto);
		return "redirect:/spbtpj1/init";
	}
}
