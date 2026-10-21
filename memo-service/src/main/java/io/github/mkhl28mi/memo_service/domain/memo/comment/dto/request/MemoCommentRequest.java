package io.github.mkhl28mi.memo_service.domain.memo.comment.dto.request;

import io.github.mkhl28mi.memo_service.domain.memo.comment.dto.response.MemoCommentResponse;
import jakarta.validation.constraints.NotEmpty;

public record MemoCommentRequest(@NotEmpty(message = "Content cannot be empty") String content) {
	
	public MemoCommentRequest() {
		this((String) null);
	}
	
	public MemoCommentRequest(MemoCommentResponse memoCommentResponse) {
		this(memoCommentResponse.content());
	}

}
