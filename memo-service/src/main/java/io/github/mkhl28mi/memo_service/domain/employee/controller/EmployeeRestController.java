package io.github.mkhl28mi.memo_service.domain.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.mkhl28mi.memo_service.domain.position.dto.response.PositionResponse;
import io.github.mkhl28mi.memo_service.domain.position.service.PositionService;

@RestController
@RequestMapping("/api/v1/admin/employees")
public class EmployeeRestController {
	
	@Autowired
	private PositionService positionService;
	
	@GetMapping("/enabled-positions")
	public List<PositionResponse> getEnabledPositionOptions(@RequestParam("q") String query) {
		return positionService.getEnabledPostions(query);
	}

}
