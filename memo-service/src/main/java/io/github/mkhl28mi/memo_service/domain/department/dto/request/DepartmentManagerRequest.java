package io.github.mkhl28mi.memo_service.domain.department.dto.request;

import java.util.UUID;

public record DepartmentManagerRequest(UUID departmentId, UUID employeeId) {
	
	public DepartmentManagerRequest() {
		this(null, null);
	}
	
}
