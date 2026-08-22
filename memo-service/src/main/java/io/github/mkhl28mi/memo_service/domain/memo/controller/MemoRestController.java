package io.github.mkhl28mi.memo_service.domain.memo.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.mkhl28mi.memo_service.config.security.CustomUserDetails;
import io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.dto.response.EmployeeAssignmentResponse;
import io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.service.EmployeeAssignmentService;
import io.github.mkhl28mi.memo_service.domain.admin.user.assignment.dto.response.UserAssignmentResponse;
import io.github.mkhl28mi.memo_service.domain.admin.user.assignment.service.UserAssignmentService;
import io.github.mkhl28mi.memo_service.domain.memo.label.service.MemoLabelService;

@RestController
@RequestMapping("/api/v1/memos")
public class MemoRestController {
	
	private final UserAssignmentService userAssignmentService;
	
	private final EmployeeAssignmentService employeeAssignmentService;
	
	private final MemoLabelService memoLabelService;
	
	public MemoRestController(UserAssignmentService userAssignmentService, EmployeeAssignmentService employeeAssignmentService, MemoLabelService memoLabelService) {
		super();
		this.userAssignmentService = userAssignmentService;
		this.employeeAssignmentService = employeeAssignmentService;
		this.memoLabelService = memoLabelService;
	}
	
	@GetMapping("/enabled-assignees")
	public List<UserAssignmentResponse> getEnabledAssigneeOptions(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam("q") String query) {
		return userAssignmentService.getEnabledUserAssignments(userDetails.getUser(), query);
	}
	
	@GetMapping("/enabled-employees")
	public List<EmployeeAssignmentResponse> getEnabledEmployeeOptions(@RequestParam("q") String query) {
		return employeeAssignmentService.getEnabledEmployeeAssignments(query);
	}
	
	@GetMapping("/distinct-labels")
	public List<String> getLabelsOptions(@RequestParam("q") String query) {
		return memoLabelService.getDistinctLabelsAsString(query);
	}
	
}
