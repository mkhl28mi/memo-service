package io.github.mkhl28mi.memo_service.domain.user.assignment.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import io.github.mkhl28mi.memo_service.domain.department.unit.entity.DepartmentUnit;
import io.github.mkhl28mi.memo_service.domain.department.unit.service.DepartmentUnitService;
import io.github.mkhl28mi.memo_service.domain.user.assignment.dto.response.UserAssignmentResponse;
import io.github.mkhl28mi.memo_service.domain.user.assignment.entity.UserAssignment;
import io.github.mkhl28mi.memo_service.domain.user.assignment.repository.UserAssignmentRepository;
import io.github.mkhl28mi.memo_service.domain.user.entity.User;
import io.github.mkhl28mi.memo_service.domain.user.service.UserService;


@Service
@Transactional(readOnly = true)
public class UserAssignmentService {
	
	private final UserService userService;
	
	private final DepartmentUnitService departmentUnitService;
	
	private final UserAssignmentRepository userAssignmentRepository;
	
	public UserAssignmentService(UserService userService, DepartmentUnitService departmentUnitService, UserAssignmentRepository userAssignmentRepository) {
		this.userService = userService;
		this.departmentUnitService = departmentUnitService;
		this.userAssignmentRepository = userAssignmentRepository;
	}
	
	public List<UserAssignmentResponse> getUserAssignments(UUID userId) {
		return userAssignmentRepository.searchByUserId(userId).stream()
				.map(UserAssignmentResponse::new)
				.toList();
	}
	
	@Transactional
	public UserAssignmentResponse addUserAssignment(UUID departmentUintId, UUID userId) {
		DepartmentUnit departmentUnit = departmentUnitService.getDepartmentUnitById(departmentUintId);
		
		Assert.state(departmentUnit.isEnabled(), "DepartmentUnit must be enabled");
		
		Assert.state(departmentUnit.getDepartment().isEnabled(), "Department must be enabled");
		
		User user = userService.getUserById(userId);
		
		Assert.state(user.isEnabled(), "User must be enabled");
		
		return new UserAssignmentResponse(userAssignmentRepository.save(new UserAssignment(departmentUnit, user)));
	}
	
}
