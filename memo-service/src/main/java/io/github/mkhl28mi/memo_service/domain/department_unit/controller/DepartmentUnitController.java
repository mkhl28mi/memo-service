package io.github.mkhl28mi.memo_service.domain.department_unit.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.mkhl28mi.memo_service.domain.department_unit.dto.response.DepartmentUnitResponse;
import io.github.mkhl28mi.memo_service.domain.department_unit.service.DepartmentUnitService;
import io.github.mkhl28mi.memo_service.exception.ResourceNotFoundException;

@Controller
@RequestMapping("/admin/departments/{departmentId}/units")
public class DepartmentUnitController {
	
	@Autowired
	private DepartmentUnitService departmentUnitService;
	
	@GetMapping
	public String getDepartmentUnitsByDepartmentId(@PathVariable UUID departmentId, Model model) {
		model.addAttribute("units", departmentUnitService.getDepartmentUnitsByDepartmentId(departmentId));
		model.addAttribute("departmentId", departmentId);
		model.addAttribute("activePage", "admin/departments");
		return "departments/units/units";
	}
	
	@PostMapping
	public String createDepartmentUnit(@PathVariable UUID departmentId, @RequestParam("code") String code) {
		departmentUnitService.saveDepartmentUnit(departmentId, code);
		return String.format("redirect:/admin/departments/%s/units", departmentId);
	}
	
	@GetMapping("/{id}")
	public String getDepartmentById(@PathVariable UUID departmentId, @PathVariable UUID id, Model model) {
		DepartmentUnitResponse departmentUnitResponse = departmentUnitService.getDepartmentUnitResponseById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Department unit not found with id: " + id));
		model.addAttribute("departmentId", departmentId);
		model.addAttribute("unitId", id);
		model.addAttribute("code", departmentUnitResponse.code());
		model.addAttribute("activePage", "admin/departments");
		return "departments/units/unit";
	}
	
	@PutMapping("/{id}")
    public String updateDepartmentUnit(@PathVariable UUID departmentId, @PathVariable UUID id, @RequestParam("code") String code) {
        departmentUnitService.updateDepartmentUnit(id, code);
        return String.format("redirect:/admin/departments/%s/units", departmentId);
    }
	
	@DeleteMapping("/{id}")
	public String deleteDepartmentUnit(@PathVariable UUID departmentId, @PathVariable UUID id) {
		departmentUnitService.deleteDepartmentUnit(id);
		return String.format("redirect:/admin/departments/%s/units", departmentId);
	}

}
