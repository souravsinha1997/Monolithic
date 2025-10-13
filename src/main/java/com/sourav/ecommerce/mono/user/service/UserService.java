package com.sourav.ecommerce.mono.user.service;

import java.util.List;

import com.sourav.ecommerce.mono.user.dto.LoginRequest;
import com.sourav.ecommerce.mono.user.dto.UserRequest;
import com.sourav.ecommerce.mono.user.dto.UserResponse;
import com.sourav.ecommerce.mono.user.entity.Token;



public interface UserService {

	List<UserResponse> getAllUsers();
	UserResponse getUser(int id);
	String deleteUser(int id);
	UserResponse updateUser(UserRequest request, int id);
	String saveUser(UserRequest userRequest);
	Token loginUser(LoginRequest loginRequest);
	UserResponse getUserByToken();
}
