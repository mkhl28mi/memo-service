package io.github.mkhl28mi.memo_service.domain.admin.department.unit.controller;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.mkhl28mi.memo_service.domain.admin.department.unit.dto.request.DepartmentUnitRequest;
import io.github.mkhl28mi.memo_service.domain.admin.department.unit.dto.response.DepartmentUnitResponse;
import io.github.mkhl28mi.memo_service.domain.admin.department.unit.service.DepartmentUnitService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/departments/{departmentId}/units")
@PreAuthorize("hasAnyRole('ADMIN')")
public class DepartmentUnitController {
	
	private static final String REDIRECT_UNITS = "redirect:/admin/departments/%s/units";
	
	private final DepartmentUnitService departmentUnitService;
	
	public DepartmentUnitController(DepartmentUnitService departmentUnitService) {
		this.departmentUnitService = departmentUnitService;
	}
	
	@GetMapping
	public String getDepartmentUnitsByDepartmentId(@PathVariable UUID departmentId, Model model) {
		model.addAttribute("activePage", "admin/departments");
		model.addAttribute("departmentId", departmentId);
		model.addAttribute("departmentUnitRequest", new DepartmentUnitRequest());
		model.addAttribute("units", departmentUnitService.getDepartmentUnitsByDepartmentId(departmentId));
		
		return "admin/departments/units/units";
	}
	
	@PostMapping
	public String addDepartmentUnit(@PathVariable UUID departmentId, @Valid @ModelAttribute DepartmentUnitRequest departmentUnitRequest, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("activePage", "admin/departments");
			model.addAttribute("departmentId", departmentId);
			model.addAttribute("departmentUnitRequest", departmentUnitRequest);
			model.addAttribute("units", departmentUnitService.getDepartmentUnitsByDepartmentId(departmentId));
			
            return "admin/departments/units/units"; 
        }
		
		departmentUnitService.addDepartmentUnit(departmentId, departmentUnitRequest);
		
		return String.format(REDIRECT_UNITS, departmentId);
	}
	
	@GetMapping("/{id}")
	public String getDepartmentById(@PathVariable UUID departmentId, @PathVariable UUID id, Model model) {
		DepartmentUnitResponse departmentUnitResponse = departmentUnitService.getDepartmentUnitResponseById(id);
		
		model.addAttribute("activePage", "admin/departments");
		model.addAttribute("departmentId", departmentId);
		model.addAttribute("unitId", id);
		model.addAttribute("departmentUnitRequest", new DepartmentUnitRequest(departmentUnitResponse));
		
		return "admin/departments/units/unit";
	}
	
	@PutMapping("/{id}")
    public String updateDepartmentUnit(@PathVariable UUID departmentId, @PathVariable UUID id, @Valid @ModelAttribute DepartmentUnitRequest departmentUnitRequest, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("activePage", "admin/departments");
			model.addAttribute("departmentId", departmentId);
			model.addAttribute("unitId", id);
			model.addAttribute("departmentUnitRequest", departmentUnitRequest);
			
            return "admin/departments/units/unit"; 
        }
		
		departmentUnitService.updateDepartmentUnit(id, departmentUnitRequest);
        
        return String.format(REDIRECT_UNITS, departmentId);
    }
	
	@DeleteMapping("/{id}")
	public String deleteDepartmentUnit(@PathVariable UUID departmentId, @PathVariable UUID id) {
		departmentUnitService.deleteDepartmentUnit(id);
		
		return String.format(REDIRECT_UNITS, departmentId);
	}

}
