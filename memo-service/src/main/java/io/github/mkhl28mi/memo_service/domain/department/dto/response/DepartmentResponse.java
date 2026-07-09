package io.github.mkhl28mi.memo_service.domain.department.dto.response;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.department.entity.Department;

public record DepartmentResponse(UUID id, String name, String code, String description, LocalDateTime createdAt) {
	
	public DepartmentResponse(Department department) {
		this(department.getId(), department.getName(), department.getCode(), department.getDescription(), department.getCreatedAt());
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
		DepartmentResponse other = (DepartmentResponse) obj;
		return Objects.equals(id, other.id);
	}
}
