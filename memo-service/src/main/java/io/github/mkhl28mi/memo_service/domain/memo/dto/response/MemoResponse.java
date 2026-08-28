package io.github.mkhl28mi.memo_service.domain.memo.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.admin.department.dto.response.DepartmentResponse;
import io.github.mkhl28mi.memo_service.domain.admin.user.assignment.dto.response.UserAssignmentResponse;
import io.github.mkhl28mi.memo_service.domain.memo.employee.dto.response.MemoEmployeeResponse;
import io.github.mkhl28mi.memo_service.domain.memo.entity.Memo.Status;
import io.github.mkhl28mi.memo_service.domain.memo.label.dto.response.MemoLabelResponse;

public record MemoResponse(UUID id, 
		String content,
		Status status, 
		UserAssignmentResponse assignee,
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
	
	public String getMemoNumberPattern() {
	    int yearFormatted = (creationYear >= 2100) ? (creationYear % 1000) : (creationYear % 100);

	    String yearPattern = (creationYear >= 2100) ? "%03d" : "%02d";

	    return "%s/%s-%s".formatted(
	        departmentResponse.code(),
	        sequenceNumber,
	        yearPattern.formatted(yearFormatted)
	    );
	}
	
}
