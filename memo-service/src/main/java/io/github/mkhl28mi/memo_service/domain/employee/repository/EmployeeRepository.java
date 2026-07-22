package io.github.mkhl28mi.memo_service.domain.employee.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.mkhl28mi.memo_service.domain.employee.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
	
	@Query("SELECT e FROM Employee e WHERE LOWER(e.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) "
			+ "OR LOWER(e.targetFullName) LIKE LOWER(CONCAT('%', :keyword, '%')) ")
	List<Employee> searchByNameOrTargetName(@Param("keyword") String keyword);
	
}
