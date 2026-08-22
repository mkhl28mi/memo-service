package io.github.mkhl28mi.memo_service.domain.admin.user.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.mkhl28mi.memo_service.domain.admin.user.dto.request.UserRequest;
import io.github.mkhl28mi.memo_service.domain.admin.user.dto.response.UserResponse;
import io.github.mkhl28mi.memo_service.domain.admin.user.entity.User;
import io.github.mkhl28mi.memo_service.domain.admin.user.repository.UserRepository;
import io.github.mkhl28mi.memo_service.domain.admin.user.role.service.RoleService;
import io.github.mkhl28mi.memo_service.exception.ResourceNotFoundException;

@Service
@Transactional(readOnly = true)
public class UserService {
	
	private final UserRepository userRepository;
	
	private final RoleService roleService;
	
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleService = roleService;
		this.passwordEncoder = passwordEncoder;
	}

	public List<UserResponse> getUsers(String search) {
    	if (search == null || search.trim().isEmpty()) {
    		return mapToUserResponse(userRepository.findAll()); 
    	} else {
    		return mapToUserResponse(userRepository.searchByFullnameOrUsername(search.trim()));
    	}
	}
	
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
				.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id)));
	}
	
	@Transactional
	public UserResponse addUser(UserRequest userRequest) {
		var user = new User(userRequest.username(),
				passwordEncoder.encode(userRequest.password()), 
				userRequest.fullName(), 
				userRequest.cell(), 
				userRequest.enabled());
		
		userRequest.roleIds().forEach(roleId -> user.addRole(roleService.getRoleById(roleId)));
		
		return new UserResponse(userRepository.save(user));
	}
	
	@Transactional
	public UserResponse updateUser(UUID userId, UserRequest userRequest) throws ResourceNotFoundException {		
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
		
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
