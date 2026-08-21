package io.github.mkhl28mi.memo_service.domain.admin.user.assignment.dto.response;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.admin.department.unit.dto.response.DepartmentUnitResponse;
import io.github.mkhl28mi.memo_service.domain.admin.user.assignment.entity.UserAssignment;
import io.github.mkhl28mi.memo_service.domain.admin.user.dto.response.UserResponse;

public record UserAssignmentResponse(UUID id, DepartmentUnitResponse departmentUnitResponse, UserResponse userResponse, LocalDateTime startDate, Optional<LocalDateTime> endDate) {

	public UserAssignmentResponse(UserAssignment userAssignment) {
		this(userAssignment.getId(),
				new DepartmentUnitResponse(userAssignment.getDepartmentUnit()), 
				new UserResponse(userAssignment.getUser()), 
				userAssignment.getStartDate(),
				userAssignment.getEndDate());
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
		UserAssignmentResponse other = (UserAssignmentResponse) obj;
		return Objects.equals(id, other.id);
	}

}
