package io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.dto.response;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.entity.EmployeeAssignment;
import io.github.mkhl28mi.memo_service.domain.admin.employee.dto.response.EmployeeResponse;
import io.github.mkhl28mi.memo_service.domain.admin.position.dto.response.PositionResponse;

public record EmployeeAssignmentResponse(UUID id, PositionResponse positionResponse, EmployeeResponse employeeResponse, LocalDateTime startDate, Optional<LocalDateTime> endDate) {
	
	public EmployeeAssignmentResponse(EmployeeAssignment employeeAssignment) {
		this(employeeAssignment.getId(),
				new PositionResponse(employeeAssignment.getPosition()), 
				new EmployeeResponse(employeeAssignment.getEmployee()), 
				employeeAssignment.getStartDate(),
				employeeAssignment.getEndDate());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeeAssignmentResponse other = (EmployeeAssignmentResponse) obj;
		return Objects.equals(id, other.id);
	}

}
