package io.github.mkhl28mi.memo_service.domain.admin.user.role.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.mkhl28mi.memo_service.domain.admin.user.role.entity.Role;
import io.github.mkhl28mi.memo_service.domain.admin.user.role.entity.Role.RoleType;

public interface RoleRepository extends JpaRepository<Role, UUID> {
	
	public Optional<Role> findByName(RoleType name);
	
}
