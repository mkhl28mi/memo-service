package io.github.mkhl28mi.memo_service.domain.employee.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.employee.entity.Employee;
import io.github.mkhl28mi.memo_service.domain.employee_position.dto.response.EmployeePositionResponse;

public record EmployeeResponse(UUID id,
		String fullName,
		String targetFullName,
		LocalDateTime createdAt,
		List<EmployeePositionResponse> employeePositionResponses) {
	
	public EmployeeResponse(Employee employee) {
		this(employee.getId(),
				employee.getFullName(),
				employee.getTargetFullName(),
				employee.getCreatedAt(),
				employee.getEmployeePositions().stream().map(EmployeePositionResponse::new).toList());
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
		EmployeeResponse other = (EmployeeResponse) obj;
		return Objects.equals(id, other.id);
	}
	
}
