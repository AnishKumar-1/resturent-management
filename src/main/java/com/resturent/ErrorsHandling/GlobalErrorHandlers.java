package com.resturent.ErrorsHandling;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalErrorHandlers {

	
	
	
	// Handle CustomDataNotFound exception
    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ApiError> handleCustomDataNotFound(ItemNotFoundException ex, HttpServletRequest request) {
        int statusCode = HttpStatus.NOT_FOUND.value(); // 404 for Not Found
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = ex.getLocalizedMessage(); // Custom error message

        ApiError apiError = new ApiError(
                statusCode,
                status,
                message,
                request.getRequestURI()
        );

        return new ResponseEntity<>(apiError, status);
    }

	
	
	
	
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleExceptions(Exception ex, HttpServletRequest request) {
        int statusCode;
        HttpStatus status;
        String message;

        if (ex instanceof BadCredentialsException) {
            statusCode = HttpStatus.UNAUTHORIZED.value();
            status = HttpStatus.UNAUTHORIZED;
            message = ex.getLocalizedMessage();
        } else if (ex instanceof AccessDeniedException) {
        	//AccessDeniedException
            statusCode = HttpStatus.FORBIDDEN.value();
            status = HttpStatus.FORBIDDEN;
            message = ex.getLocalizedMessage();
        } else if (ex instanceof MalformedJwtException || ex instanceof IllegalArgumentException || ex instanceof ExpiredJwtException) {
            statusCode = HttpStatus.BAD_REQUEST.value();
            status = HttpStatus.BAD_REQUEST;
            message = ex.getLocalizedMessage();
        } 
        else if (ex instanceof InvalidTokenException) {
            statusCode = HttpStatus.BAD_REQUEST.value();
            status = HttpStatus.BAD_REQUEST;
            message = ex.getLocalizedMessage();
        }
        else if (ex instanceof NoSuchElementException) {
            statusCode = HttpStatus.NOT_FOUND.value();
            status = HttpStatus.NOT_FOUND;
            message = ex.getLocalizedMessage();
        }else {
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            message = ex.getLocalizedMessage();
        }

        ApiError apiError = new ApiError(
                statusCode,
                status,
                message,
                request.getRequestURI()
        );

        return new ResponseEntity<>(apiError, status);
    }
}
