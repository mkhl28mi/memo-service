package io.github.mkhl28mi.memo_service.domain.department_unit.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.mkhl28mi.memo_service.domain.department.service.DepartmentService;
import io.github.mkhl28mi.memo_service.domain.department_unit.dto.response.DepartmentUnitResponse;
import io.github.mkhl28mi.memo_service.domain.department_unit.entity.DepartmentUnit;
import io.github.mkhl28mi.memo_service.domain.department_unit.repository.DepartmentUnitRepository;
import io.github.mkhl28mi.memo_service.exception.ResourceNotFoundException;

@Service
@Transactional(readOnly = true)
public class DepartmentUnitService {
	
	@Autowired
	private DepartmentUnitRepository departmentUnitRepository;
	
	@Autowired
	private DepartmentService departmentService;
	
	public List<DepartmentUnitResponse> getDepartmentUnitsByDepartmentId(UUID departmentId) {
		return mapToDepartmentUnitResponse(departmentUnitRepository.findByDepartmentId(departmentId));
	}
	
	public List<DepartmentUnitResponse> getEnabledDepartmentUnits(String search) {
		if (search == null) { throw new IllegalArgumentException("search cannot be null"); }
		return mapToDepartmentUnitResponse(departmentUnitRepository.searchEnabledByDepartmentUnitCodeOrDepartmentName(search.trim()));
	}
	
    public DepartmentUnit getDepartmentUnitById(UUID id) {
    	return departmentUnitRepository.findById(id)
    			.orElseThrow(() -> new ResourceNotFoundException("Department unit not found with id: " + id));
    }
    
    public Optional<DepartmentUnit> getDepartmentUnitByCode(String code) {
    	return departmentUnitRepository.findByCode(code);
    }
	
    public DepartmentUnitResponse getDepartmentUnitResponseById(UUID id) {
    	DepartmentUnit departmentUnit = departmentUnitRepository.findById(id)
    			.orElseThrow(() -> new ResourceNotFoundException("Department unit not found with id: " + id));
    	return new DepartmentUnitResponse(departmentUnit);
    }
	
	@Transactional
	public DepartmentUnitResponse addDepartmentUnit(UUID departmentId, String code, boolean isEnabled) {
		return new DepartmentUnitResponse(departmentUnitRepository.save(new DepartmentUnit(code, departmentService.getDepartmentById(departmentId), isEnabled)));
	}
	
	@Transactional
	public DepartmentUnitResponse updateDepartmentUnit(UUID id, String code, boolean isEnabled) {
		DepartmentUnit departmentUnit = departmentUnitRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Departnet unit not found with id: " + id));
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
