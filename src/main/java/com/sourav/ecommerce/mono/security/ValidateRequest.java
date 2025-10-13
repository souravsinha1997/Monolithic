package com.sourav.ecommerce.mono.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ValidateRequest {
	
	@Autowired
	private JwtService jwtService;
	
	
	
    private String getAuthToken() {
//        var authentication = SecurityContextHolder.getContext().getAuthentication();
//        return authentication.getCredentials().toString();
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new RuntimeException("Missing or invalid Authorization header");
    }

    private int getUserId() {
        String token = getAuthToken();
        int customerId = jwtService.getCustomerId(token);
        return Integer.valueOf(customerId);
    }
    
    private String getCustomerRole() {
    	String token = getAuthToken();
    	String role = jwtService.extractRole(token);
    	return role;
    }
    
    public  boolean validateCustomer(int customerId) {
    	int tokenId = getUserId();
    	return customerId==tokenId;
    }
    
    public int getId() {
    	return getUserId();
    }
    
}
