package io.github.mkhl28mi.memo_service.domain.department_unit.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.mkhl28mi.memo_service.domain.department_unit.entity.DepartmentUnit;

public interface DepartmentUnitRepository extends JpaRepository<DepartmentUnit, UUID> {
	
	public Optional<DepartmentUnit> findByCode(String code);
	
	public List<DepartmentUnit> findByDepartmentId(UUID departmentId);
	
	@Query("SELECT du FROM DepartmentUnit du WHERE (LOWER(du.code) LIKE LOWER(CONCAT('%', :keyword, '%')) "
			+ "OR LOWER(du.department.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) "
			+ "AND du.enabled = true "
			+ "AND du.department.enabled = true")
	public List<DepartmentUnit> searchEnabledByDepartmentUnitCodeOrDepartmentName(@Param("keyword") String keyword);
	
}
