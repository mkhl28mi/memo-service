package io.github.mkhl28mi.memo_service.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.mkhl28mi.memo_service.domain.role.entity.Role.RoleType;
import io.github.mkhl28mi.memo_service.domain.role.service.RoleService;

@Configuration
public class DataInitializerConfig {
	
	@Bean
    CommandLineRunner initDatabase(RoleService roleService) {
        return args -> {
            if (roleService.getCount() == 0) {
            	roleService.addRole(RoleType.ROLE_USER.name());
            	roleService.addRole(RoleType.ROLE_MANAGER.name());
            	roleService.addRole(RoleType.ROLE_ADMIN.name());                
            }
        };
    }

}
