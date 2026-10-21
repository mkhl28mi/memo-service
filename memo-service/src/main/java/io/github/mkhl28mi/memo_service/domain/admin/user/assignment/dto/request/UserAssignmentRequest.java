package io.github.mkhl28mi.memo_service.domain.admin.user.assignment.dto.request;

import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.admin.user.assignment.dto.response.UserAssignmentResponse;
import jakarta.validation.constraints.NotNull;

public record UserAssignmentRequest(@NotNull(message = "Department unit cannot be null") UUID departmentUnitId) {
	
	public UserAssignmentRequest() {
		this((UUID) null);
	}
	
	public UserAssignmentRequest(UserAssignmentResponse userAssignmentResponse) {
		this(userAssignmentResponse.departmentUnitResponse().id());
	}

}
