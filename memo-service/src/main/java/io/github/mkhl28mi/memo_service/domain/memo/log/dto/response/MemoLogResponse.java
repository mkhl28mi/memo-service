package io.github.mkhl28mi.memo_service.domain.memo.log.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.admin.user.assignment.dto.response.UserAssignmentResponse;
import io.github.mkhl28mi.memo_service.domain.memo.log.entity.MemoLog;
import io.github.mkhl28mi.memo_service.domain.memo.log.entity.MemoLog.Status;

public record MemoLogResponse(UUID id, UserAssignmentResponse createdBy, Status status, LocalDateTime createdAt) {
	
	public MemoLogResponse(MemoLog memoLog) {
		this(memoLog.getId(), new UserAssignmentResponse(memoLog.getCreatedBy()), memoLog.getStatus(), memoLog.getCreatedAt());
	}
	
}
