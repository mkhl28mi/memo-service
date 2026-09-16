package io.github.mkhl28mi.memo_service.domain.memo.log.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.mkhl28mi.memo_service.domain.memo.log.entity.MemoLog;

public interface MemoLogRepository extends JpaRepository<MemoLog, UUID> {
	
	@Query("SELECT ml FROM MemoLog ml WHERE ml.memo.id = :memoId ORDER BY ml.createdAt DESC")
	public List<MemoLog> findAllByMemoId(@Param("memoId") UUID memoId);

}
