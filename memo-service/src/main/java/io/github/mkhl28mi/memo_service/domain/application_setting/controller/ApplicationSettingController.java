package io.github.mkhl28mi.memo_service.domain.application_setting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.mkhl28mi.memo_service.domain.application_setting.dto.request.PageSetupRequest;
import io.github.mkhl28mi.memo_service.domain.application_setting.service.ApplicationSettingService;

@Controller
@RequestMapping("/admin/application-settings")
public class ApplicationSettingController {
	
	@Autowired
	private ApplicationSettingService applicationSettingService;
		
	@GetMapping
	public String getApllicationSettings(Model model) {
		model.addAttribute("activePage", "admin/application-settings");
		return "admin/application-settings/application-settings";
	}
	
	@GetMapping("/page-setup")
	public String getPageSetup(Model model) {
		model.addAttribute("pageSetupRequest", applicationSettingService.getPageSetup());
		model.addAttribute("activePage", "admin/application-settings");
		return "admin/application-settings/page-setup";
	}
	
	@PutMapping("/page-setup")
	public String updatePageSetup(@ModelAttribute PageSetupRequest pageSetupRequest) {
		applicationSettingService.updatePageSetup(pageSetupRequest);
		return "redirect:/admin/application-settings/page-setup";
	}
	
}
