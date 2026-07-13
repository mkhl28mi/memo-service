package io.github.mkhl28mi.memo_service.domain.employee_position.controller;

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

import io.github.mkhl28mi.memo_service.domain.employee_position.dto.request.EmployeePositionRequest;
import io.github.mkhl28mi.memo_service.domain.employee_position.dto.response.EmployeePositionResponse;
import io.github.mkhl28mi.memo_service.domain.employee_position.service.EmployeePositionService;

@Controller
@RequestMapping("/admin/employee-positions")
public class EmployeePositionController {
	
	@Autowired
	private EmployeePositionService employeePositionService;
	
	@GetMapping
	public String getEmployeePositions(@RequestParam(required = false) String search, Model model) {
		model.addAttribute("employeePositions", employeePositionService.getEmployeePostions(search));
		model.addAttribute("employeePositionRequest", new EmployeePositionRequest());
		model.addAttribute("activePage", "admin/employee-positions");
        return "admin/employee-positions/employee-positions";
	}
	
	@PostMapping
	public String createEmployeePosition(@ModelAttribute("employeePositionRequest") EmployeePositionRequest employeePositionRequest) {
		employeePositionService.saveEmployeePosition(employeePositionRequest);
		return "redirect:/admin/employee-positions";
	}
	
	@GetMapping("/{id}")
	public String getEmployeePosition(@PathVariable UUID id, Model model) {
		EmployeePositionResponse employeePositionResponse = employeePositionService.getEmployeePositionById(id);
		model.addAttribute("employeePositionId", id);
		model.addAttribute("employeePositionRequest", new EmployeePositionRequest(employeePositionResponse));
		model.addAttribute("activePage", "admin/employee-positions");
		return "admin/employee-positions/employee-position";
	}
	
	@PutMapping("/{id}")
	public String updateEmployeePosition(@PathVariable UUID id, @ModelAttribute("employeePositionRequest") EmployeePositionRequest employeePositionRequest) {
		employeePositionService.updateEmployeePosition(id, employeePositionRequest);
		return "redirect:/admin/employee-positions";
	}
	
	@DeleteMapping("/{id}")
	public String deleteEmployeePosition(@PathVariable UUID id) {
		employeePositionService.deleteEmployeePosition(id);
		return "redirect:/admin/employee-positions";
	}
	
}
