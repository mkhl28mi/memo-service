package io.github.mkhl28mi.memo_service.domain.role.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.mkhl28mi.memo_service.domain.role.dto.response.RoleResponse;
import io.github.mkhl28mi.memo_service.domain.role.entity.Role;
import io.github.mkhl28mi.memo_service.domain.role.entity.Role.RoleType;
import io.github.mkhl28mi.memo_service.domain.role.repository.RoleRepository;
import io.github.mkhl28mi.memo_service.exception.ResourceNotFoundException;

@Service
@Transactional(readOnly = true)
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	public Role getRoleById(UUID id) {
		return roleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
	}
	
	public long getCount() {
		return roleRepository.count();
	}
 	
	@Transactional
	public RoleResponse addRole(String name) {
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("name cannot be null or empty.");
		}
		
		return new RoleResponse(roleRepository.save(new Role(RoleType.valueOf(name))));
	}
	
}
