package io.github.mkhl28mi.memo_service.domain.user.dto.request;

import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.user.dto.response.UserResponse;

public record UserRequest(String username, 
		String password, 
		String fullName,
		String cell,
		UUID departmentUnitId) {
	
	public UserRequest() {
		this("", "", "", "", null);
	}
	
	public UserRequest(UserResponse userResponse) {
		this(userResponse.username(),
				"",
				userResponse.fullName(),
				userResponse.cell(),
				userResponse.departmentUnitResponse().id());
	}
	
}
