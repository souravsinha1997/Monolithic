package com.sourav.ecommerce.mono.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sourav.ecommerce.mono.user.dto.LoginRequest;
import com.sourav.ecommerce.mono.dto.MessageResponse;
import com.sourav.ecommerce.mono.user.dto.UserRequest;
import com.sourav.ecommerce.mono.user.dto.UserResponse;
import com.sourav.ecommerce.mono.user.entity.Token;
import com.sourav.ecommerce.mono.user.service.UserService;



@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService=userService;
	}
	
	@PostMapping("/signIn")
    public ResponseEntity<Token> loginUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }

    @PostMapping("/signUp")
    public ResponseEntity<MessageResponse> createUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(new MessageResponse(userService.saveUser(userRequest)));
    }
    
    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getUser() {
        return ResponseEntity.ok(userService.getUserByToken());
    }
    
    
}
