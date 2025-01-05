package com.manage.helper.SPBTPJ1.Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.manage.helper.COMMON.Pages;
import com.manage.helper.SPBTPJ1.BusinessLogic.Service.InitService;
import com.manage.helper.SPBTPJ1.BusinessLogic.Service.InsertService;
import com.manage.helper.SPBTPJ1.Model.InitBDto;
import com.manage.helper.SPBTPJ1.Model.InsertBDto;
import com.manage.helper.SPBTPJ1.ViewModel.Spbtpj1ViewModel;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/spbtpj1")
@RequiredArgsConstructor
public class Spbtpj1Controller {
	private final InitService initService;
	private final InsertService insertService;

	@GetMapping("/init")
	public String init(Model model) {
		InitBDto dto = new InitBDto();
		dto.setViewModel(new Spbtpj1ViewModel());
		if (initService.execute(dto)) {
			model.addAttribute("viewModel", dto.getViewModel());
		}
		return Pages.PAGE_SPBTPJ1;
	}

	@PostMapping("/insert")
	public String insert(Model model, @ModelAttribute Spbtpj1ViewModel viewModel) {
		InsertBDto dto = new InsertBDto();
		dto.setGroup(viewModel.getGroup());
		dto.setPath(viewModel.getPath());
		insertService.execute(dto);
		return Pages.PAGE_SPBTPJ1_REDIRECT;
	}

	@GetMapping("/openFile")
	public String openFile(@RequestParam("filename") String filename) {
		try {
			File file = new File(filename);
			if (file.exists()) {
				List<String> cmd = new ArrayList<String>();
				cmd.add("cmd.exe");
				cmd.add("/c");
				cmd.add("start");
				cmd.add("explorer");
				cmd.add(file.getPath());
				Runtime.getRuntime().exec(cmd.stream().toArray(String[]::new));
			}
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return Pages.PAGE_SPBTPJ1_REDIRECT;
	}
}
