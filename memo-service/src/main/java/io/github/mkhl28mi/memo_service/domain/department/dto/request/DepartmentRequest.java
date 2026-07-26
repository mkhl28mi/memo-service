package io.github.mkhl28mi.memo_service.domain.department.dto.request;

import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.department.dto.response.DepartmentResponse;

public record DepartmentRequest(String name, String code, String description, UUID employeePositionId) {
	
	public DepartmentRequest() {
		this("", "", "", null);
	}
	
	public DepartmentRequest(DepartmentResponse departmentResponse) {
		this(departmentResponse.name(), departmentResponse.code(), departmentResponse.description(), departmentResponse.employeePositionResponse().id());
	}
	
}
