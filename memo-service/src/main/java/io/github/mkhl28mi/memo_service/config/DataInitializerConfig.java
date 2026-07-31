package io.github.mkhl28mi.memo_service.config;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.mkhl28mi.memo_service.domain.department.dto.request.DepartmentRequest;
import io.github.mkhl28mi.memo_service.domain.department.entity.Department;
import io.github.mkhl28mi.memo_service.domain.department.service.DepartmentService;
import io.github.mkhl28mi.memo_service.domain.department_unit.entity.DepartmentUnit;
import io.github.mkhl28mi.memo_service.domain.department_unit.service.DepartmentUnitService;
import io.github.mkhl28mi.memo_service.domain.position.dto.request.PositionRequest;
import io.github.mkhl28mi.memo_service.domain.position.entity.Position;
import io.github.mkhl28mi.memo_service.domain.position.service.PositionService;
import io.github.mkhl28mi.memo_service.domain.role.entity.Role;
import io.github.mkhl28mi.memo_service.domain.role.entity.Role.RoleType;
import io.github.mkhl28mi.memo_service.domain.role.service.RoleService;
import io.github.mkhl28mi.memo_service.domain.user.dto.request.UserRequest;
import io.github.mkhl28mi.memo_service.domain.user.service.UserService;

@Configuration
public class DataInitializerConfig {
	
	private static final String EMPLOYEE_POSITION_NAME = "Test employee position";
	
	private static final String DEPARTMENT_NAME = "Test department";
	
	private static final String DEPARTMENT_UNIT_CODE = "00001";
	
	private static final String USER_USERNAME = "admin";
	
	@Bean
    CommandLineRunner initDatabase(PositionService employeePositionService,
    		DepartmentService departmentService,
    		DepartmentUnitService departmentUnitService,
    		RoleService roleService,
    		UserService userService) {
        return args -> {
        	if (employeePositionService.getPositionByName(EMPLOYEE_POSITION_NAME).isEmpty()) {
        		employeePositionService.addPosition(new PositionRequest(EMPLOYEE_POSITION_NAME, "Test target employee position", 1, true));
        	}
        	
        	Optional<Position> employeePosition = employeePositionService.getPositionByName(EMPLOYEE_POSITION_NAME);
        	
        	if (employeePosition.isPresent() && departmentService.getDepartmentByName(DEPARTMENT_NAME).isEmpty()) {
        		departmentService.addDepartment(new DepartmentRequest(DEPARTMENT_NAME, "01", "Test department description",  employeePosition.get().getId(), true));
        	}
        	
        	Optional<Department> department = departmentService.getDepartmentByName(DEPARTMENT_NAME);
        	
        	if (department.isPresent() && departmentUnitService.getDepartmentUnitByCode(DEPARTMENT_UNIT_CODE).isEmpty()) {
        		departmentUnitService.addDepartmentUnit(department.get().getId(), DEPARTMENT_UNIT_CODE, true);
        	}
        	
        	if (roleService.getRoleByName(RoleType.ROLE_USER).isEmpty()) {
        		roleService.addRole(RoleType.ROLE_USER.name());
        	}
        	
        	if (roleService.getRoleByName(RoleType.ROLE_MANAGER).isEmpty()) {
        		roleService.addRole(RoleType.ROLE_MANAGER.name());
        	}
        	
        	if (roleService.getRoleByName(RoleType.ROLE_ADMIN).isEmpty()) {
        		roleService.addRole(RoleType.ROLE_ADMIN.name());
        	}
        	
        	Optional<Role> role = roleService.getRoleByName(RoleType.ROLE_ADMIN);
        	
            Optional<DepartmentUnit> departmentUnit = departmentUnitService.getDepartmentUnitByCode(DEPARTMENT_UNIT_CODE);
            
            if (role.isPresent() && departmentUnit.isPresent() && userService.getUserByUsername(USER_USERNAME).isEmpty()) {
            	userService.addUser(new UserRequest(USER_USERNAME, USER_USERNAME, "Admin ADMIN", "00-00", departmentUnit.get().getId(), true, List.of(role.get().getId())));
            }
        };
    }

}
