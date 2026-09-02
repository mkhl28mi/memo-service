package io.github.mkhl28mi.memo_service.domain.memo.label.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.mkhl28mi.memo_service.domain.memo.label.entity.MemoLabel;

public interface MemoLabelRepository extends JpaRepository<MemoLabel, UUID> {
	
	@Query("SELECT DISTINCT ml.name FROM MemoLabel ml WHERE LOWER(ml.name) LIKE LOWER(CONCAT('%', :keyword, '%')) ")
	List<String> searchDistinctByName(@Param("keyword") String keyword);

}
