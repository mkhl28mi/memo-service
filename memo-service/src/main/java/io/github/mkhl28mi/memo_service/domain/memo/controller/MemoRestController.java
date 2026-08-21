package io.github.mkhl28mi.memo_service.domain.memo.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.mkhl28mi.memo_service.config.security.CustomUserDetails;
import io.github.mkhl28mi.memo_service.domain.admin.employee.service.EmployeeService;
import io.github.mkhl28mi.memo_service.domain.admin.user.dto.response.UserResponse;
import io.github.mkhl28mi.memo_service.domain.admin.user.service.UserService;
import io.github.mkhl28mi.memo_service.domain.memo_label.service.MemoLabelService;

@RestController
@RequestMapping("/api/v1/memos")
public class MemoRestController {
	
	private final EmployeeService userService;
	
	private final EmployeeService employeeService;
	
	private final MemoLabelService memoLabelService;
	
	public MemoRestController(EmployeeService userService, EmployeeService employeeService, MemoLabelService memoLabelService) {
		this.userService = userService;
		this.employeeService = employeeService;
		this.memoLabelService = memoLabelService;
	}
 	
	@GetMapping("/enabled-assignees")
	public List<UserResponse> getEnabledAssigneeOptions(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam("q") String query) {
//		return userService.getEnabledUsersByDepartment(userDetails.getUser(), query);
		return null;
	}
	
//	@GetMapping("/enabled-employees")
//	public List<EmployeeOptionResponse> getEnabledEmployeeOptions(@RequestParam("q") String query) {
//		return employeeService.getEnabledEmployeeOptions(query);
//	}
	
	@GetMapping("/distinct-labels")
	public List<String> getLabelsOptions(@RequestParam("q") String query) {
		return memoLabelService.getDistinctLabelsAsString(query);
	}
	
}
