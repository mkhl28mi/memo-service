package io.github.mkhl28mi.memo_service.domain.user.assignment.controller;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.mkhl28mi.memo_service.domain.user.assignment.service.UserAssignmentService;
import io.github.mkhl28mi.memo_service.domain.user.service.UserService;

@Controller
@RequestMapping("/admin/users/{userId}/assignments")
public class UserAssignmentController {
	
	private final UserAssignmentService userAssignmentService;
	
	private final UserService userService;
	
	public UserAssignmentController(UserAssignmentService userAssignmentService, UserService userService) {
		this.userAssignmentService = userAssignmentService;
		this.userService = userService;
	}
	
	@GetMapping
	public String getUserAssignments(@PathVariable UUID userId, Model model) {
		model.addAttribute("activePage", "admin/users");
		model.addAttribute("user", userService.getUserById(userId));
		model.addAttribute("assignments", userAssignmentService.getUserAssignments(userId));
		
		return "admin/users/assignments/assignments";
	}
	
	@PostMapping
	public String addUserAssignment(@PathVariable UUID userId, @RequestParam UUID departmentUnitId) {
		userAssignmentService.addUserAssignment(departmentUnitId, userId);
		
		return String.format("redirect:/admin/users/%s/assignments", userId);
	}

}
