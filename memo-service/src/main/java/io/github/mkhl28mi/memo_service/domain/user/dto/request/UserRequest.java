package io.github.mkhl28mi.memo_service.domain.user.dto.request;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.role.dto.response.RoleResponse;
import io.github.mkhl28mi.memo_service.domain.user.dto.response.UserResponse;

public record UserRequest(String username, 
		String password, 
		String fullName,
		String cell,
		UUID departmentUnitId,
		boolean enabled,
		List<UUID> roleIds) {
	
	public UserRequest() {
		this("", "", "", "", null, true, Collections.emptyList());
	}
	
	public UserRequest(UserResponse userResponse) {
		this(userResponse.username(),
				"",
				userResponse.fullName(),
				userResponse.cell(),
				userResponse.departmentUnitResponse().id(),
				userResponse.enabled(),
				userResponse.roleRespones().stream().map(RoleResponse::id).toList());
	}
	
}
