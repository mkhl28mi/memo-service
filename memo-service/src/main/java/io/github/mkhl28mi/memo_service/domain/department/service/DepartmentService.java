package io.github.mkhl28mi.memo_service.domain.department.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.mkhl28mi.memo_service.domain.department.dto.request.DepartmentRequest;
import io.github.mkhl28mi.memo_service.domain.department.dto.request.DepartmentManagerRequest;
import io.github.mkhl28mi.memo_service.domain.department.dto.response.DepartmentResponse;
import io.github.mkhl28mi.memo_service.domain.department.dto.response.DepartmentManagerResponse;
import io.github.mkhl28mi.memo_service.domain.department.entity.Department;
import io.github.mkhl28mi.memo_service.domain.department.repository.DepartmentRepository;
import io.github.mkhl28mi.memo_service.domain.employee.entity.Employee;
import io.github.mkhl28mi.memo_service.domain.employee.service.EmployeeService;
import io.github.mkhl28mi.memo_service.exception.ResourceNotFoundException;

@Service
@Transactional(readOnly = true)
public class DepartmentService {
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private EmployeeService employeeService;
	
    public List<DepartmentResponse> getDepartments(String search) {
    	if (search == null || search.trim().isEmpty()) {
    		return mapToDepartmentResponse(departmentRepository.findAll()); 
    	} else {
    		return mapToDepartmentResponse(departmentRepository.findByNameContainingIgnoreCase(search.trim()));
    	}
    }
    
    public List<DepartmentManagerResponse> getDepartmentManagers() {
    	List<Department> departments = departmentRepository.findAll();
    	departments.forEach(Department::initializeEmployees);
    	List<DepartmentManagerResponse> departmentSignerResponses = new ArrayList<>();
    	for (Department department : departments) {
    		for (Employee employee : department.getEmployees()) {
    			departmentSignerResponses.add(new DepartmentManagerResponse(employee.getId(),
    					department.getId(),
    					employee.getFullName(), 
    					employee.getEmployeePosition().getName(), 
    					department.getName()));
    		}
    	}
    	return departmentSignerResponses;
    }
    
    public Department getDepartmentById(UUID id) {
        return departmentRepository.findById(id)
        		.orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
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
    public void addDepartmentManager(DepartmentManagerRequest departmentSignerRequest) {
    	Employee employee = employeeService.getEmployeeById(departmentSignerRequest.employeeId());
    	Department department = departmentRepository.findById(departmentSignerRequest.departmentId())
        		.orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentSignerRequest.departmentId()));
    	department.initializeEmployees();
    	department.addEmployee(employee);
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
    
    @Transactional
    public void deleteDepartmentManager(UUID departmentId, UUID employeeId) {
    	Employee employee = employeeService.getEmployeeById(employeeId);
    	Department department = departmentRepository.findById(departmentId)
        		.orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId));
    	department.initializeEmployees();
    	department.removeEmployee(employee);
    }
    
    private static List<DepartmentResponse> mapToDepartmentResponse(List<Department> departments) {
    	return departments.stream().map(DepartmentResponse::new).toList();
    }
    
}
