package io.github.mkhl28mi.memo_service.domain.admin.department.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.mkhl28mi.memo_service.domain.admin.department.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {
	
	Optional<Department> findByName(String name);
	
	List<Department> findByNameContainingIgnoreCase(String name);

}
