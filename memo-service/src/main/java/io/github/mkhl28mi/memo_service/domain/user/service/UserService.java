package io.github.mkhl28mi.memo_service.domain.user.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.mkhl28mi.memo_service.domain.department.entity.Department;
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
	private PasswordEncoder passwordEncoder;
	
	public List<UserResponse> getUsers(String search) {
    	if (search == null || search.trim().isEmpty()) {
    		return mapToUserResponse(userRepository.findAll()); 
    	} else {
    		return mapToUserResponse(userRepository.searchByFullnameOrUsername(search.trim()));
    	}
	}
	
//	public List<UserResponse> getEnabledUsersByDepartment(Department department, String search) throws IllegalArgumentException {
//		if (search == null) { throw new IllegalArgumentException("Search cannot be null."); }
//		
//		return mapToUserResponse(userRepository.searchEnabledByFullnameAndDepartment(search, department));		
// 	}
	
	public User getUserById(UUID id) throws ResourceNotFoundException {
		return userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
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
		var user = new User(userRequest.username(),
				passwordEncoder.encode(userRequest.password()), 
				userRequest.fullName(), 
				userRequest.cell(), 
				true);
		
		userRequest.roleIds().forEach(roleId -> user.addRole(roleService.getRoleById(roleId)));
		
		return new UserResponse(userRepository.save(user));
	}
	
	@Transactional
	public UserResponse updateUser(UUID userId, UserRequest userRequest) throws ResourceNotFoundException {		
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
		
		user.setUsername(userRequest.username());
		user.setPassword(passwordEncoder.encode(userRequest.password()));
		user.setFullName(userRequest.fullName());
		user.setCell(userRequest.cell());
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
