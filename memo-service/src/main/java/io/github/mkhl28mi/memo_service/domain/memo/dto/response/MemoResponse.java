package io.github.mkhl28mi.memo_service.domain.memo.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.department.dto.response.DepartmentResponse;
import io.github.mkhl28mi.memo_service.domain.department_unit.dto.response.DepartmentUnitResponse;
import io.github.mkhl28mi.memo_service.domain.memo.entity.Memo.Status;
import io.github.mkhl28mi.memo_service.domain.memo_employee.dto.response.MemoEmployeeResponse;
import io.github.mkhl28mi.memo_service.domain.memo_label.dto.response.MemoLabelResponse;
import io.github.mkhl28mi.memo_service.domain.user.dto.response.UserResponse;

public record MemoResponse(UUID id, 
		String content,
		Status status, 
		UserResponse assignee,
		DepartmentUnitResponse departmentUnitResponse,
		DepartmentResponse departmentResponse,
		int sequenceNumber,
		int creationYear,
		LocalDateTime createdAt,
		LocalDateTime updatedAt,
		List<MemoEmployeeResponse> recipients,
		List<MemoEmployeeResponse> copyRecipients,
		List<MemoEmployeeResponse> signers,
		List<MemoEmployeeResponse> approvers,
		List<MemoLabelResponse> labels) {

}
