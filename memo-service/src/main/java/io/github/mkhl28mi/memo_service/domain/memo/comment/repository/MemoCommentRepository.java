package io.github.mkhl28mi.memo_service.domain.memo.comment.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.mkhl28mi.memo_service.domain.memo.comment.entity.MemoComment;

public interface MemoCommentRepository extends JpaRepository<MemoComment, UUID> {
	
	@Query("SELECT mc FROM MemoComment mc WHERE mc.memo.id = :memoId ORDER BY mc.createdAt DESC")
	public List<MemoComment> findAllByMemoId(@Param("memoId") UUID memoId);
	
}
