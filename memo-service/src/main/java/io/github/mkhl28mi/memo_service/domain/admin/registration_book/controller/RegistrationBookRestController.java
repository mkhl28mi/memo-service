package io.github.mkhl28mi.memo_service.domain.admin.registration_book.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.mkhl28mi.memo_service.config.security.CustomUserDetails;
import io.github.mkhl28mi.memo_service.domain.admin.employee.dto.response.EmployeeResponse;
import io.github.mkhl28mi.memo_service.domain.admin.employee.service.EmployeeService;
import io.github.mkhl28mi.memo_service.domain.admin.user.assignment.service.UserAssignmentService;
import io.github.mkhl28mi.memo_service.domain.admin.user.dto.response.UserResponse;
import io.github.mkhl28mi.memo_service.domain.admin.user.service.UserService;
import io.github.mkhl28mi.memo_service.domain.memo.label.service.MemoLabelService;

@RestController
@RequestMapping("/api/v1/admin/registration-book")
public class RegistrationBookRestController {
	
	private final UserAssignmentService userAssignmentService;
	
	private final MemoLabelService memoLabelService;
	
	private final UserService userService;
	
	private final EmployeeService employeeService;
	
	public RegistrationBookRestController(UserAssignmentService userAssignmentService, MemoLabelService memoLabelService, UserService userService, EmployeeService employeeService) {
		super();
		this.userAssignmentService = userAssignmentService;
		this.memoLabelService = memoLabelService;
		this.userService = userService;
		this.employeeService = employeeService;
	}

	@GetMapping("/all-assignees")
	public List<UserResponse> getAllAssignees(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam("q") String query) {
		var currentUserAssignment = userAssignmentService.getCurrentUserAssignmentResponseByUserId(userDetails.getUser().getId());
		
		return userService.getAllUsersByDepartmentId(currentUserAssignment.departmentUnitResponse().departmentResponse().id(), query);
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
