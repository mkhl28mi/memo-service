package io.github.mkhl28mi.memo_service.domain.employee.dto.request;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.employee.dto.response.EmployeeResponse;
import io.github.mkhl28mi.memo_service.domain.employee_position.dto.response.EmployeePositionResponse;

public record EmployeeRequest(String fullName, String targetFullName, List<UUID> employeePositionIds) {
	
	public EmployeeRequest() {
		this("", "", Collections.emptyList());
	}
	
	public EmployeeRequest(EmployeeResponse employeeResponse) {
		this(employeeResponse.fullName(),
				employeeResponse.targetFullName(),
				employeeResponse.employeePositionResponses().stream().map(EmployeePositionResponse::id).toList());
	}
	
}
