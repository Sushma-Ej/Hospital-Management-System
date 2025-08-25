package com.example.HospitalManagementSystem.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_PATIENT")) {
                return "/patients";
            } else if (grantedAuthority.getAuthority().equals("ROLE_DOCTOR")) {
                return "/doctors";
            } else if (grantedAuthority.getAuthority().equals("ROLE_RECEPTIONIST")) {
                return "/receptionist";
            }
        }
        // Default redirect if no specific role is found
        return "/main";
    }
}
