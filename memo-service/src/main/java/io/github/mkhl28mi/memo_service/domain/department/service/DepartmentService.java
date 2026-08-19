package io.github.mkhl28mi.memo_service.domain.department.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import io.github.mkhl28mi.memo_service.domain.department.dto.request.DepartmentRequest;
import io.github.mkhl28mi.memo_service.domain.department.dto.response.DepartmentResponse;
import io.github.mkhl28mi.memo_service.domain.department.entity.Department;
import io.github.mkhl28mi.memo_service.domain.department.repository.DepartmentRepository;
import io.github.mkhl28mi.memo_service.domain.position.entity.Position;
import io.github.mkhl28mi.memo_service.domain.position.service.PositionService;
import io.github.mkhl28mi.memo_service.exception.ResourceNotFoundException;

@Service
@Transactional(readOnly = true)
public class DepartmentService {
	
	private final DepartmentRepository departmentRepository;
	
	private final PositionService positionService;
	
	public DepartmentService(DepartmentRepository departmentRepository, PositionService positionService) {
		this.departmentRepository = departmentRepository;
		this.positionService = positionService;
		
	}
	
    public List<DepartmentResponse> getDepartments(String search) {
    	if (search == null || search.trim().isEmpty()) {
    		return mapToDepartmentResponse(departmentRepository.findAll()); 
    	} else {
    		return mapToDepartmentResponse(departmentRepository.findByNameContainingIgnoreCase(search.trim()));
    	}
    }
    
    public Department getDepartmentById(UUID id) {
    	return departmentRepository.findById(id)
        		.orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + id));
    }
    
    public Optional<Department> getDepartmentByName(String name) {
    	return departmentRepository.findByName(name);
    }
    
    public DepartmentResponse getDepartmentResponseById(UUID id) {
    	Department department = departmentRepository.findById(id)
        		.orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + id));
    	
        return new DepartmentResponse(department);
    }
    
    @Transactional
    public DepartmentResponse addDepartment(DepartmentRequest departmentRequest) {
    	Position position = positionService.getPositionById(departmentRequest.positionId());
    	
		Assert.state(position.isEnabled(), "Position must be enabled");

        return new DepartmentResponse(departmentRepository.save(new Department(departmentRequest.name(),
        		departmentRequest.code(),
        		departmentRequest.description(),
        		position,
        		departmentRequest.enabled())));
    }
    
    @Transactional
    public DepartmentResponse updateDepartment(UUID id, DepartmentRequest departmentRequest) {
    	Position position = positionService.getPositionById(departmentRequest.positionId());
    	
    	Assert.state(position.isEnabled(), "Position must be enabled");
    	
        Department department = departmentRepository.findById(id)
        		.orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + id));
        
        department.setName(departmentRequest.name());
        department.setCode(departmentRequest.code());
        department.setDescription(departmentRequest.description());
        department.setPosition(position);
        department.setEnabled(departmentRequest.enabled());
        
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
