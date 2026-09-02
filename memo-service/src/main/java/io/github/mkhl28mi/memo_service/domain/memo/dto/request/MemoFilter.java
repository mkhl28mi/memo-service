
package io.github.mkhl28mi.memo_service.domain.memo.dto.request;

import java.util.UUID;

public record MemoFilter(UUID recipientId, UUID assigneeId, Integer year, Integer month, String keyword, String label, Integer memoNumber) {

}
