package io.github.mkhl28mi.memo_service.domain.admin.application.setting.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.mkhl28mi.memo_service.domain.admin.application.setting.dto.request.AboutCompanyRequest;
import io.github.mkhl28mi.memo_service.domain.admin.application.setting.dto.request.PageSetupRequest;
import io.github.mkhl28mi.memo_service.domain.admin.application.setting.service.ApplicationSettingService;

@Controller
@RequestMapping("/admin/application-settings")
@PreAuthorize("hasAnyRole('ADMIN')")
public class ApplicationSettingController {
	
	private final ApplicationSettingService applicationSettingService;
	
	public ApplicationSettingController(ApplicationSettingService applicationSettingService) {
		this.applicationSettingService = applicationSettingService;
	}

	@GetMapping
	public String getApllicationSettings(Model model) {
		model.addAttribute("activePage", "admin/application-settings");
		
		return "admin/application-settings/application-settings";
	}
	
	@GetMapping("/page-setup")
	public String getPageSetup(Model model) {
		model.addAttribute("activePage", "admin/application-settings");
		model.addAttribute("pageSetupRequest", applicationSettingService.getPageSetup());
		
		return "admin/application-settings/page-setup";
	}
	
	@PutMapping("/page-setup")
	public String updatePageSetup(@ModelAttribute PageSetupRequest pageSetupRequest) {
		applicationSettingService.updatePageSetup(pageSetupRequest);
		
		return "redirect:/admin/application-settings/page-setup";
	}
	
	@GetMapping("/about-company")
	public String getAboutCompany(Model model) {
		model.addAttribute("activePage", "admin/application-settings");
		model.addAttribute("aboutCompanyRequest", applicationSettingService.getAboutCompany());
		
		return "admin/application-settings/about-company";
	}
	
	@PutMapping("/about-company")
	public String updatePageSetup(@ModelAttribute AboutCompanyRequest aboutCompanyRequest) {
		applicationSettingService.updateAboutCompany(aboutCompanyRequest);
		
		return "redirect:/admin/application-settings/about-company";
	}
}
