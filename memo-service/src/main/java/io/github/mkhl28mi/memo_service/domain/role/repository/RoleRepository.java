package io.github.mkhl28mi.memo_service.domain.role.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.mkhl28mi.memo_service.domain.role.entity.Role;
import io.github.mkhl28mi.memo_service.domain.role.entity.Role.RoleType;

public interface RoleRepository extends JpaRepository<Role, UUID> {
	
	public Optional<Role> findByName(RoleType name);
	
}
