package io.github.mkhl28mi.memo_service.domain.user.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.department_unit.dto.response.DepartmentUnitResponse;
import io.github.mkhl28mi.memo_service.domain.user.entity.User;

public record UserResponse(UUID id,
		String username,
		String fullName,
		String cell,
		DepartmentUnitResponse departmentUnitResponse,
		LocalDateTime createdAt) {
	
	public UserResponse(User user) {
		this(user.getId(),
				user.getUsername(),
				user.getFullName(),
				user.getCell(),
				new DepartmentUnitResponse(user.getPosition()),
				user.getCreatedAt());
	}
	
}
