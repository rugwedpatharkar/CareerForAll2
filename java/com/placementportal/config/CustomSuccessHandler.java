package com.placementportal.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ADMIN"))) {
            response.sendRedirect("/adminhome"); // Redirect to admin page for users with ROLE ADMIN
        } else if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("USER"))) {
            response.sendRedirect("/userhome"); // Redirect to user page for users with ROLE USER
        }else if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("HR"))) {
            response.sendRedirect("/profile"); // Redirect to user page for users with ROLE HR
        }
        else if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("PO"))) {
            response.sendRedirect("/pohome"); // Redirect to user page for users with ROLE PO
        }
        else{
            response.sendRedirect("/"); // Default redirect for other roles or unauthenticated users
        }
				
	}
	
}
