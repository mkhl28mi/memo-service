package io.github.mkhl28mi.memo_service.domain.admin.department.dto.request;

import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.admin.department.dto.response.DepartmentResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DepartmentRequest(@NotNull(message = "Name cannot be null") @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")String name, 
		@NotNull(message = "Code cannot be null") @Size(min = 1, max = 8, message = "Code must be between 1 and 8 characters") String code, 
		@NotNull(message = "Description cannot be null") @Size(min = 1, max = 250, message = "Description must be between 1 and 250 characters") String description, 
		@NotNull(message = "Position cannot be null") UUID positionId, 
		boolean enabled) {
	
	public DepartmentRequest() {
		this("", "", "", null, true);
	}
	
	public DepartmentRequest(DepartmentResponse departmentResponse) {
		this(departmentResponse.name(), departmentResponse.code(), departmentResponse.description(), departmentResponse.positionResponse().id(), departmentResponse.enabled());
	}
	
}
