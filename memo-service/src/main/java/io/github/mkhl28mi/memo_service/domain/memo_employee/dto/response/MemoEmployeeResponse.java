package io.github.mkhl28mi.memo_service.domain.memo_employee.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.admin.employee.dto.response.EmployeeResponse;
import io.github.mkhl28mi.memo_service.domain.admin.position.dto.response.PositionResponse;
import io.github.mkhl28mi.memo_service.domain.memo_employee.entity.MemoEmployee.Role;

public record MemoEmployeeResponse(UUID id,
		EmployeeResponse employeeBasicResponse,
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
