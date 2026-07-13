package io.github.mkhl28mi.memo_service.domain.department_unit.dto.response;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.department.entity.Department;
import io.github.mkhl28mi.memo_service.domain.department_unit.entity.DepartmentUnit;

public record DepartmentUnitResponse(UUID id, String code, Department department, LocalDateTime createdAt) {
	
	public DepartmentUnitResponse(DepartmentUnit departmentUnit) {
		this(departmentUnit.getId(), departmentUnit.getCode(), departmentUnit.getDepartment(),departmentUnit.getCreatedAt());
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
		DepartmentUnitResponse other = (DepartmentUnitResponse) obj;
		return Objects.equals(id, other.id);
	}
	
}
