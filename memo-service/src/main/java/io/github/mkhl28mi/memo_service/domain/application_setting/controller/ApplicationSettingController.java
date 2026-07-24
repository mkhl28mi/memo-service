package io.github.mkhl28mi.memo_service.domain.application_setting.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.mkhl28mi.memo_service.domain.application_setting.dto.request.PageSetupRequest;
import io.github.mkhl28mi.memo_service.domain.application_setting.service.ApplicationSettingService;
import io.github.mkhl28mi.memo_service.domain.department.dto.request.DepartmentManagerRequest;
import io.github.mkhl28mi.memo_service.domain.department.service.DepartmentService;

@Controller
@RequestMapping("/admin/application-settings")
public class ApplicationSettingController {
	
	@Autowired
	private ApplicationSettingService applicationSettingService;
	
	@Autowired
	private DepartmentService departmentService;
	
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
	
	@GetMapping("/department-managers")
	public String getDepartmentManagers(Model model) {
		model.addAttribute("departmentManagerRequest", new DepartmentManagerRequest());
		model.addAttribute("departmentManagers", departmentService.getDepartmentManagers());
		model.addAttribute("activePage", "admin/application-settings");
		return "admin/application-settings/department-managers";
	}
	
	@PostMapping("/department-managers")
	public String addDepartmentManager(@ModelAttribute DepartmentManagerRequest departmentManagerRequest) {
		departmentService.addDepartmentManager(departmentManagerRequest);
		return "redirect:/admin/application-settings/department-managers";
	}
	
	@DeleteMapping("/department-managers")
	public String deleteDepartmentManager(@RequestParam UUID departmentId, @RequestParam UUID employeeId) {
		departmentService.deleteDepartmentManager(departmentId, employeeId);
		return "redirect:/admin/application-settings/department-managers";
	}
	
}
