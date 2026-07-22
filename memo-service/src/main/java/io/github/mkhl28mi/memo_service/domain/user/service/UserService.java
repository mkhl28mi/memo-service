package io.github.mkhl28mi.memo_service.domain.user.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.mkhl28mi.memo_service.domain.department_unit.entity.DepartmentUnit;
import io.github.mkhl28mi.memo_service.domain.department_unit.service.DepartmentUnitService;
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
	private DepartmentUnitService departmentUnitService;
	
	public List<UserResponse> getUsers(String search) {
    	if (search == null || search.trim().isEmpty()) {
    		return mapToUserResponse(userRepository.findAll()); 
    	} else {
    		return mapToUserResponse(userRepository.findByUsernameContainingOrFullNameContaining(search.trim(), search.trim()));
    	}
	}
		
	public User getUserById(UUID id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
	}
	
	public UserResponse getUserResponseById(UUID id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
		return new UserResponse(user);
	}
	
	@Transactional
	public UserResponse addUser(UserRequest userRequest) {
		DepartmentUnit departmentUnit = departmentUnitService.getDepartmentUnitById(userRequest.departmentUnitId());
		return new UserResponse(userRepository.save(new User(userRequest.username(),
				userRequest.password(), 
				userRequest.fullName(), 
				userRequest.cell(), 
				departmentUnit)));
	}
	
	@Transactional
	public UserResponse updateUser(UUID userId, UserRequest userRequest) {
		DepartmentUnit departmentUnit = departmentUnitService.getDepartmentUnitById(userRequest.departmentUnitId());
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
		user.setUsername(userRequest.username());
		user.setPassword(userRequest.password());
		user.setFullName(userRequest.fullName());
		user.setCell(userRequest.cell());
		user.setPosition(departmentUnit);
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
