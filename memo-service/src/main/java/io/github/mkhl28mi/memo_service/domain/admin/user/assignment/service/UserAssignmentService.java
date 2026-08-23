package io.github.mkhl28mi.memo_service.domain.admin.user.assignment.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import io.github.mkhl28mi.memo_service.domain.admin.department.unit.entity.DepartmentUnit;
import io.github.mkhl28mi.memo_service.domain.admin.department.unit.service.DepartmentUnitService;
import io.github.mkhl28mi.memo_service.domain.admin.user.assignment.dto.response.UserAssignmentResponse;
import io.github.mkhl28mi.memo_service.domain.admin.user.assignment.entity.UserAssignment;
import io.github.mkhl28mi.memo_service.domain.admin.user.assignment.repository.UserAssignmentRepository;
import io.github.mkhl28mi.memo_service.domain.admin.user.entity.User;
import io.github.mkhl28mi.memo_service.domain.admin.user.service.UserService;
import io.github.mkhl28mi.memo_service.exception.ResourceNotFoundException;


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
	
	public UserAssignment getUserAssignmentById(UUID id) throws ResourceNotFoundException {
		return userAssignmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("UserAssignment not found with ID: " + id));
	}
	
	public UserAssignment getCurrentUserAssignmentByUserId(UUID id) throws ResourceNotFoundException {
		return userAssignmentRepository.findCurrentByUserId(id)
				.orElseThrow(() -> new ResourceNotFoundException("UserAssignment not found with user`s ID: " + id));
	}
	
	public List<UserAssignmentResponse> getUserAssignments(UUID userId) {
		return userAssignmentRepository.searchByUserId(userId).stream()
				.map(UserAssignmentResponse::new)
				.toList();
	}
	
	public List<UserAssignmentResponse> getEnabledUserAssignments(User user, String search) throws IllegalArgumentException {
		if (search == null) { throw new IllegalArgumentException("Search cannot be null."); }
		
		UserAssignment currentUserAssignment =  userAssignmentRepository.findCurrentByUserId(user.getId())
				.orElseThrow(() -> new ResourceNotFoundException("UserAssignment not found with ID: " + user.getId()));
		
		return userAssignmentRepository.searchEnabledByFullnameAndDepartment(search, currentUserAssignment.getDepartmentUnit().getDepartment()).stream()
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
		
		LocalDateTime today = LocalDateTime.now();
		
		userAssignmentRepository.findCurrentByUserId(user.getId())
        .ifPresent(current -> {
            current.setEndDate(today);
            userAssignmentRepository.save(current);
        });
		
		return new UserAssignmentResponse(userAssignmentRepository.save(new UserAssignment(departmentUnit, user, today, null)));
	}
	
}
