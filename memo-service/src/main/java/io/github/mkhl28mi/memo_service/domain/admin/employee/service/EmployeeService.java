package io.github.mkhl28mi.memo_service.domain.admin.employee.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import io.github.mkhl28mi.memo_service.domain.admin.employee.dto.request.EmployeeRequest;
import io.github.mkhl28mi.memo_service.domain.admin.employee.dto.response.EmployeeResponse;
import io.github.mkhl28mi.memo_service.domain.admin.employee.dto.xml_parsing.EmployeeDto;
import io.github.mkhl28mi.memo_service.domain.admin.employee.dto.xml_parsing.EmployeeListDto;
import io.github.mkhl28mi.memo_service.domain.admin.employee.entity.Employee;
import io.github.mkhl28mi.memo_service.domain.admin.employee.repository.EmployeeRepository;
import io.github.mkhl28mi.memo_service.exception.ResourceNotFoundException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

@Service
@Transactional(readOnly = true)
public class EmployeeService {
	
	private final EmployeeRepository employeeRepository;
		
	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
	public EmployeeResponse getEmployeeResponseById(UUID id) throws ResourceNotFoundException {
		return new EmployeeResponse(employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id)));
	}
	
	public Employee getEmployeeById(UUID id) throws ResourceNotFoundException {
		return employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
	}
	
	public Optional<Employee> getEmployeeByFullName(String fullName) throws ResourceNotFoundException {
		return employeeRepository.findByFullName(fullName);
	}
	
	public List<EmployeeResponse> getEmployees(String search) {
    	if (search == null) {
    		return mapToEmployeeResponse(employeeRepository.findAll()); 
    	} else {
    		return mapToEmployeeResponse(employeeRepository.searchByName(search.trim()));
    	}
	}
	
	@Transactional
	public EmployeeResponse addEmployee(EmployeeRequest employeeRequest) {
		return new EmployeeResponse(employeeRepository.save(new Employee(employeeRequest.fullName(), 
				employeeRequest.targetFullName(), 
				employeeRequest.enabled())));
	}
	
	@Transactional
	public EmployeeResponse updateEmployee(UUID employeeId, EmployeeRequest employeeRequest) {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));
		
		employee.setFullName(employeeRequest.fullName());
		employee.setTargetFullName(employeeRequest.targetFullName());
		employee.setEnabled(employeeRequest.enabled());
		
		return new EmployeeResponse(employeeRepository.save(employee));
	}
	
	@Transactional
	public void deleteEmployee(UUID id) {
		employeeRepository.deleteById(id);
	}
	
	@Transactional
	public void parseAndSaveEmployees(MultipartFile file) throws JAXBException, IOException {
		JAXBContext context = JAXBContext.newInstance(EmployeeListDto.class);
		
		Unmarshaller unmarshaller = context.createUnmarshaller();
		
		EmployeeListDto employeeListDto = (EmployeeListDto) unmarshaller.unmarshal(file.getInputStream());
		
		List<Employee> employees = new HashSet<EmployeeDto>(employeeListDto.getEmployees()).stream()
				.map(e -> new Employee(e.getFullName(), e.getTargetFullName(), true))
				.toList();
		
		employeeRepository.saveAll(employees);
	}
	
    private static List<EmployeeResponse> mapToEmployeeResponse(List<Employee> employees) {
    	return employees.stream().map(EmployeeResponse::new).toList();
    }
    
}
