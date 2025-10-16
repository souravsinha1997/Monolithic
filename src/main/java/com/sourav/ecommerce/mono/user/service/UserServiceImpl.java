package com.sourav.ecommerce.mono.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sourav.ecommerce.mono.exception.InvalidLoginException;
import com.sourav.ecommerce.mono.exception.UserExists;
import com.sourav.ecommerce.mono.exception.UserNotFoundException;
import com.sourav.ecommerce.mono.security.JwtService;
import com.sourav.ecommerce.mono.security.ValidateRequest;
import com.sourav.ecommerce.mono.user.dto.LoginRequest;
import com.sourav.ecommerce.mono.user.dto.UserRequest;
import com.sourav.ecommerce.mono.user.dto.UserResponse;
import com.sourav.ecommerce.mono.user.entity.Token;
import com.sourav.ecommerce.mono.user.entity.User;
import com.sourav.ecommerce.mono.user.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final ValidateRequest validate;
	    
	public UserServiceImpl(ValidateRequest validate,UserRepository userRepo,PasswordEncoder passwordEncoder,JwtService jwtService) {
		this.userRepo = userRepo;
		this.jwtService = jwtService;
		this.passwordEncoder = passwordEncoder;
		this.validate = validate;
	}
	
	public List<UserResponse> getAllUsers(){
		List<User> users =  userRepo.findAll();
		List<UserResponse> response = new ArrayList<>();
		for(User user : users) {
			UserResponse userResponse = new UserResponse();
			userResponse.setEmail(user.getEmail());
			userResponse.setFirstName(user.getFirstName());
			userResponse.setLastName(user.getLastName());
			userResponse.setPhnNo(user.getPhnNo());
			userResponse.setUserName(user.getUsername());
			userResponse.setRole(user.getRole());
			response.add(userResponse);
		}
		return response;
	}
	
	public UserResponse getUser(int id){
		Optional<User> user =  userRepo.findById(id);
		UserResponse userResponse = new UserResponse();
		if(user==null) {
			throw new UserNotFoundException("User does not exist with id : "+id);
		}
		else {
			userResponse.setEmail(user.get().getEmail());
			userResponse.setFirstName(user.get().getFirstName());
			userResponse.setLastName(user.get().getLastName());
			userResponse.setPhnNo(user.get().getPhnNo());
			userResponse.setUserName(user.get().getUsername());
			userResponse.setRole(user.get().getRole());
		}
		return userResponse;
	}
	
	public UserResponse getUserByToken(){
		int id = validate.getId();
		Optional<User> user =  userRepo.findById(id);
		UserResponse userResponse = new UserResponse();
		if(user==null) {
			throw new UserNotFoundException("User does not exist with id : "+id);
		}
		else {
			userResponse.setEmail(user.get().getEmail());
			userResponse.setFirstName(user.get().getFirstName());
			userResponse.setLastName(user.get().getLastName());
			userResponse.setPhnNo(user.get().getPhnNo());
			userResponse.setUserName(user.get().getUsername());
			userResponse.setRole(user.get().getRole());
		}
		return userResponse;
	}
	
	@Transactional
	public String deleteUser(int id) {
		Optional<User> user = userRepo.findById(id);
		
		if(user!=null) {
			userRepo.delete(user.get());
		}
		else {
			throw new UserNotFoundException("User not found with id : "+id);
		}
		
		return "User account is removed successfully";
	}
	
	public UserResponse updateUser(UserRequest request, int id) {
		User savedUser = userRepo.findById(id).orElseThrow(()->new UserNotFoundException("User not found with id : "+id));
			
		
		if(request.getFirstName()!=null && !request.getFirstName().equals("")) {
			savedUser.setFirstName(request.getFirstName());
		}
		if(request.getLastName()!=null && !request.getLastName().equals("")) {
			savedUser.setLastName(request.getLastName());
		}
		if(request.getUserName()!=null && !request.getUserName().equals("")) {
			savedUser.setUserName(request.getUserName());
		}
		if(request.getPassword()!=null && !request.getPassword().equals("")) {
			savedUser.setPassword(passwordEncoder.encode(request.getPassword()));
		}
		if(request.getPhnNo()!=null && !request.getPhnNo().equals("")) {
			savedUser.setPhnNo(request.getPhnNo());
		}
		if(request.getEmail()!=null && !request.getEmail().equals("")) {
			savedUser.setEmail(request.getEmail());
		}
		
		User newUser = userRepo.save(savedUser);
		
		UserResponse response = new UserResponse();
		response.setEmail(newUser.getEmail());
		response.setFirstName(newUser.getFirstName());
		response.setLastName(newUser.getLastName());
		response.setPhnNo(newUser.getPhnNo());
		response.setRole(newUser.getRole());
		response.setUserName(newUser.getUsername());
		
		return response;
		
	}
	
	@Transactional
	public String saveUser(UserRequest userRequest) {
		
		User user = new User();
		user.setFirstName(userRequest.getFirstName());
		user.setLastName(userRequest.getLastName());
		user.setUserName(userRequest.getUserName());
		user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		user.setPhnNo(userRequest.getPhnNo());
		user.setEmail(userRequest.getEmail());
		user.setRole(userRequest.getRole());
		
		Optional<User> savedName = userRepo.findByUserName(userRequest.getUserName());
		if(!savedName.isEmpty()) throw new UserExists("User with same user name exists, use a different user name");
		Optional<User> savedPhone = userRepo.findByPhnNo(userRequest.getPhnNo());
		if(!savedPhone.isEmpty()) throw new UserExists("User with same Phone noe exists, use a different number");
		Optional<User> savedMail = userRepo.findByEmail(userRequest.getEmail());
		if(!savedMail.isEmpty()) throw new UserExists("User with same Email exists, use a different Email");
		
		userRepo.save(user);
		
		
		return "User registered successfully";
	}
	
    public Token loginUser(LoginRequest loginRequest) {
        User user = userRepo.findByUserName(loginRequest.getUsername())
                .orElseThrow(() -> new InvalidLoginException("Invalid username or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidLoginException("Invalid username or password");
        }

        String token = jwtService.generateToken(user);
        return new Token(token);
    }
	
}
