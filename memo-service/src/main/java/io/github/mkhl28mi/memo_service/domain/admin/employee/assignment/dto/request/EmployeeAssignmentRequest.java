
package io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.dto.request;

import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.dto.response.EmployeeAssignmentResponse;
import jakarta.validation.constraints.NotNull;

public record EmployeeAssignmentRequest(@NotNull(message = "Position cannot be null") UUID positionId) {
	
	public EmployeeAssignmentRequest() {
		this((UUID) null);
	}
	
	public EmployeeAssignmentRequest(EmployeeAssignmentResponse employeeAssignmentResponse) {
		this(employeeAssignmentResponse.positionResponse().id());
	}

}
