package io.github.mkhl28mi.memo_service.domain.memo.dto.request;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public record MemoRequest(String content, 
		List<String> recipientIds,
		List<String> copyRecipientIds,
		List<String> signerIds,
		List<String> approverIds,
		UUID assigneeId,
		List<String> labels) {
	
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
