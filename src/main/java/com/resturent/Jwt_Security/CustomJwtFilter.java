package com.resturent.Jwt_Security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.resturent.AppSecurity.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomJwtFilter extends OncePerRequestFilter {

	
	
	@Autowired
	private JwtHelper jwtHelper;
	@Autowired
	private CustomUserDetailsService customUserDetails;
	
	@Autowired
	@Qualifier("handlerExceptionResolver")
	private HandlerExceptionResolver exceptionResolver;
	
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException, BadCredentialsException {
		// TODO Auto-generated method stub
		
	    String path = request.getServletPath();
	    // Allow paths like Swagger, API login, and others
	    if (path.equals("/api/login") || 
	        path.startsWith("/swagger-ui") || 
	        path.startsWith("/v3/api-docs") || 
	        path.startsWith("/swagger-resources") || 
	        path.startsWith("/webjars")) {
	        filterChain.doFilter(request, response);
	        return;
	    }

		 
		 try {
			 
			 String authToken=tokenFromHeader(request);
			 if(jwtHelper.validateToken(authToken)) {
				 String username=jwtHelper.extractUsername(authToken);
				 UserDetails userDetails=customUserDetails.loadUserByUsername(username);
				 UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new
						 UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
				 usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				 SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			 }

		 }catch(Exception ex) {
				exceptionResolver.resolveException(request, response, null, ex);
				return;
			}
		  
		 filterChain.doFilter(request, response);

	}
	
	//get token from header and verify
	public String tokenFromHeader(HttpServletRequest request) {
		String token=request.getHeader("Authorization");
		if(token==null || !token.startsWith("Bearer")) {
			throw new IllegalArgumentException("Token is empty or not found");
		}
		return token.substring(7);
	}

}
