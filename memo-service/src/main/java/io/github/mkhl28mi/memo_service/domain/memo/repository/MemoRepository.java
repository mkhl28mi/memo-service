package io.github.mkhl28mi.memo_service.domain.memo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.mkhl28mi.memo_service.domain.department.entity.Department;
import io.github.mkhl28mi.memo_service.domain.memo.entity.Memo;

public interface MemoRepository extends JpaRepository<Memo, UUID> {
	
	@Query("SELECT MAX(m.sequenceNumber) FROM Memo m WHERE m.creationYear = :year AND m.department = :department")
    Optional<Integer> searchMaxSequenceNumber(@Param("year") int year, @Param("department") Department department);
	
}
