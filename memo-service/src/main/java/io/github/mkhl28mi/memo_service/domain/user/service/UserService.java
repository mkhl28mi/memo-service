package io.github.mkhl28mi.memo_service.domain.user.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.mkhl28mi.memo_service.domain.department_unit.entity.DepartmentUnit;
import io.github.mkhl28mi.memo_service.domain.department_unit.service.DepartmentUnitService;
import io.github.mkhl28mi.memo_service.domain.role.service.RoleService;
import io.github.mkhl28mi.memo_service.domain.user.dto.request.UserRequest;
import io.github.mkhl28mi.memo_service.domain.user.dto.response.UserResponse;
import io.github.mkhl28mi.memo_service.domain.user.entity.User;
import io.github.mkhl28mi.memo_service.domain.user.repository.UserRepository;
import io.github.mkhl28mi.memo_service.exception.ResourceNotFoundException;

@Service
@Transactional(readOnly = true)
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private DepartmentUnitService departmentUnitService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<UserResponse> getUsers(String search) {
    	if (search == null || search.trim().isEmpty()) {
    		return mapToUserResponse(userRepository.findAll()); 
    	} else {
    		return mapToUserResponse(userRepository.searchUsers(search.trim()));
    	}
	}
	
	public List<UserResponse> getEnabledUsersByDepartment(User user, String search) throws IllegalArgumentException {
		if (user == null) { throw new IllegalArgumentException("User cannot be null."); }
		
		if (search == null) { throw new IllegalArgumentException("Search cannot be null."); }
		
		return mapToUserResponse(userRepository.searchEnabledUsers(search, user.getDepartmentUnit().getDepartment()));		
 	}
	
	public User getUserById(UUID id) throws ResourceNotFoundException {
		return userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
	}
	
	public Optional<User> getUserByUsername(String username) throws IllegalArgumentException {
		if (username == null) { throw new IllegalArgumentException("Username cannot be null."); }
		
		return userRepository.findByUsername(username);
	}
	
	public UserResponse getUserResponseById(UUID id) throws ResourceNotFoundException {
		return new UserResponse(userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id)));
	}
	
	@Transactional
	public UserResponse addUser(UserRequest userRequest) {
		DepartmentUnit departmentUnit = departmentUnitService.getDepartmentUnitById(userRequest.departmentUnitId());
		
		var user = new User(userRequest.username(),
				passwordEncoder.encode(userRequest.password()), 
				userRequest.fullName(), 
				userRequest.cell(), 
				departmentUnit,
				true);
		
		userRequest.roleIds().forEach(roleId -> user.addRole(roleService.getRoleById(roleId)));
		
		return new UserResponse(userRepository.save(user));
	}
	
	@Transactional
	public UserResponse updateUser(UUID userId, UserRequest userRequest) throws ResourceNotFoundException {
		DepartmentUnit departmentUnit = departmentUnitService.getDepartmentUnitById(userRequest.departmentUnitId());
		
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
		
		user.setUsername(userRequest.username());
		user.setPassword(passwordEncoder.encode(userRequest.password()));
		user.setFullName(userRequest.fullName());
		user.setCell(userRequest.cell());
		user.setDepartmentUnit(departmentUnit);
		user.setEnabled(userRequest.enabled());
		
		new HashSet<>(user.getRoles()).forEach(user::removeRole);
		
		userRequest.roleIds().forEach(roleId -> user.addRole(roleService.getRoleById(roleId)));
		
		return new UserResponse(userRepository.save(user));
	}
	
	@Transactional
	public void deleteUser(UUID id) {
		userRepository.deleteById(id);
	}
	
    private static List<UserResponse> mapToUserResponse(List<User> users) {
    	return users.stream().map(UserResponse::new).toList();
    }
    
}
