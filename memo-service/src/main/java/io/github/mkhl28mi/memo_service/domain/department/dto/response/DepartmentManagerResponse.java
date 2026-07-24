package io.github.mkhl28mi.memo_service.domain.department.dto.response;

import java.util.Objects;
import java.util.UUID;

public record DepartmentManagerResponse(UUID employeeId, UUID departmentId, String fullName, String position, String department) {

	@Override
	public int hashCode() {
		return Objects.hash(departmentId, employeeId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DepartmentManagerResponse other = (DepartmentManagerResponse) obj;
		return Objects.equals(departmentId, other.departmentId) && Objects.equals(employeeId, other.employeeId);
	}
	
}
