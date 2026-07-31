package io.github.mkhl28mi.memo_service.domain.memo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.mkhl28mi.memo_service.domain.memo.entity.Memo;

public interface MemoRepository extends JpaRepository<Memo, UUID> {
	
}
