package io.github.mkhl28mi.memo_service.domain.admin.employee.dto.request;

import io.github.mkhl28mi.memo_service.domain.admin.employee.dto.response.EmployeeResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EmployeeRequest(
		@NotNull(message = "Full name cannot be null") @Size(min = 1, max = 100, message = "Full name must be between 1 and 100 characters")String fullName, 
		@NotNull(message = "Target full name cannot be null") @Size(min = 1, max = 100, message = "Target full name must be between 1 and 100 characters")String targetFullName, 
		boolean enabled) {
	
	public EmployeeRequest() {
		this("", "", true);
	}
	
	public EmployeeRequest(EmployeeResponse employeeResponse) {
		this(employeeResponse.fullName(),
				employeeResponse.targetFullName(),
				employeeResponse.enabled());
	}
	
}
