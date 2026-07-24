package io.github.mkhl28mi.memo_service.domain.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.mkhl28mi.memo_service.domain.employee.dto.response.EmployeeResponse;
import io.github.mkhl28mi.memo_service.domain.employee.service.EmployeeService;

@RestController
@RequestMapping("/api/v1/admin/employees")
public class EmployeeRestController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/search")
	public List<EmployeeResponse> searchEmployees(@RequestParam("q") String query) {
		return employeeService.getEmployees(query);
	}

}
