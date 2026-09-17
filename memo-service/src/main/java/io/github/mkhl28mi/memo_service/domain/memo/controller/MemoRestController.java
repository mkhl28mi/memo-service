package io.github.mkhl28mi.memo_service.domain.memo.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.mkhl28mi.memo_service.config.security.CustomUserDetails;
import io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.dto.response.EmployeeAssignmentResponse;
import io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.service.EmployeeAssignmentService;
import io.github.mkhl28mi.memo_service.domain.admin.employee.dto.response.EmployeeResponse;
import io.github.mkhl28mi.memo_service.domain.admin.employee.service.EmployeeService;
import io.github.mkhl28mi.memo_service.domain.admin.user.assignment.dto.response.UserAssignmentResponse;
import io.github.mkhl28mi.memo_service.domain.admin.user.assignment.service.UserAssignmentService;
import io.github.mkhl28mi.memo_service.domain.admin.user.dto.response.UserResponse;
import io.github.mkhl28mi.memo_service.domain.admin.user.service.UserService;
import io.github.mkhl28mi.memo_service.domain.memo.label.service.MemoLabelService;

@RestController
@RequestMapping("/api/v1/memos")
@PreAuthorize("isAuthenticated()")
public class MemoRestController {
	
	private final UserAssignmentService userAssignmentService;
	
	private final EmployeeAssignmentService employeeAssignmentService;
	
	private final MemoLabelService memoLabelService;
	
	private final UserService userService;
	
	private final EmployeeService employeeService;
	
	public MemoRestController(UserAssignmentService userAssignmentService, EmployeeAssignmentService employeeAssignmentService, MemoLabelService memoLabelService, UserService userService, EmployeeService employeeService) {
		super();
		this.userAssignmentService = userAssignmentService;
		this.employeeAssignmentService = employeeAssignmentService;
		this.memoLabelService = memoLabelService;
		this.userService = userService;
		this.employeeService = employeeService;
	}

	@GetMapping("/enabled-assignees")
	public List<UserAssignmentResponse> getEnabledAssignees(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam("q") String query) {
		return userAssignmentService.getEnabledUserAssignments(userDetails.getUser(), query);
	}
	
	@GetMapping("/all-assignees")
	public List<UserResponse> getAllAssignees(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam("q") String query) {
		var currentUserAssignment = userAssignmentService.getCurrentUserAssignmentResponseByUserId(userDetails.getUser().getId());
		
		return userService.getAllUsersByDepartmentId(currentUserAssignment.departmentUnitResponse().departmentResponse().id(), query);
	}
	
	@GetMapping("/enabled-employees")
	public List<EmployeeAssignmentResponse> getEnabledEmployees(@RequestParam("q") String query) {
		return employeeAssignmentService.getEnabledEmployeeAssignments(query);
	}
	
	@GetMapping("/all-employees")
	public List<EmployeeResponse> getAllEmployees(@RequestParam("q") String query) {
		return employeeService.getEmployees(query);
	}
	
	@GetMapping("/distinct-labels")
	public List<String> getLabels(@RequestParam("q") String query) {
		return memoLabelService.getDistinctLabels(query);
	}
	
}
