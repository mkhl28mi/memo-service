package io.github.mkhl28mi.memo_service.domain.employee_position.dto.response;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.employee_position.entity.EmployeePosition;

public record EmployeePositionResponse(UUID id, 
		String name, 
		String targetName, 
		int placementOrder,
		LocalDateTime createdAt) {
	
	public EmployeePositionResponse(EmployeePosition employeePosition) {
		this(employeePosition.getId(),
				employeePosition.getName(),
				employeePosition.getTargetName(),
				employeePosition.getPlacementOrder(),
				employeePosition.getCreatedAt());
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
		EmployeePositionResponse other = (EmployeePositionResponse) obj;
		return Objects.equals(id, other.id);
	}
	
}
