package com.sourav.ecommerce.mono.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sourav.ecommerce.mono.user.entity.User;

public interface UserRepository extends JpaRepository<User,Integer>{

	Optional<User> findByUserName(String userName);
    Optional<User> findById(Long id);

}
