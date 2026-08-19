package io.github.mkhl28mi.memo_service.domain.memo_label.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.department.unit.dto.response.DepartmentUnitResponse;
import io.github.mkhl28mi.memo_service.domain.user.dto.response.UserResponse;

public record MemoLabelResponse(UUID id,
		UserResponse createdBy,
		DepartmentUnitResponse departmentUnitResponse,
		String name,
		LocalDateTime createdAt) {
}
