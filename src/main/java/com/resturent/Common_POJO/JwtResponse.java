package com.resturent.Common_POJO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {

	private String token;
	private final String type="jwt";
	private String refreshToken;
	
	 // Constructor to initialize token and refreshToken
    public JwtResponse(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }
}
