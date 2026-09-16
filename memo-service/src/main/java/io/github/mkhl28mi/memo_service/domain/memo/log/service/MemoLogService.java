package io.github.mkhl28mi.memo_service.domain.memo.log.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.mkhl28mi.memo_service.domain.memo.log.dto.response.MemoLogResponse;
import io.github.mkhl28mi.memo_service.domain.memo.log.repository.MemoLogRepository;

@Service
@Transactional(readOnly = true)
public class MemoLogService {
	
	private final MemoLogRepository memoLogRepository;
	
	public MemoLogService(MemoLogRepository memoLogRepository) {
		super();
		this.memoLogRepository = memoLogRepository;
	}

	public List<MemoLogResponse> getMemoLogsById(UUID memoId) {
		return memoLogRepository.findAllByMemoId(memoId).stream()
				.map(MemoLogResponse::new)
				.toList();
	}

}
