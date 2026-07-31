package io.github.mkhl28mi.memo_service.domain.department_unit.dto.response;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.department.dto.response.DepartmentResponse;
import io.github.mkhl28mi.memo_service.domain.department_unit.entity.DepartmentUnit;

public record DepartmentUnitResponse(UUID id, String code, DepartmentResponse departmentResponse, boolean enabled, LocalDateTime createdAt, LocalDateTime updatedAt) {
	
	public DepartmentUnitResponse(DepartmentUnit departmentUnit) {
		this(departmentUnit.getId(), 
				departmentUnit.getCode(), 
				new DepartmentResponse(departmentUnit.getDepartment()), 
				departmentUnit.isEnabled(),
				departmentUnit.getCreatedAt(),
				departmentUnit.getUpdatedAt());
	}
	
	public String getDepartmentUnitLabel() {
		if (departmentResponse == null) { return  "(" + code + ")"; }
		return departmentResponse.name() + " (" + code + ")";
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
