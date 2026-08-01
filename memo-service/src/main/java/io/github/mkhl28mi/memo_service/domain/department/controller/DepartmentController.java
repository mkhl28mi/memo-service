package io.github.mkhl28mi.memo_service.domain.department.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.mkhl28mi.memo_service.domain.department.dto.request.DepartmentRequest;
import io.github.mkhl28mi.memo_service.domain.department.dto.response.DepartmentResponse;
import io.github.mkhl28mi.memo_service.domain.department.service.DepartmentService;

@Controller
@RequestMapping("/admin/departments")
public class DepartmentController {
	
	@Autowired
	private DepartmentService departmentService;
	
	@GetMapping
    public String getDepartments(@RequestParam(required = false) String search, Model model) {
		model.addAttribute("activePage", "admin/departments");
		model.addAttribute("departmentRequest", new DepartmentRequest());
		model.addAttribute("departments", departmentService.getDepartments(search));
        return "admin/departments/departments";
    }
	
	@PostMapping
    public String addDepartment(@ModelAttribute("departmentRequest") DepartmentRequest departmentRequest) {
		departmentService.addDepartment(departmentRequest);
        return "redirect:/admin/departments";
    }
	
	@GetMapping("/{id}")
    public String getDepartmentById(@PathVariable UUID id, Model model) {
		DepartmentResponse departmentResponse = departmentService.getDepartmentResponseById(id);
		model.addAttribute("activePage", "admin/departments");
		model.addAttribute("departmentId", id);
		model.addAttribute("departmentRequest", new DepartmentRequest(departmentResponse));
		model.addAttribute("positions", List.of(departmentResponse.positionResponse()));
		return "admin/departments/department";
    }
	
	@PutMapping("/{id}")
    public String updateDepartment(@PathVariable UUID id, @ModelAttribute("departmentRequest") DepartmentRequest departmentRequest) {
        departmentService.updateDepartment(id, departmentRequest);
        return "redirect:/admin/departments";
    }
	
	@DeleteMapping("/{id}")
    public String deleteDepartment(@PathVariable UUID id) {
		departmentService.deleteDepartment(id);
		return "redirect:/admin/departments";
    }
	
}
