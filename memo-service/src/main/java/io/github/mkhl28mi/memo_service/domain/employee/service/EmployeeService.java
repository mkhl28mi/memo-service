package io.github.mkhl28mi.memo_service.domain.employee.service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.mkhl28mi.memo_service.domain.employee.dto.request.EmployeeRequest;
import io.github.mkhl28mi.memo_service.domain.employee.dto.response.EmployeeResponse;
import io.github.mkhl28mi.memo_service.domain.employee.entity.Employee;
import io.github.mkhl28mi.memo_service.domain.employee.repository.EmployeeRepository;
import io.github.mkhl28mi.memo_service.domain.employee_position.service.EmployeePositionService;
import io.github.mkhl28mi.memo_service.exception.ResourceNotFoundException;

@Service
@Transactional(readOnly = true)
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private EmployeePositionService employeePositionService;
	
	public List<EmployeeResponse> getEmployees(String search) {
    	if (search == null) {
    		return mapToEmployeeResponse(employeeRepository.findAll()); 
    	} else {
    		return mapToEmployeeResponse(employeeRepository.searchByNameOrTargetName(search.trim()));
    	}
	}
	
	public Employee getEmployeeById(UUID id) {
		return employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
	}
	
	public EmployeeResponse getEmployeeResponseById(UUID id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
		return new EmployeeResponse(employee);
	}
	
	@Transactional
	public EmployeeResponse saveEmployee(EmployeeRequest employeeRequest) {
		Employee employee = new Employee(employeeRequest.fullName(), employeeRequest.targetFullName());
		employeeRequest.employeePositionIds().forEach(id -> employee.addEmployeePosition(employeePositionService.getEmployeePositionById(id)));
		return new EmployeeResponse(employeeRepository.save(employee));
	}
	
	@Transactional
	public EmployeeResponse updateEmployee(UUID employeeId, EmployeeRequest employeeRequest) {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
		employee.setFullName(employeeRequest.fullName());
		employee.setTargetFullName(employeeRequest.targetFullName());
		employee.initializeEmployeePositions();
		
		new HashSet<>(employee.getEmployeePositions()).forEach(employee::removeEmployeePosition);
		
		employeeRequest.employeePositionIds().forEach(id -> employee.addEmployeePosition(employeePositionService.getEmployeePositionById(id)));
		
		return new EmployeeResponse(employeeRepository.save(employee));
	}
	
	@Transactional
	public void deleteEmployee(UUID id) {
		employeeRepository.deleteById(id);
	}
	
    private static List<EmployeeResponse> mapToEmployeeResponse(List<Employee> employees) {
    	return employees.stream().map(EmployeeResponse::new).toList();
    }

}
