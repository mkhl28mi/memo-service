package io.github.mkhl28mi.memo_service.domain.employee.dto.response;

import java.util.Objects;
import java.util.UUID;

public record EmployeeOptionResponse(UUID employeeId,
		UUID positionId,
		String employeeFullName,
		String employeeTargetFullName,
		String positionName, 
		String positionTargetName,
		int positionPlacementOrder) {
	
	public String getId() {
		return employeeId + ":" + positionId;
	}
	
	public String getNameLabel() {
		return employeeFullName + " - " + positionName;
	}
	
	public String getTargetNameLabel() {
		return employeeTargetFullName + " - " + positionTargetName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(employeeId, positionId);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeeOptionResponse other = (EmployeeOptionResponse) obj;
		return Objects.equals(employeeId, other.employeeId)
				&& Objects.equals(positionId, other.positionId);
	}

}
