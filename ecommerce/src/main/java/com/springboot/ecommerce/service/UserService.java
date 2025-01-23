package com.springboot.ecommerce.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.springboot.ecommerce.entity.Users;

public interface UserService {
    Users registerUser(Users user);

	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}

