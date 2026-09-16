package io.github.mkhl28mi.memo_service.domain.memo.comment.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import io.github.mkhl28mi.memo_service.domain.admin.user.assignment.entity.UserAssignment;
import io.github.mkhl28mi.memo_service.domain.admin.user.assignment.service.UserAssignmentService;
import io.github.mkhl28mi.memo_service.domain.memo.comment.dto.response.MemoCommentResponse;
import io.github.mkhl28mi.memo_service.domain.memo.comment.entity.MemoComment;
import io.github.mkhl28mi.memo_service.domain.memo.comment.repository.MemoCommentRepository;
import io.github.mkhl28mi.memo_service.domain.memo.entity.Memo;
import io.github.mkhl28mi.memo_service.domain.memo.entity.Memo.Status;
import io.github.mkhl28mi.memo_service.domain.memo.service.MemoService;


@Service
@Transactional(readOnly = true)
public class MemoCommentService {
	
	private final MemoCommentRepository memoCommentRepository;
	
	private final UserAssignmentService userAssignmentService;
	
	private final MemoService memoService;
	
	public MemoCommentService(MemoCommentRepository memoCommentRepository, UserAssignmentService userAssignmentService, MemoService memoService) {
		super();
		this.memoCommentRepository = memoCommentRepository;
		this.userAssignmentService = userAssignmentService;
		this.memoService = memoService;
	}
	
	public List<MemoCommentResponse> getMemoCommentsByMemoId(UUID memoId) {
		return memoCommentRepository.findAllByMemoId(memoId).stream()
				.map(MemoCommentResponse::new)
				.toList();
	}
	
	@Transactional
	public void addComment(UUID memoId, UUID userId, String content) {
		Memo memo = memoService.getMemoById(memoId);
		
		Assert.state((memo.getStatus() == Status.ON_APPROVAL), () -> "Memo cannot be updated" + " for user ID: " + userId);

		memo.setStatus(Status.REVISE_MEMO);
		
		UserAssignment currentUserAssignment = userAssignmentService.getCurrentUserAssignmentByUserId(userId);
				
		memoCommentRepository.save(new MemoComment(memo, currentUserAssignment, content));
		
		memoService.updateMemo(memo);
	}
	
}
