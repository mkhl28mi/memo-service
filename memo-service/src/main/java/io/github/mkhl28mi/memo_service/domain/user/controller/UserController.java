package io.github.mkhl28mi.memo_service.domain.user.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.mkhl28mi.memo_service.domain.user.dto.request.UserRequest;
import io.github.mkhl28mi.memo_service.domain.user.service.UserService;

@Controller
@RequestMapping("/admin/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public String getEmployees(@RequestParam(required = false) String search, Model model) {
		model.addAttribute("users", userService.getUsers(search));
		model.addAttribute("userRequest", new UserRequest());
		model.addAttribute("activePage", "admin/users");
		return "admin/users/users";
	}
	
	@PostMapping
	public String addUser(@ModelAttribute("userRequest") UserRequest userRequest) {
		userService.addUser(userRequest);
		return "redirect:/admin/users";
	}
	
	@GetMapping("/{id}")
	public String getUserById(@PathVariable UUID id, Model model) {
		model.addAttribute("userId", id);
		model.addAttribute("userRequest", new UserRequest(userService.getUserResponseById(id)));
		model.addAttribute("activePage", "admin/users");
		return "admin/users/user";
	}
	
	@PutMapping("/{id}")
	public String updateUser(@PathVariable UUID id, @ModelAttribute("userRequest") UserRequest userRequest) {
		userService.updateUser(id, userRequest);
		return "redirect:/admin/users";
	}
	
	@DeleteMapping("/{id}")
	public String deleteEmployee(@PathVariable UUID id) {
		userService.deleteUser(id);
		return "redirect:/admin/users";
	}
	
}
