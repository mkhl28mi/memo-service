package io.github.mkhl28mi.memo_service.domain.employee_position.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.mkhl28mi.memo_service.domain.employee_position.dto.request.EmployeePositionRequest;
import io.github.mkhl28mi.memo_service.domain.employee_position.dto.response.EmployeePositionResponse;
import io.github.mkhl28mi.memo_service.domain.employee_position.entity.EmployeePosition;
import io.github.mkhl28mi.memo_service.domain.employee_position.repository.EmployeePositionsRepository;
import io.github.mkhl28mi.memo_service.exception.ResourceNotFoundException;

@Service
@Transactional(readOnly = true)
public class EmployeePositionService {
	
	@Autowired
	private EmployeePositionsRepository employeePositionsRepository;
	
	public List<EmployeePositionResponse> getEmployeePostions(String search) {
    	if (search == null || search.trim().isEmpty()) {
    		return mapToEmployeePositionResponse(employeePositionsRepository.findAll()); 
    	} else {
    		return mapToEmployeePositionResponse(employeePositionsRepository.findByNameContainingOrTargetNameContaining(search.trim(), search.trim()));
    	}
	}
	
	public EmployeePositionResponse getEmployeePositionById(UUID id) {
		EmployeePosition employeePosition = employeePositionsRepository.findById(id)
        		.orElseThrow(() -> new ResourceNotFoundException("Employee position not found with id: " + id));
		return new EmployeePositionResponse(employeePosition);
	}
	
	@Transactional
	public EmployeePositionResponse saveEmployeePosition(EmployeePositionRequest employeePositionRequest) {
		return new EmployeePositionResponse(employeePositionsRepository.save(new EmployeePosition(employeePositionRequest.name(), 
				employeePositionRequest.targetName(), 
				employeePositionRequest.placementOrder())));
	}
	
	@Transactional
	public EmployeePositionResponse updateEmployeePosition(UUID id, EmployeePositionRequest employeePositionRequest) {
		EmployeePosition employeePosition = employeePositionsRepository.findById(id)
        		.orElseThrow(() -> new ResourceNotFoundException("Employee position not found with id: " + id));
		employeePosition.setName(employeePositionRequest.name());
		employeePosition.setTargetName(employeePositionRequest.targetName());
		employeePosition.setPlacementOrder(employeePositionRequest.placementOrder());
		return new EmployeePositionResponse(employeePositionsRepository.save(employeePosition));
	}
	
	@Transactional
	public void deleteEmployeePosition(UUID id) {
		employeePositionsRepository.deleteById(id);
	}
	
    private static List<EmployeePositionResponse> mapToEmployeePositionResponse(List<EmployeePosition> employeePositions) {
    	return employeePositions.stream().map(EmployeePositionResponse::new).toList();
    }

}
