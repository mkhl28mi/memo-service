package io.github.mkhl28mi.memo_service.domain.admin.user.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.mkhl28mi.memo_service.domain.admin.department.entity.Department;
import io.github.mkhl28mi.memo_service.domain.admin.user.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {
	
	public Optional<User> findByUsername(String username);
	
	@Query("SELECT u FROM User u WHERE LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) "
			+ "OR LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) ")
	public List<User> searchByFullnameOrUsername(@Param("keyword") String keyword);
	
	@Query("SELECT u FROM User u JOIN u.userAssignments ua WHERE LOWER(u.user.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) "
			+ "AND u.enabled = true "
			+ "AND ua.departmentUnit.enabled = true "
			+ "AND ua.departmentUnit.department.enabled = true "
			+ "AND ua.departmentUnit.department = :department "
			+ "AND ua.endDate IS NULL ")
	public List<User> searchEnabledByFullnameAndDepartment(@Param("keyword") String keyword, @Param("department") Department department);

}
