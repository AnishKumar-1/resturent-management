package com.resturent.ErrorsHandling;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiError {

	private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    
    
    public ApiError(int status, String error, String message, String path) {
        this.timestamp = java.time.Instant.now().toString();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
    
    
}