package com.resturent.AppSecurity;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resturent.ErrorsHandling.ApiError;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // HTTP 401
       
        ApiError apiError = new ApiError(
                HttpServletResponse.SC_UNAUTHORIZED,
                HttpStatus.UNAUTHORIZED,
                authException.getMessage(),
                request.getRequestURI()
        );

        
        ObjectMapper objectMapper=new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(apiError));
    }
}
