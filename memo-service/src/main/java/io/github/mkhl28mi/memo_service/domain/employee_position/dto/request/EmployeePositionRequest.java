package io.github.mkhl28mi.memo_service.domain.employee_position.dto.request;

import io.github.mkhl28mi.memo_service.domain.employee_position.dto.response.EmployeePositionResponse;

public record EmployeePositionRequest(String name, String targetName, int placementOrder) {
	
	public EmployeePositionRequest() {
		this("", "", 0);
	}
	
	public EmployeePositionRequest(EmployeePositionResponse employeePositionResponse) {
		this(employeePositionResponse.name(), employeePositionResponse.targetName(), employeePositionResponse.placementOrder());
	}
	
}
