package io.github.mkhl28mi.memo_service.domain.memo.comment.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.admin.user.assignment.dto.response.UserAssignmentResponse;
import io.github.mkhl28mi.memo_service.domain.memo.comment.entity.MemoComment;

public record MemoCommentResponse(UUID id, UserAssignmentResponse createdBy, String content, LocalDateTime createdAt) {
	
	public MemoCommentResponse(MemoComment memoComment) {
		this(memoComment.getId(), new UserAssignmentResponse(memoComment.getCreatedBy()), memoComment.getContent(), memoComment.getCreatedAt());
	}
	
}
