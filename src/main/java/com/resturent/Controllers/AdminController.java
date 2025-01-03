package com.resturent.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resturent.Modules.AdminModule;
import com.resturent.Services.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@PostMapping("/login")
	public ResponseEntity<String> adminLogin(@RequestBody AdminModule adminModule) {
		return new ResponseEntity<>(adminService.login(adminModule),HttpStatus.OK);
	}
}
