package com.resturent.Jwt_Security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtHelper {

	@Value("${SECRET_KEY}")
	private String SECRET_KEY;
	
	// creating security key
	private SecretKey getKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
	}
	
	public String generate_Token(String username) {
		String token=Jwts.builder()
				.header().empty().add("type", "JWT").and()
				.subject(username).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+ 60*60*1000))
				.signWith(getKey())
				.compact();
		return token;
	}
	
	//extract all claims from token
	public <T> T extractAllClaims(String token,Function<Claims, T> claimResolver) {
		Claims claims=Jwts.parser().verifyWith(getKey())
				.build().parseSignedClaims(token).getPayload();
		return claimResolver.apply(claims);
	}
	//extract subject from all claims
	
	public String extractUsername(String token) {
		return extractAllClaims(token,Claims::getSubject);
	}
	//extract expiration from date from token
	public boolean isTokenExpired(String token) {
	 return extractAllClaims(token,Claims::getExpiration).before(new Date());	
	}

	//validate token
	public boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith(getKey()).build().parse(token);
			return true;
		}catch(ExpiredJwtException ex) {
			throw new ExpiredJwtException(null,null,"Token has expired");
		}catch(MalformedJwtException ex) {
			throw new MalformedJwtException("Invalid JWT token");
		}catch(Exception ex) {
			throw new RuntimeException("something went wrong");
		}catch(InternalError internal) {
			throw new InternalError("internal server error");
		}
	}
}
