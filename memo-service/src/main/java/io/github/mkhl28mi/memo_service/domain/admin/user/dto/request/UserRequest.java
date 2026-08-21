package io.github.mkhl28mi.memo_service.domain.admin.user.dto.request;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.admin.user.dto.response.UserResponse;
import io.github.mkhl28mi.memo_service.domain.admin.user.role.dto.response.RoleResponse;

public record UserRequest(String username, 
		String password, 
		String fullName,
		String cell,
		boolean enabled,
		List<UUID> roleIds) {
	
	public UserRequest() {
		this("", "", "", "", true, Collections.emptyList());
	}
	
	public UserRequest(UserResponse userResponse) {
		this(userResponse.username(),
				"",
				userResponse.fullName(),
				userResponse.cell(),
				userResponse.enabled(),
				userResponse.roleRespones().stream().map(RoleResponse::id).toList());
	}
	
}
