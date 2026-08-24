package io.github.mkhl28mi.memo_service.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import io.github.mkhl28mi.memo_service.domain.admin.user.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final CustomUserDetailsService userDetailsService;
	
	public SecurityConfig(CustomUserDetailsService userDetailsService) {
		super();
		this.userDetailsService = userDetailsService;
	}

	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
            .authorizeHttpRequests(auth -> auth
            		.requestMatchers("/", "/login", "/about", "/public/**", "/webjars/**").permitAll()
                	.requestMatchers("/admin/**", "/api/v1/admin/**").hasRole("ADMIN")
                	.anyRequest().authenticated()
            )
            .authenticationProvider(authenticationProvider())
            .formLogin(Customizer.withDefaults())
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                )
            .httpBasic(AbstractHttpConfigurer::disable);
             
        return http.build();
    }
	
	@Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
	
	@Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
}
