package io.github.mkhl28mi.memo_service.domain.employee.dto.response;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.employee.entity.Employee;

public record EmployeeBasicResponse(UUID id,
		String fullName,
		String targetFullName,
		boolean enabled,
		LocalDateTime createdAt,
		LocalDateTime updatedAt) {
	
	public EmployeeBasicResponse(Employee employee) {
		this(employee.getId(),
				employee.getFullName(),
				employee.getTargetFullName(),
				employee.isEnabled(),
				employee.getCreatedAt(),
				employee.getUpdatedAt());	
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
		EmployeeBasicResponse other = (EmployeeBasicResponse) obj;
		return Objects.equals(id, other.id);
	}
	
}
