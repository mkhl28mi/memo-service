package io.github.mkhl28mi.memo_service.domain.admin.department.unit.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import io.github.mkhl28mi.memo_service.domain.admin.department.entity.Department;
import io.github.mkhl28mi.memo_service.domain.admin.department.service.DepartmentService;
import io.github.mkhl28mi.memo_service.domain.admin.department.unit.dto.response.DepartmentUnitResponse;
import io.github.mkhl28mi.memo_service.domain.admin.department.unit.entity.DepartmentUnit;
import io.github.mkhl28mi.memo_service.domain.admin.department.unit.repository.DepartmentUnitRepository;
import io.github.mkhl28mi.memo_service.exception.ResourceNotFoundException;

@Service
@Transactional(readOnly = true)
public class DepartmentUnitService {
	
	private final DepartmentUnitRepository departmentUnitRepository;
	
	private final DepartmentService departmentService;
	
	public DepartmentUnitService(DepartmentUnitRepository departmentUnitRepository, DepartmentService departmentService) {
		this.departmentUnitRepository = departmentUnitRepository;
		this.departmentService = departmentService;
	}

	public List<DepartmentUnitResponse> getDepartmentUnitsByDepartmentId(UUID departmentId) {
		return mapToDepartmentUnitResponse(departmentUnitRepository.findAllByDepartmentId(departmentId));
	}
	
	public List<DepartmentUnitResponse> getEnabledDepartmentUnits(String search) {		
		return mapToDepartmentUnitResponse(departmentUnitRepository.searchEnabledByDepartmentUnitCodeOrDepartmentName(search.trim()));
	}
	
    public DepartmentUnit getDepartmentUnitById(UUID id) throws ResourceNotFoundException {
    	return departmentUnitRepository.findById(id)
    			.orElseThrow(() -> new ResourceNotFoundException("DepartmentUnit not found with ID: " + id));
    }
    
    public Optional<DepartmentUnit> getDepartmentUnitByCode(String code) {
    	return departmentUnitRepository.findByCode(code);
    }
	
    public DepartmentUnitResponse getDepartmentUnitResponseById(UUID id) throws ResourceNotFoundException {
    	DepartmentUnit departmentUnit = departmentUnitRepository.findById(id)
    			.orElseThrow(() -> new ResourceNotFoundException("DepartmentUnit not found with ID: " + id));
    	
    	return new DepartmentUnitResponse(departmentUnit);
    }
	
	@Transactional
	public DepartmentUnitResponse addDepartmentUnit(UUID departmentId, String code, boolean isEnabled) {
		Department department = departmentService.getDepartmentById(departmentId);
		
		Assert.state(department.isEnabled(), "Department must be enabled");
		
		return new DepartmentUnitResponse(departmentUnitRepository.save(new DepartmentUnit(code, department, isEnabled)));
	}
	
	@Transactional
	public DepartmentUnitResponse updateDepartmentUnit(UUID id, String code, boolean isEnabled) throws ResourceNotFoundException {
		DepartmentUnit departmentUnit = departmentUnitRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("DepartmentUnit not found with ID: " + id));
		
		departmentUnit.setCode(code);
		departmentUnit.setEnabled(isEnabled);
		
		return new DepartmentUnitResponse(departmentUnitRepository.save(departmentUnit));
	}
	
	@Transactional
	public void deleteDepartmentUnit(UUID id) {
		departmentUnitRepository.deleteById(id);
	}
	
	private static List<DepartmentUnitResponse> mapToDepartmentUnitResponse(List<DepartmentUnit> departmentUnits) {
		return departmentUnits.stream().map(DepartmentUnitResponse::new).toList();
	}
}
