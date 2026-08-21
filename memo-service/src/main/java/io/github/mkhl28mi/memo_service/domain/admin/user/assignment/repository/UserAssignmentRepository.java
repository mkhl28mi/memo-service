package io.github.mkhl28mi.memo_service.domain.admin.user.assignment.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.mkhl28mi.memo_service.domain.admin.user.assignment.entity.UserAssignment;

public interface UserAssignmentRepository extends JpaRepository<UserAssignment, UUID> {
	
	@Query("SELECT ua FROM UserAssignment ua WHERE ua.user.id = :userId AND up.endDate IS NULL")
	public Optional<UserAssignment> findCurrentByUserId(@Param("userId") UUID userId);
	
	@Query("SELECT ua FROM UserAssignment ua WHERE ua.user.id = :userId")
	public List<UserAssignment> searchByUserId(@Param("userId") UUID userId);
	
}
