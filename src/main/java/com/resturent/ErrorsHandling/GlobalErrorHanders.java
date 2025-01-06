package com.resturent.ErrorsHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalErrorHanders {

	 @ExceptionHandler(AuthenticationException.class)
	    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
	        ApiError apiError = new ApiError(
	                HttpStatus.UNAUTHORIZED.value(),
	                "Unauthorized",
	                ex.getMessage(),
	                request.getRequestURI()
	        );
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
	    }
	 
	 @ExceptionHandler(AccessDeniedException.class)
	    public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
	        ApiError apiError = new ApiError(
	                HttpStatus.FORBIDDEN.value(),
	                "Access Denied",
	                ex.getMessage(),
	                request.getRequestURI()
	        );
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError);
	    }
	 
	 @ExceptionHandler(Exception.class)
	    public ResponseEntity<ApiError> handleGlobalException(Exception ex, HttpServletRequest request) {
	        ApiError apiError = new ApiError(
	                HttpStatus.INTERNAL_SERVER_ERROR.value(),
	                "Internal Server Error",
	                ex.getMessage(),
	                request.getRequestURI()
	        );
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
	    }
}
