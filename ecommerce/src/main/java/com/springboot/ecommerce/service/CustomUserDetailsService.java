package com.springboot.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.ecommerce.entity.Users;
import com.springboot.ecommerce.enums.Role;
import com.springboot.ecommerce.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // Map roles using the Role enum
        Role roleEnum = user.getRole(); // No need for valueOf since user.getRole() returns Role
        if (roleEnum == null) {
            throw new UsernameNotFoundException("Role not assigned to user: " + username);
        }

        // Convert roles to GrantedAuthority format
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(roleEnum.name()) // Use the role name as the authority
                .build();
    }
}
