package io.github.mkhl28mi.memo_service.domain.memo.employee.dto.response;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.dto.response.EmployeeAssignmentResponse;
import io.github.mkhl28mi.memo_service.domain.memo.employee.entity.MemoEmployee.Role;

public record MemoEmployeeResponse(UUID id,
		EmployeeAssignmentResponse employeeAssignmentResponse,
		Role role,
		int placementOrder,
		LocalDateTime createdAt) {

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
		MemoEmployeeResponse other = (MemoEmployeeResponse) obj;
		return Objects.equals(id, other.id);
	}
	
}
