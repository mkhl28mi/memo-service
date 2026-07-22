package io.github.mkhl28mi.memo_service.domain.employee.dto.request;

import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.employee.dto.response.EmployeeResponse;

public record EmployeeRequest(String fullName, String targetFullName, UUID employeePositionId) {
	
	public EmployeeRequest() {
		this("", "", null);
	}
	
	public EmployeeRequest(EmployeeResponse employeeResponse) {
		this(employeeResponse.fullName(),
				employeeResponse.targetFullName(),
				employeeResponse.employeePositionResponse().id());
	}
	
}
