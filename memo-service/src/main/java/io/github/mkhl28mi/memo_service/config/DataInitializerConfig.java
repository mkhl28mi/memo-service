package io.github.mkhl28mi.memo_service.config;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.mkhl28mi.memo_service.domain.role.entity.Role;
import io.github.mkhl28mi.memo_service.domain.role.entity.Role.RoleType;
import io.github.mkhl28mi.memo_service.domain.role.service.RoleService;
import io.github.mkhl28mi.memo_service.domain.user.dto.request.UserRequest;
import io.github.mkhl28mi.memo_service.domain.user.service.UserService;

@Configuration
public class DataInitializerConfig {
	
	@Bean
    CommandLineRunner initDatabase(RoleService roleService, UserService userService) {
        return args -> {
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
        	
            if (role.isPresent() && userService.getUserByUsername("admin").isEmpty()) {
            	userService.addUser(new UserRequest("admin", "1111", "Admin ADMIN", "00-00", true, List.of(role.get().getId())));
            }
        };
    }

}
