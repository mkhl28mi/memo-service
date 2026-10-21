package io.github.mkhl28mi.memo_service.domain.admin.department.unit.dto.request;

import io.github.mkhl28mi.memo_service.domain.admin.department.unit.dto.response.DepartmentUnitResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DepartmentUnitRequest(@NotNull(message = "Code cannot be null") @Size(min = 1, max = 8, message = "Code must be between 1 and 8 characters") String code, 
		boolean enabled) {
	
	public DepartmentUnitRequest() {
		this("", false);
	}
	
	public DepartmentUnitRequest(DepartmentUnitResponse departmentUnitResponse) {
		this(departmentUnitResponse.code(), departmentUnitResponse.enabled());
	}

}
