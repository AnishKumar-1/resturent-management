package com.resturent.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.resturent.ErrorsHandling.InvalidTokenException;
import com.resturent.Jwt_Security.CustomJwtFilter;
import com.resturent.Jwt_Security.JwtHelper;
import com.resturent.Modules.AdminModule;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class AdminController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private CustomJwtFilter customJwtFilter;

    @Value("${accessExpirationTime}")
    private long accessExpirationTime;
    @Value("${refreshExpirationTime}")
    private long refreshExpirationTime;

    
    
    
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> adminLogin(@RequestBody AdminModule adminModule) {
        authenticate(adminModule.getUsername(), adminModule.getPassword());

        String accessToken = jwtHelper.generate_Token(adminModule.getUsername(), accessExpirationTime);
        String refreshToken = jwtHelper.generate_Token(adminModule.getUsername(), refreshExpirationTime);

        JwtResponse jwtResponse = new JwtResponse(accessToken, refreshToken);

        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    @GetMapping("/test")
    public String test() {
        return "test api";
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshAccessToken(HttpServletRequest request) {
        String refreshTokenFromHeader = customJwtFilter.tokenFromHeader(request);
        return new ResponseEntity<>(refreshAcessToken(refreshTokenFromHeader), HttpStatus.OK);
    }
    
    

    //authentication user for login purpose
    private void authenticate(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        if (authentication.isAuthenticated())
            SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    
    //generate refresh access token
    private String refreshAcessToken(String refreshTokenFromHeader) {
    	
            jwtHelper.validateToken(refreshTokenFromHeader);
            String username = jwtHelper.extractUsername(refreshTokenFromHeader);
            String newAccessToken = jwtHelper.generate_Token(username, accessExpirationTime);
            return newAccessToken;
    }
}
