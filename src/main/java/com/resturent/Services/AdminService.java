package com.resturent.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.resturent.Jwt_Security.JwtHelper;
import com.resturent.Modules.AdminModule;

@Service
public class AdminService {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtHelper jwtHelper;
	
	public String login(AdminModule admin) {
	        Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(admin.getUsername(), admin.getPassword())
	        );
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        return jwtHelper.generate_Token(authentication);
	    
	}
}
