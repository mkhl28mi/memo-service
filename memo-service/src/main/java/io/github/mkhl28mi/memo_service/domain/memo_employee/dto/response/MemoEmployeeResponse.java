package io.github.mkhl28mi.memo_service.domain.memo_employee.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.employee.dto.response.EmployeeBasicResponse;
import io.github.mkhl28mi.memo_service.domain.memo_employee.entity.MemoEmployee.Role;
import io.github.mkhl28mi.memo_service.domain.position.dto.response.PositionResponse;

public record MemoEmployeeResponse(UUID id,
		EmployeeBasicResponse employeeBasicResponse,
		PositionResponse positionResponse,
		Role role,
		int placementOrder,
		LocalDateTime createdAt) {
	
	public String getOptionId() {
		return employeeBasicResponse.id() + ":" + positionResponse.id();
	}
	
	public String getOptionNameLabel() {
		return employeeBasicResponse.fullName() + " - " + positionResponse.name();
	}
	
	public String getOptionTargetNameLabel() {
		return employeeBasicResponse.targetFullName() + " - " + positionResponse.targetName();
	}
	
}
