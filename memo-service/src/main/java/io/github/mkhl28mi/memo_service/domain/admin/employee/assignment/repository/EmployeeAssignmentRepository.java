package io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.entity.EmployeeAssignment;

public interface EmployeeAssignmentRepository extends JpaRepository<EmployeeAssignment, UUID> {
	
	@Query("SELECT ea FROM EmployeeAssignment ea WHERE ea.employee.id = :employeeId AND ea.endDate IS NULL")
	public List<EmployeeAssignment> findAllCurrentByEmployeeId(@Param("employeeId") UUID employeeId);
	
	@Query("SELECT ea FROM EmployeeAssignment ea WHERE ea.employee.id = :employeeId")
	public List<EmployeeAssignment> finadAllByEmployeeId(@Param("employeeId") UUID employeeId);
	
	@Query("SELECT ea FROM EmployeeAssignment ea WHERE (LOWER(ea.employee.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) "
			+ "OR LOWER(ea.employee.targetFullName) LIKE LOWER(CONCAT('%', :keyword, '%'))) "
			+ "AND ea.employee.enabled = true "
			+ "AND ea.endDate IS NULL ")
	public List<EmployeeAssignment> searchAllEnabledByEmployeeFullname(@Param("keyword") String keyword);
	
}
