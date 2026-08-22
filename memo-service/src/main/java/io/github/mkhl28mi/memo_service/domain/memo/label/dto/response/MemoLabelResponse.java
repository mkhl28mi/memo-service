package io.github.mkhl28mi.memo_service.domain.memo.label.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.admin.user.assignment.dto.response.UserAssignmentResponse;

public record MemoLabelResponse(UUID id,
		UserAssignmentResponse createdBy,
		String name,
		LocalDateTime createdAt) {
}
