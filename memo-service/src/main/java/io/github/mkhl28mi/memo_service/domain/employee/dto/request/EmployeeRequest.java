package io.github.mkhl28mi.memo_service.domain.employee.dto.request;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.employee.dto.response.EmployeeDetailedResponse;
import io.github.mkhl28mi.memo_service.domain.position.dto.response.PositionResponse;

public record EmployeeRequest(String fullName, String targetFullName, boolean enabled, List<UUID> positionIds) {
	
	public EmployeeRequest() {
		this("", "", true, Collections.emptyList());
	}
	
	public EmployeeRequest(EmployeeDetailedResponse employeeResponse) {
		this(employeeResponse.fullName(),
				employeeResponse.targetFullName(),
				employeeResponse.enabled(),
				employeeResponse.positionResponses().stream().map(PositionResponse::id).toList());
	}
	
}
