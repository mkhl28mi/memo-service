package io.github.mkhl28mi.memo_service.domain.department.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.mkhl28mi.memo_service.domain.department.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {

}
