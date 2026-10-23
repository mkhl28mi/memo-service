package io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import io.github.mkhl28mi.memo_service.domain.admin.department.dto.response.DepartmentResponse;
import io.github.mkhl28mi.memo_service.domain.admin.department.service.DepartmentService;
import io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.dto.request.EmployeeAssignmentRequest;
import io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.dto.response.EmployeeAssignmentResponse;
import io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.entity.EmployeeAssignment;
import io.github.mkhl28mi.memo_service.domain.admin.employee.assignment.repository.EmployeeAssignmentRepository;
import io.github.mkhl28mi.memo_service.domain.admin.employee.dto.xml_parsing.EmployeeDto;
import io.github.mkhl28mi.memo_service.domain.admin.employee.dto.xml_parsing.EmployeeListDto;
import io.github.mkhl28mi.memo_service.domain.admin.employee.dto.xml_parsing.PositionDto;
import io.github.mkhl28mi.memo_service.domain.admin.employee.entity.Employee;
import io.github.mkhl28mi.memo_service.domain.admin.employee.service.EmployeeService;
import io.github.mkhl28mi.memo_service.domain.admin.position.entity.Position;
import io.github.mkhl28mi.memo_service.domain.admin.position.service.PositionService;
import io.github.mkhl28mi.memo_service.exception.ResourceNotFoundException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;


@Service
@Transactional(readOnly = true)
public class EmployeeAssignmentService {
	
	private final EmployeeService employeeService;
	
	private final PositionService positionService;
	
	private final DepartmentService departmentService;
	
	private final EmployeeAssignmentRepository employeeAssignmentRepository;
	
	public EmployeeAssignmentService(EmployeeService employeeService, PositionService positionService, DepartmentService departmentService, EmployeeAssignmentRepository employeeAssignmentRepository) {
		super();
		this.employeeService = employeeService;
		this.positionService = positionService;
		this.departmentService = departmentService;
		this.employeeAssignmentRepository = employeeAssignmentRepository;
	}

	public EmployeeAssignmentResponse getEmployeeAssignmentResponseById(UUID id) {
		return new EmployeeAssignmentResponse(employeeAssignmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("EmployeeAssignment not found with ID: " + id)));
	}
	
	public EmployeeAssignmentResponse getSigner(UUID departmentId) {
	 	DepartmentResponse departmentResponse =  departmentService.getDepartmentResponseById(departmentId);
	 	
	 	return new EmployeeAssignmentResponse(employeeAssignmentRepository.findCurrentByPositionId(departmentResponse.positionResponse().id())
	 			.orElseThrow(() -> new ResourceNotFoundException("Current EmployeeAssignment not found with position ID: " + departmentResponse.positionResponse().id())));
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
	public EmployeeAssignmentResponse addEmployeeAssignment(EmployeeAssignmentRequest employeeAssignmentRequest, UUID employeeId) {
		Position position = positionService.getPositionById(employeeAssignmentRequest.positionId());
		
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
	
	@Transactional
	public void parseAndSaveEmployeeAssignments(MultipartFile file) throws JAXBException, IOException {
		JAXBContext context = JAXBContext.newInstance(EmployeeListDto.class);
		
		Unmarshaller unmarshaller = context.createUnmarshaller();
		
		EmployeeListDto employeeListDto = (EmployeeListDto) unmarshaller.unmarshal(file.getInputStream());
		
		var employeeAssignments = new ArrayList<EmployeeAssignment>();
		
		for (EmployeeDto employeeDto : new HashSet<EmployeeDto>(employeeListDto.getEmployees())) {
			Employee employee = employeeService.getEmployeeByFullName(employeeDto.getFullName())
					.orElseThrow(() -> new ResourceNotFoundException("Employee not found with full name: " + employeeDto.getFullName()));
			
			Assert.state(employee.isEnabled(), "Employee must be enabled");
			
			for (PositionDto positionDto : employeeDto.getPositions()) {
				Position position = positionService.getPositionByName(positionDto.getName())
						.orElseThrow(() -> new ResourceNotFoundException("Position not found with name: " + positionDto.getName()));
				
				Assert.state(position.isEnabled(), "Position must be enabled");
				
				employeeAssignments.add(new EmployeeAssignment(position, employee, LocalDateTime.now(), null));
			}
		}

		employeeAssignmentRepository.saveAll(employeeAssignments);
	}
	
}
