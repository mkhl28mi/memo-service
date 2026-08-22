package io.github.mkhl28mi.memo_service.domain.memo.dto.request;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import io.github.mkhl28mi.memo_service.domain.memo.dto.response.MemoResponse;
import io.github.mkhl28mi.memo_service.domain.memo.label.dto.response.MemoLabelResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record MemoRequest(@NotEmpty(message = "Content cannot be empty") String content,
		@NotEmpty(message = "Recipients are required. At least 1") List<UUID> recipientIds,
		@NotNull(message = "Copy recipients are required") List<UUID> copyRecipientIds,
		@NotEmpty(message = "Signers are required. At least 1") List<UUID> signerIds,
		@NotNull(message = "Approvers are required") List<UUID> approverIds,
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
	
	public MemoRequest(MemoResponse memoResponse) {
		this(memoResponse.content(),
				memoResponse.recipients().stream().map(e -> e.employeeAssignmentResponse().id()).toList(),  
				memoResponse.copyRecipients().stream().map(e -> e.employeeAssignmentResponse().id()).toList(), 
				memoResponse.signers().stream().map(e -> e.employeeAssignmentResponse().id()).toList(), 
				memoResponse.approvers().stream().map(e -> e.employeeAssignmentResponse().id()).toList(), 
				memoResponse.assignee().id(), 
				memoResponse.labels().stream().map(MemoLabelResponse::name).toList());
	}

}
