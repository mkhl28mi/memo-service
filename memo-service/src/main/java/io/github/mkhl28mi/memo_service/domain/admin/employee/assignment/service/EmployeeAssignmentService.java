package io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.dto.response.EmployeeAssignmentResponse;
import io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.entity.EmployeeAssignment;
import io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.repository.EmployeeAssignmentRepository;
import io.github.mkhl28mi.memo_service.domain.admin.employee.entity.Employee;
import io.github.mkhl28mi.memo_service.domain.admin.employee.service.EmployeeService;
import io.github.mkhl28mi.memo_service.domain.admin.position.entity.Position;
import io.github.mkhl28mi.memo_service.domain.admin.position.service.PositionService;
import io.github.mkhl28mi.memo_service.exception.ResourceNotFoundException;


@Service
@Transactional(readOnly = true)
public class EmployeeAssignmentService {
	
	private final EmployeeService employeeService;
	
	private final PositionService positionService;
	
	private final EmployeeAssignmentRepository employeeAssignmentRepository;
	
	public EmployeeAssignmentService(EmployeeService employeeService, PositionService positionService, EmployeeAssignmentRepository employeeAssignmentRepository) {
		super();
		this.employeeService = employeeService;
		this.positionService = positionService;
		this.employeeAssignmentRepository = employeeAssignmentRepository;
	}
	
	public EmployeeAssignmentResponse getEmployeeAssignmentResponseById(UUID id) {
		return new EmployeeAssignmentResponse(employeeAssignmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("EmployeeAssignment not found with ID: " + id)));
	}
	
	public List<EmployeeAssignment> getEmployeeAssignmentsByIds(List<UUID> ids) {
		return employeeAssignmentRepository.findAllById(ids);
	}

	public List<EmployeeAssignmentResponse> getEmployeeAssignments(UUID employeeId) {
		return employeeAssignmentRepository.finadAllByEmployeeId(employeeId).stream()
				.map(EmployeeAssignmentResponse::new)
				.toList();
	}
	
	public List<EmployeeAssignmentResponse> getEnabledEmployeeAssignments(String query) {
		return employeeAssignmentRepository.searchEnabledByEmployeeOrPosition(query).stream()
		.map(EmployeeAssignmentResponse::new)
		.toList();
	}
	
	@Transactional
	public EmployeeAssignmentResponse addEmployeeAssignment(UUID positionId, UUID employeeId) {
		Position position = positionService.getPositionById(positionId);
		
		Assert.state(position.isEnabled(), "Position must be enabled");
		
		Employee employee = employeeService.getEmployeeById(employeeId);
		
		Assert.state(employee.isEnabled(), "Employee must be enabled");
		
		return new EmployeeAssignmentResponse(employeeAssignmentRepository.save(new EmployeeAssignment(position, employee, LocalDateTime.now(), null)));
	}
	
	@Transactional
	public void updateEmployeeAssignmentEndDate(UUID id) {
		EmployeeAssignment employeeAssignment = employeeAssignmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("EmployeeAssignment not found with ID: " + id));
		
		Assert.state(employeeAssignment.getEndDate().isEmpty(), "EmployeeAssignment`s endDate must be null");
		
		employeeAssignment.setEndDate(LocalDateTime.now());
		
		employeeAssignmentRepository.save(employeeAssignment);
	}
	
}
