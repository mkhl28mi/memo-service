package io.github.mkhl28mi.memo_service.domain.memo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.mkhl28mi.memo_service.config.security.CustomUserDetails;
import io.github.mkhl28mi.memo_service.domain.employee.dto.response.EmployeeOptionResponse;
import io.github.mkhl28mi.memo_service.domain.employee.service.EmployeeService;
import io.github.mkhl28mi.memo_service.domain.memo_label.service.MemoLabelService;
import io.github.mkhl28mi.memo_service.domain.user.dto.response.UserResponse;
import io.github.mkhl28mi.memo_service.domain.user.service.UserService;

@RestController
@RequestMapping("/api/v1/memos")
public class MemoRestController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private MemoLabelService memoLabelService;
	
	@GetMapping("/enabled-assignees")
	public List<UserResponse> getEnabledAssigneeOptions(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam("q") String query) {
		return userService.getEnabledUsersByDepartment(userDetails.getUser(), query);
	}
	
	@GetMapping("/enabled-employees")
	public List<EmployeeOptionResponse> getEnabledEmployeeOptions(@RequestParam("q") String query) {
		return employeeService.getEnabledEmployeeOptions(query);
	}
	
	@GetMapping("/labels")
	public List<String> getLabelsOptions(@RequestParam("q") String query) {
		return memoLabelService.getDistinctLabelsAsString(query);
	}
	
}
