package io.github.mkhl28mi.memo_service.domain.admin.employee.dto.request;

import io.github.mkhl28mi.memo_service.domain.admin.employee.dto.response.EmployeeResponse;

public record EmployeeRequest(String fullName, String targetFullName, boolean enabled) {
	
	public EmployeeRequest() {
		this("", "", true);
	}
	
	public EmployeeRequest(EmployeeResponse employeeResponse) {
		this(employeeResponse.fullName(),
				employeeResponse.targetFullName(),
				employeeResponse.enabled());
	}
	
}
