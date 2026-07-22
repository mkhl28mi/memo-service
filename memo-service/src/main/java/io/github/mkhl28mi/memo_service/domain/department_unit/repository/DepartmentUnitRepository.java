package io.github.mkhl28mi.memo_service.domain.department_unit.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.mkhl28mi.memo_service.domain.department_unit.entity.DepartmentUnit;

public interface DepartmentUnitRepository extends JpaRepository<DepartmentUnit, UUID> {
	
	public List<DepartmentUnit> findDepartntUnitsByDepartmentId(UUID departmentId);
	
	@Query("SELECT du FROM DepartmentUnit du WHERE LOWER(du.department.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	public List<DepartmentUnit> searchByDepartmentName(@Param("keyword") String keyword);
	
}
