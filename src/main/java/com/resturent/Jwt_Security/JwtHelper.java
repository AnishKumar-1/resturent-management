package com.resturent.Jwt_Security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtHelper {

	@Value("${SECRET_KEY}")
	private String SECRET_KEY;
	
	// decoding the key
	private SecretKey getKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
	}
	
	public String generate_Token(Authentication authentication) {
		String username=authentication.getName();
		String token=Jwts.builder()
				.header().empty().add("type", "JWT").and()
				.subject(username).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+ 60*1000))
				.signWith(getKey())
				.compact();
		return token;
	}
	
	//extract all claims from token
	//extract subject from all claims
	//extract expiration from date from token

}
