package io.github.mkhl28mi.memo_service.domain.memo.dto.request;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record MemoRequest(@NotEmpty(message = "Content cannot be empty") String content,
		@NotEmpty(message = "Recipients are required. At least 1") List<String> recipientIds,
		@NotNull(message = "Copy recipients are required") List<String> copyRecipientIds,
		@NotEmpty(message = "Signers are required. At least 1") List<String> signerIds,
		@NotNull(message = "Approvers are required") List<String> approverIds,
		@NotNull(message = "Assignee is required") UUID assigneeId,
		@NotNull(message = "Labels are required") List<String> labels) {
	
	public MemoRequest() {
		this("", 
				Collections.emptyList(),
				Collections.emptyList(),
				Collections.emptyList(),
				Collections.emptyList(),
				null,
				Collections.emptyList());
	}

}
