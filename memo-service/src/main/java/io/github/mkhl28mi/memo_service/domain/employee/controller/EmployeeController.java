package io.github.mkhl28mi.memo_service.domain.employee.controller;

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

import io.github.mkhl28mi.memo_service.domain.employee.dto.request.EmployeeRequest;
import io.github.mkhl28mi.memo_service.domain.employee.service.EmployeeService;

@Controller
@RequestMapping("/admin/employees")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping
	public String getEmployees(@RequestParam(required = false) String search, Model model) {
		model.addAttribute("activePage", "admin/employees");
		model.addAttribute("employeeRequest", new EmployeeRequest());
		model.addAttribute("employees", employeeService.getDetailedEmployees(search));
		return "admin/employees/employees";
	}
	
	@PostMapping
	public String saveEmployee(@ModelAttribute("employeeRequest") EmployeeRequest employeeRequest) {
		employeeService.addEmployee(employeeRequest);
		return "redirect:/admin/employees";
	}
	
	@GetMapping("/{id}")
	public String getEmployeeById(@PathVariable UUID id, Model model) {
		model.addAttribute("activePage", "admin/employees");
		model.addAttribute("employeeId", id);
		model.addAttribute("employeeRequest", new EmployeeRequest(employeeService.getEmployeeDetailedResponseById(id)));
		return "admin/employees/employee";
	}
	
	@PutMapping("/{id}")
	public String updateEmplyee(@PathVariable UUID id, @ModelAttribute("employeeRequest") EmployeeRequest employeeRequest) {
		employeeService.updateEmployee(id, employeeRequest);
		return "redirect:/admin/employees";
	}
	
	@DeleteMapping("/{id}")
	public String deleteEmployee(@PathVariable UUID id) {
		employeeService.deleteEmployee(id);
		return "redirect:/admin/employees";
	}
	
}
