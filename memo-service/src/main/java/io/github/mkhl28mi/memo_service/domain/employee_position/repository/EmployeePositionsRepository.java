package io.github.mkhl28mi.memo_service.domain.employee_position.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.mkhl28mi.memo_service.domain.employee_position.entity.EmployeePosition;

public interface EmployeePositionsRepository extends JpaRepository<EmployeePosition, UUID> {
	
	List<EmployeePosition> findByNameContainingOrTargetNameContaining(String name, String targetName);
	
}
