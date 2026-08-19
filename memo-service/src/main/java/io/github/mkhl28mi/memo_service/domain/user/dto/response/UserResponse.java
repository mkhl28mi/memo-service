package io.github.mkhl28mi.memo_service.domain.user.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.role.dto.response.RoleResponse;
import io.github.mkhl28mi.memo_service.domain.user.entity.User;

public record UserResponse(UUID id,
		String username,
		String fullName,
		String cell,
		boolean enabled,
		LocalDateTime createdAt,
		LocalDateTime updatedAt,
		List<RoleResponse> roleRespones) {
	
	public UserResponse(User user) {
		this(user.getId(),
				user.getUsername(),
				user.getFullName(),
				user.getCell(),
				user.isEnabled(),
				user.getCreatedAt(),
				user.getUpdatedAt(),
				user.getRoles().stream().map(RoleResponse::new).toList());
	}
	
}
