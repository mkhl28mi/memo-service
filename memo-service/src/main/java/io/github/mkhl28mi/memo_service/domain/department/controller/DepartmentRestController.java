package io.github.mkhl28mi.memo_service.domain.department.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.mkhl28mi.memo_service.domain.department.dto.response.DepartmentResponse;
import io.github.mkhl28mi.memo_service.domain.department.service.DepartmentService;

@RestController
@RequestMapping("/api/v1/admin/departments")
public class DepartmentRestController {
	
	@Autowired
	private DepartmentService departmentService;
	
	@GetMapping("/search")
	public List<DepartmentResponse> searchDepartments(@RequestParam("q") String query) {
		return departmentService.getDepartments(query);
	}
	
}
