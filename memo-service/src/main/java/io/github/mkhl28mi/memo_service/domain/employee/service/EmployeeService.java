package io.github.mkhl28mi.memo_service.domain.employee.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.mkhl28mi.memo_service.domain.employee.dto.request.EmployeeRequest;
import io.github.mkhl28mi.memo_service.domain.employee.dto.response.EmployeeBasicResponse;
import io.github.mkhl28mi.memo_service.domain.employee.dto.response.EmployeeDetailedResponse;
import io.github.mkhl28mi.memo_service.domain.employee.dto.response.EmployeeOptionResponse;
import io.github.mkhl28mi.memo_service.domain.employee.entity.Employee;
import io.github.mkhl28mi.memo_service.domain.employee.repository.EmployeeRepository;
import io.github.mkhl28mi.memo_service.domain.position.entity.Position;
import io.github.mkhl28mi.memo_service.domain.position.service.PositionService;
import io.github.mkhl28mi.memo_service.exception.ResourceNotFoundException;

@Service
@Transactional(readOnly = true)
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private PositionService positionService;
	
	public List<EmployeeBasicResponse> getBasicEmployees(String search) {
    	if (search == null) {
    		return mapToEmployeeBasicResponse(employeeRepository.findAll()); 
    	} else {
    		return mapToEmployeeBasicResponse(employeeRepository.searchByName(search.trim()));
    	}
	}
	
	public List<EmployeeDetailedResponse> getDetailedEmployees(String search) {
    	if (search == null) {
    		return mapToEmployeeDetailedResponse(employeeRepository.searchAllWithPositions()); 
    	} else {
    		return mapToEmployeeDetailedResponse(employeeRepository.searchByNameWithPositions(search.trim()));
    	}
	}
	
	public List<EmployeeOptionResponse> getEnabledEmployeeOptions(String search) {
		if (search == null) { throw new IllegalArgumentException("search cannot be null."); }
		List<EmployeeOptionResponse> list = new ArrayList<>();
		for (Employee employee : employeeRepository.searchEnabledByNameOrPosition(search.trim())) {
			for (Position position : employee.getPositions()) {
				list.add(new EmployeeOptionResponse(employee.getId(), 
						position.getId(), 
						employee.getFullName(), 
						employee.getTargetFullName(), 
						position.getName(), 
						position.getTargetName(), 
						position.getPlacementOrder()));
			}
		}
		return list;
 	}
	
	public Employee getEmployeeById(UUID id) {
		return employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
	}
	
	public EmployeeDetailedResponse getEmployeeDetailedResponseById(UUID id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
		return new EmployeeDetailedResponse(employee);
	}
	
	@Transactional
	public EmployeeDetailedResponse addEmployee(EmployeeRequest employeeRequest) {
		Employee employee = new Employee(employeeRequest.fullName(), employeeRequest.targetFullName(), employeeRequest.enabled());
		employeeRequest.positionIds().forEach(id -> employee.addPosition(positionService.getPositionById(id)));
		return new EmployeeDetailedResponse(employeeRepository.save(employee));
	}
	
	@Transactional
	public EmployeeDetailedResponse updateEmployee(UUID employeeId, EmployeeRequest employeeRequest) {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
		employee.setFullName(employeeRequest.fullName());
		employee.setTargetFullName(employeeRequest.targetFullName());
		employee.setEnabled(employeeRequest.enabled());
		employee.initializePositions();
		new HashSet<>(employee.getPositions()).forEach(employee::removePosition);
		employeeRequest.positionIds().forEach(id -> employee.addPosition(positionService.getPositionById(id)));
		return new EmployeeDetailedResponse(employeeRepository.save(employee));
	}
	
	@Transactional
	public void deleteEmployee(UUID id) {
		employeeRepository.deleteById(id);
	}
	
    private static List<EmployeeBasicResponse> mapToEmployeeBasicResponse(List<Employee> employees) {
    	return employees.stream().map(EmployeeBasicResponse::new).toList();
    }
    
    private static List<EmployeeDetailedResponse> mapToEmployeeDetailedResponse(List<Employee> employees) {
    	return employees.stream().map(EmployeeDetailedResponse::new).toList();
    }

}
