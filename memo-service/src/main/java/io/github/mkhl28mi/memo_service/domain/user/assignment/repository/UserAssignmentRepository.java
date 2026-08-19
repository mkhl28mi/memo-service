package io.github.mkhl28mi.memo_service.domain.user.assignment.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.mkhl28mi.memo_service.domain.user.assignment.entity.UserAssignment;

public interface UserAssignmentRepository extends JpaRepository<UserAssignment, UUID> {
	
	public Optional<UserAssignment> findTopByOrderByCreatedAtDesc();
	
	@Query("SELECT ua FROM UserAssignment ua WHERE ua.user.id = :userId")
	public List<UserAssignment> searchByUserId(@Param("userId") UUID userId);
	
//	@Query("SELECT u FROM User u WHERE LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) "
//			+ "AND u.enabled = true"
//			+ "AND u.departmentUnit.enabled = true "
//			+ "AND u.departmentUnit.department.enabled = true "
//			+ "AND u.departmentUnit.department = :department")
//	public List<User> searchEnabledByFullnameAndDepartment(@Param("keyword") String keyword, @Param("department") Department department);
//	

}
