package io.github.mkhl28mi.memo_service.domain.memo.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.mkhl28mi.memo_service.domain.admin.department.entity.Department;
import io.github.mkhl28mi.memo_service.domain.memo.entity.Memo;

public interface MemoRepository extends JpaRepository<Memo, UUID>, JpaSpecificationExecutor<Memo> {
	
	@Query("SELECT MAX(m.sequenceNumber) FROM Memo m WHERE m.creationYear = :year AND m.department = :department")
    Optional<Integer> searchMaxSequenceNumber(@Param("year") int year, @Param("department") Department department);
	
	@Query("SELECT COUNT(m) FROM Memo m WHERE m.department.id = :departmentId")
	long counByDepartmentId(@Param("departmentId") UUID departemntId);
	
    @Query("SELECT COUNT(m) FROM Memo m WHERE m.department.id = :departmentId AND (m.createdAt >= :start AND m.createdAt <= :end)")
    long countByDepartmentIdAndDateRange(@Param("departmentId") UUID departemntId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
		
}
