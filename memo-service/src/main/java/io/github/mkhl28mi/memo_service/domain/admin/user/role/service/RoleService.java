package io.github.mkhl28mi.memo_service.domain.admin.user.role.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.mkhl28mi.memo_service.domain.admin.user.role.dto.response.RoleResponse;
import io.github.mkhl28mi.memo_service.domain.admin.user.role.entity.Role;
import io.github.mkhl28mi.memo_service.domain.admin.user.role.entity.Role.RoleType;
import io.github.mkhl28mi.memo_service.domain.admin.user.role.repository.RoleRepository;
import io.github.mkhl28mi.memo_service.exception.ResourceNotFoundException;

@Service
@Transactional(readOnly = true)
public class RoleService {
	
	private final RoleRepository roleRepository;
	
	public RoleService(RoleRepository roleRepository) {
		super();
		this.roleRepository = roleRepository;
	}

	public List<RoleResponse> getRoles() {
		return roleRepository.findAll().stream()
				.map(RoleResponse::new)
				.toList();
	}
	
	public Role getRoleById(UUID id) throws ResourceNotFoundException {
		return roleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
	}
	
	public Optional<Role> getRoleByName(RoleType name) {
		return roleRepository.findByName(name);
	}
	
	public long getCount() {
		return roleRepository.count();
	}
 	
	@Transactional
	public RoleResponse addRole(RoleType name) {
		return new RoleResponse(roleRepository.save(new Role(name)));
	}
	
}
