package io.github.mkhl28mi.memo_service.domain.admin.user.assignment.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.mkhl28mi.memo_service.domain.admin.department.entity.Department;
import io.github.mkhl28mi.memo_service.domain.admin.user.assignment.entity.UserAssignment;

public interface UserAssignmentRepository extends JpaRepository<UserAssignment, UUID> {
	
	@Query("SELECT ua FROM UserAssignment ua WHERE ua.user.id = :userId AND ua.endDate IS NULL")
	public Optional<UserAssignment> findCurrentByUserId(@Param("userId") UUID userId);
	
	@Query("SELECT ua FROM UserAssignment ua WHERE ua.user.id = :userId")
	public List<UserAssignment> searchByUserId(@Param("userId") UUID userId);
	
	@Query("SELECT ua FROM UserAssignment ua WHERE LOWER(ua.user.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) "
			+ "AND ua.user.enabled = true "
			+ "AND ua.departmentUnit.enabled = true "
			+ "AND ua.departmentUnit.department.enabled = true "
			+ "AND ua.departmentUnit.department = :department "
			+ "AND ua.endDate IS NULL ")
	public List<UserAssignment> searchEnabledByFullnameAndDepartment(@Param("keyword") String keyword, @Param("department") Department department);
	
}
