package io.github.mkhl28mi.memo_service.domain.user.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.mkhl28mi.memo_service.domain.department.entity.Department;
import io.github.mkhl28mi.memo_service.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {
	
	public Optional<User> findByUsername(String username);
	
	@Query("SELECT u FROM User u WHERE u.id = :id "
			+ "AND u.enabled = true"
			+ "AND u.departmentUnit.enabled = true "
			+ "AND u.departmentUnit.department.enabled = true ")
	public Optional<User> findEnabledById(@Param("id") UUID id);
	
	@Query("SELECT u FROM User u WHERE LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) "
			+ "OR LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) ")
	public List<User> searchByFullnameOrUsername(@Param("keyword") String keyword);
	
	@Query("SELECT u FROM User u WHERE LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) "
			+ "AND u.enabled = true"
			+ "AND u.departmentUnit.enabled = true "
			+ "AND u.departmentUnit.department.enabled = true "
			+ "AND u.departmentUnit.department = :department")
	public List<User> searchEnabledByFullnameAndDepartment(@Param("keyword") String keyword, @Param("department") Department department);
	
}
