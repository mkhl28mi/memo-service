package io.github.mkhl28mi.memo_service.config;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import io.github.mkhl28mi.memo_service.config.security.CustomUserDetails;
import io.github.mkhl28mi.memo_service.domain.admin.user.dto.response.UserResponse;

@ControllerAdvice
public class GlobalUserModelAdvice {
	
	@ModelAttribute("currentUser")
    public UserResponse getCurrentUserEntity() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails userDetails) {
            return new UserResponse(userDetails.getUser()); 
        }

        return null;
    }

}
