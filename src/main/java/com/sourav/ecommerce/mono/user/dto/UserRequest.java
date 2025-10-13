package com.sourav.ecommerce.mono.user.dto;

import com.sourav.ecommerce.mono.user.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserRequest {

	private String firstName;
	private String lastName;
	private String userName;
	private String phnNo;
	private String password;
	private String email;
	private Role role;
}
