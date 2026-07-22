package io.github.mkhl28mi.memo_service.domain.employee_position.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.mkhl28mi.memo_service.domain.employee_position.dto.response.EmployeePositionResponse;
import io.github.mkhl28mi.memo_service.domain.employee_position.service.EmployeePositionService;

@RestController
@RequestMapping("/api/v1/admin/employees/positions")
public class EmployeePositionRestController {
	
	@Autowired
	private EmployeePositionService employeePositionService;
	
	@GetMapping("/search")
	public List<EmployeePositionResponse> searchEmployeePositions(@RequestParam("q") String query) {
		return employeePositionService.getEmployeePostions(query);
	}

}
