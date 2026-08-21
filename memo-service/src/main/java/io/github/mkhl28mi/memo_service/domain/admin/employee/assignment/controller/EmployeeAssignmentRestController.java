package io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.mkhl28mi.memo_service.domain.admin.position.dto.response.PositionResponse;
import io.github.mkhl28mi.memo_service.domain.admin.position.service.PositionService;

@RestController
@RequestMapping("/api/v1/admin/employees/assignments")
public class EmployeeAssignmentRestController {
	
	private final PositionService positionService;
	
	public EmployeeAssignmentRestController(PositionService positionService) {
		this.positionService = positionService;
	}
	
	@GetMapping("/enabled-positions")
	public List<PositionResponse> getEnabledPositions(@RequestParam("q") String query) {
		return positionService.getEnabledPostions(query);
	}

}
