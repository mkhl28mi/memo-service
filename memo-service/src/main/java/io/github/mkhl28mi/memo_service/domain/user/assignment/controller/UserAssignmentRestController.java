package io.github.mkhl28mi.memo_service.domain.user.assignment.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.mkhl28mi.memo_service.domain.department_unit.dto.response.DepartmentUnitResponse;
import io.github.mkhl28mi.memo_service.domain.department_unit.service.DepartmentUnitService;

@RestController
@RequestMapping("/api/v1/admin/users/assignments")
public class UserAssignmentRestController {
	
	private final DepartmentUnitService departmentUnitService;
	
	public UserAssignmentRestController(DepartmentUnitService departmentUnitService) {
		this.departmentUnitService = departmentUnitService;
	}
	
	@GetMapping("/enabled-department-units")
	public List<DepartmentUnitResponse> getEnabledDepartmentUnitsOptions(@RequestParam("q") String query) {
		return departmentUnitService.getEnabledDepartmentUnits(query);
	}

}
