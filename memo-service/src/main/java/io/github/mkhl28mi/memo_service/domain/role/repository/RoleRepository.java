package io.github.mkhl28mi.memo_service.domain.role.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.mkhl28mi.memo_service.domain.role.entity.Role;

public interface RoleRepository extends JpaRepository<Role, UUID> {
	
}
