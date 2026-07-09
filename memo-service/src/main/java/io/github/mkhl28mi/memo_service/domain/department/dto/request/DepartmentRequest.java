package io.github.mkhl28mi.memo_service.domain.department.dto.request;

import io.github.mkhl28mi.memo_service.domain.department.dto.response.DepartmentResponse;

public record DepartmentRequest(String name, String code, String description) {
	
	public DepartmentRequest() {
		this("", "", "");
	}
	
	public DepartmentRequest(DepartmentResponse departmentResponse) {
		this(departmentResponse.name(), departmentResponse.code(), departmentResponse.description());
	}
	
}
