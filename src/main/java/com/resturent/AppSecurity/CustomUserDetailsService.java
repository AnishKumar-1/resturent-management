package com.resturent.AppSecurity;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.resturent.Modules.AdminModule;
import com.resturent.Repositories.AdminModuleRepo;

@Service
public class CustomUserDetailsService  implements UserDetailsService{

	@Autowired
	private AdminModuleRepo adminModuleRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username){
		// TODO Auto-generated method stub
		
		AdminModule adminModule = adminModuleRepo.findByUsername(username)
				.orElseThrow(()->new UsernameNotFoundException("User not found:" + username));
		
		return new User(adminModule.getUsername(),adminModule.getPassword(),Collections.emptyList());
	}
   
}
