package io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.controller;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.dto.request.EmployeeAssignmentRequest;
import io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.service.EmployeeAssignmentService;
import io.github.mkhl28mi.memo_service.domain.admin.employee.service.EmployeeService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/employees/{employeeId}/assignments")
@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
public class EmployeeAssignmentController {
	
	private final EmployeeAssignmentService employeeAssignmentService;
	
	private final EmployeeService employeeService;
	
	public EmployeeAssignmentController(EmployeeAssignmentService employeeAssignmentService, EmployeeService employeeService) {
		this.employeeAssignmentService = employeeAssignmentService;
		this.employeeService = employeeService;
	}
	
	@GetMapping
	public String getEmployeeAssignments(@PathVariable UUID employeeId, Model model) {
		model.addAttribute("activePage", "admin/employees");
		model.addAttribute("employee", employeeService.getEmployeeResponseById(employeeId));
		model.addAttribute("assignments", employeeAssignmentService.getEmployeeAssignments(employeeId));
		model.addAttribute("employeeAssignmentRequest", new EmployeeAssignmentRequest());
		
		return "admin/employees/assignments/assignments";
	}
	
	@PostMapping
	public String addEmployeeAssignment(@PathVariable UUID employeeId, @Valid @ModelAttribute EmployeeAssignmentRequest employeeAssignmentRequest, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("activePage", "admin/employees");
			model.addAttribute("employee", employeeService.getEmployeeResponseById(employeeId));
			model.addAttribute("assignments", employeeAssignmentService.getEmployeeAssignments(employeeId));
			model.addAttribute("employeeAssignmentRequest", employeeAssignmentRequest);
			
            return "admin/employees/assignments/assignments"; 
        }
		
		employeeAssignmentService.addEmployeeAssignment(employeeAssignmentRequest, employeeId);
		
		return String.format("redirect:/admin/employees/%s/assignments", employeeId);
	}
	
	@PutMapping("{id}")
	public String removeEmployeeAssignment(@PathVariable UUID employeeId, @PathVariable UUID id) {
		employeeAssignmentService.updateEmployeeAssignmentEndDate(id);
		
		return String.format("redirect:/admin/employees/%s/assignments", employeeId);
	}
	
}
