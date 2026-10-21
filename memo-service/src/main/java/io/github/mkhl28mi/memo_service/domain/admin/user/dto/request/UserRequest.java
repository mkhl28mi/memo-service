package io.github.mkhl28mi.memo_service.domain.admin.user.dto.request;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.admin.user.dto.response.UserResponse;
import io.github.mkhl28mi.memo_service.domain.admin.user.role.dto.response.RoleResponse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequest(
		@NotNull(message = "Username cannot be null") @Size(min = 1, max = 25, message = "Username must be between 1 and 25 characters") String username, 
		@NotNull(message = "Password cannot be null") @Size(min = 8, max = 60, message = "Username must be between 8 and 60 characters") String password, 
		@NotNull(message = "Full name cannot be null") @Size(min = 1, max = 50, message = "Full name must be between 1 and 50 characters") String fullName,
		@NotNull(message = "Cell cannot be null") @Size(min = 1, max = 20, message = "Cell must be between 1 and 20 characters") String cell,
		@NotBlank(message = "Email cannot be blank") @Email(message = "Email is not correct") String email,
		boolean enabled,
		List<UUID> roleIds) {
	
	public UserRequest() {
		this("", "", "", "", "", true, Collections.emptyList());
	}
	
	public UserRequest(UserResponse userResponse) {
		this(userResponse.username(),
				"",
				userResponse.fullName(),
				userResponse.cell(),
				userResponse.email(),
				userResponse.enabled(),
				userResponse.roleRespones().stream().map(RoleResponse::id).toList());
	}
	
}
