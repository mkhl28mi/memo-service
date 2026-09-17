package io.github.mkhl28mi.memo_service.domain.admin.employee.controller;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
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

import io.github.mkhl28mi.memo_service.domain.admin.employee.dto.request.EmployeeRequest;
import io.github.mkhl28mi.memo_service.domain.admin.employee.service.EmployeeService;

@Controller
@RequestMapping("/admin/employees")
@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
public class EmployeeController {
	
	private static final String REDIRECT_EMPLOYEES = "redirect:/admin/employees";
	
	private final EmployeeService employeeService;
	
	public EmployeeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}
	
	@GetMapping
	public String getEmployees(@RequestParam(required = false) String search, Model model) {
		model.addAttribute("activePage", "admin/employees");
		model.addAttribute("employeeRequest", new EmployeeRequest());
		model.addAttribute("employees", employeeService.getEmployees(search));
		
		return "admin/employees/employees";
	}
	
	@PostMapping
	public String addEmployee(@ModelAttribute EmployeeRequest employeeRequest) {
		employeeService.addEmployee(employeeRequest);
		
		return REDIRECT_EMPLOYEES;
	}
	
	@GetMapping("/{id}")
	public String getEmployeeById(@PathVariable UUID id, Model model) {
		var employeeResponse = employeeService.getEmployeeResponseById(id); 
		
		model.addAttribute("activePage", "admin/employees");
		model.addAttribute("employeeId", id);
		model.addAttribute("employeeRequest", new EmployeeRequest(employeeResponse));
		
		return "admin/employees/employee";
	}
	
	@PutMapping("/{id}")
	public String updateEmplyee(@PathVariable UUID id, @ModelAttribute EmployeeRequest employeeRequest) {
		employeeService.updateEmployee(id, employeeRequest);
		
		return REDIRECT_EMPLOYEES;
	}
	
	@DeleteMapping("/{id}")
	public String deleteEmployee(@PathVariable UUID id) {
		employeeService.deleteEmployee(id);
		
		return REDIRECT_EMPLOYEES;
	}
	
}
