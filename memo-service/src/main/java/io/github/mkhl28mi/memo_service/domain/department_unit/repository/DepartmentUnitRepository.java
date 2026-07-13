package io.github.mkhl28mi.memo_service.domain.department_unit.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.mkhl28mi.memo_service.domain.department_unit.entity.DepartmentUnit;

public interface DepartmentUnitRepository extends JpaRepository<DepartmentUnit, UUID> {
	
	public List<DepartmentUnit> getDepartntUnitsByDepartmentId(UUID departmentId);

}
