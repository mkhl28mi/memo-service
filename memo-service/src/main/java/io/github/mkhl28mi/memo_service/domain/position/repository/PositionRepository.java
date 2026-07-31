package io.github.mkhl28mi.memo_service.domain.position.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.mkhl28mi.memo_service.domain.position.entity.Position;

public interface PositionRepository extends JpaRepository<Position, UUID> {
	
	public Optional<Position> findByName(String name);
	
	@Query("SELECT p FROM Position p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) "
			+ "OR LOWER(p.targetName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	public List<Position> searchByNameOrTargetName(@Param("keyword") String keyword);
	
	@Query("SELECT p FROM Position p WHERE (LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) "
			+ "OR LOWER(p.targetName) LIKE LOWER(CONCAT('%', :keyword, '%'))) "
			+ "AND p.enabled = true")
	public List<Position> searchEnabledByNameOrTargetName(@Param("keyword") String keyword);
	
}
