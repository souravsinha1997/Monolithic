package com.sourav.ecommerce.mono.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sourav.ecommerce.mono.dto.MessageResponse;
import com.sourav.ecommerce.mono.user.dto.UserRequest;
import com.sourav.ecommerce.mono.user.dto.UserResponse;
import com.sourav.ecommerce.mono.user.service.UserService;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

	private final UserService userService;
	
	public AdminController(UserService userService) {
		this.userService=userService;
	}

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest userRequest,@PathVariable int id) {
        return ResponseEntity.ok(userService.updateUser(userRequest, id));
    }
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable int id) {
        return ResponseEntity.ok(new MessageResponse(userService.deleteUser(id)));
    }
    
    
}
