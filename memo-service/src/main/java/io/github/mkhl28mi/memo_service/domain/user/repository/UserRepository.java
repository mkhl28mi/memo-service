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
	
	Optional<User> findByUsername(String username);
	
	@Query("SELECT u FROM User u WHERE LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) "
			+ "OR LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) ")
	public List<User> searchUsers(@Param("keyword") String keyword);
	
	@Query("SELECT u FROM User u WHERE LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) "
			+ "AND u.departmentUnit.department = :department "
			+ "AND u.departmentUnit.department.enabled = true "
			+ "AND u.enabled = true")
	public List<User> searchEnabledUsers(@Param("keyword") String keyword, @Param("department") Department department);
	
}
