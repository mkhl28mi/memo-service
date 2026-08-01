package io.github.mkhl28mi.memo_service.domain.department.dto.response;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.department.entity.Department;
import io.github.mkhl28mi.memo_service.domain.position.dto.response.PositionResponse;

public record DepartmentResponse(UUID id, String name, String code, String description, PositionResponse positionResponse, boolean enabled, LocalDateTime createdAt, LocalDateTime updatedAt) {
	
	public DepartmentResponse(Department department) {
		this(department.getId(), 
				department.getName(), 
				department.getCode(), 
				department.getDescription(), 
				new PositionResponse(department.getPosition()),
				department.isEnabled(),
				department.getCreatedAt(),
				department.getUpdatedAt());
	}
	
	public String getDepartmentLabel() {
		return name + " - " + description;
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
