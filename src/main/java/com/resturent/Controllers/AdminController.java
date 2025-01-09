package com.resturent.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resturent.Common_POJO.JwtResponse;
import com.resturent.Jwt_Security.JwtHelper;
import com.resturent.Modules.AdminModule;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtHelper jwtHelper;
	
	
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> adminLogin(@RequestBody AdminModule adminModule) {
		
		authenticate(adminModule.getUsername(),adminModule.getPassword());
		
		String token=jwtHelper.generate_Token(adminModule.getUsername());
		
		JwtResponse jwtResponse=new JwtResponse(token,"refresh token");
		
		return new ResponseEntity<>(jwtResponse,HttpStatus.OK);
	}
	
	
	
	@GetMapping("/test")
	public String test() {
		return "test api";
	}
	
	
	
	//authentication check for login
	//exception will automatically thrown by spring security if not authenticated
	private void authenticate(String username,String password) {
		Authentication authentication=
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		if(authentication.isAuthenticated())
		   SecurityContextHolder.getContext().setAuthentication(authentication);
		
	}
}
