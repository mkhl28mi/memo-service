package io.github.mkhl28mi.memo_service.domain.role.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.role.entity.Role;
import io.github.mkhl28mi.memo_service.domain.role.entity.Role.RoleType;

public record RoleResponse(UUID id, RoleType name, LocalDateTime createdAt) {
	
	public RoleResponse(Role role) {
		this(role.getId(), role.getName(), role.getCreatedAt());
	}

}
