package io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.controller;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.service.EmployeeAssignmentService;
import io.github.mkhl28mi.memo_service.domain.admin.employee.service.EmployeeService;

@Controller
@RequestMapping("/admin/employees/{employeeId}/assignments")
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
		
		return "admin/employees/assignments/assignments";
	}
	
	@PostMapping
	public String addEmployeeAssignment(@PathVariable UUID employeeId, @RequestParam UUID positionId) {
		employeeAssignmentService.addEmployeeAssignment(positionId, employeeId);
		
		return String.format("redirect:/admin/employees/%s/assignments", employeeId);
	}
	
	@PutMapping("{id}")
	public String removeEmployeeAssignment(@PathVariable UUID employeeId, @PathVariable UUID id) {
		employeeAssignmentService.updateEmployeeAssignmentEndDate(id);
		
		return String.format("redirect:/admin/employees/%s/assignments", employeeId);
	}
	
}
