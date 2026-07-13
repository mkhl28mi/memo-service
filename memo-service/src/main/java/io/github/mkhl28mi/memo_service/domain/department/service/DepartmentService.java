package io.github.mkhl28mi.memo_service.domain.department.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.mkhl28mi.memo_service.domain.department.dto.request.DepartmentRequest;
import io.github.mkhl28mi.memo_service.domain.department.dto.response.DepartmentResponse;
import io.github.mkhl28mi.memo_service.domain.department.entity.Department;
import io.github.mkhl28mi.memo_service.domain.department.repository.DepartmentRepository;
import io.github.mkhl28mi.memo_service.exception.ResourceNotFoundException;

@Service
@Transactional(readOnly = true)
public class DepartmentService {
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
    public List<DepartmentResponse> getDepartments(String search) {
    	if (search == null || search.trim().isEmpty()) {
    		return mapToDepartmentResponse(departmentRepository.findAll()); 
    	} else {
    		return mapToDepartmentResponse(departmentRepository.findByNameContaining(search.trim()));
    	}
    }
    
    public Optional<Department> getDepartmentById(UUID id) {
        return departmentRepository.findById(id);
    }
    
    public DepartmentResponse getDepartmentResponseById(UUID id) {
    	Department department = departmentRepository.findById(id)
        		.orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
        return new DepartmentResponse(department);
    }
    
    @Transactional
    public DepartmentResponse saveDepartment(DepartmentRequest departmentRequest) {
        return new DepartmentResponse(departmentRepository.save(new Department(departmentRequest.name(),
        		departmentRequest.code(),
        		departmentRequest.description())));
    }
    
    @Transactional
    public DepartmentResponse updateDepartment(UUID id, DepartmentRequest departmentRequest) {
        Department department = departmentRepository.findById(id)
        		.orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
        department.setName(departmentRequest.name());
        department.setCode(departmentRequest.code());
        department.setDescription(departmentRequest.description());
        return new DepartmentResponse(departmentRepository.save(department));
    }
    
    @Transactional
    public void deleteDepartment(UUID id) {
    	departmentRepository.deleteById(id);
    }
    
    private static List<DepartmentResponse> mapToDepartmentResponse(List<Department> departments) {
    	return departments.stream().map(DepartmentResponse::new).toList();
    }
    
}
