package com.springboot.ecommerce.service;

import com.springboot.ecommerce.entity.Users;
import com.springboot.ecommerce.enums.Role;
import com.springboot.ecommerce.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Users registerUser(Users user) {
        logger.info("Registering new user: {}", user.getUsername());

        // Encrypt the password before saving the user
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // Set the default role to CUSTOMER if not provided
        if (user.getRole() == null) {
            user.setRole(Role.CUSTOMER);
            logger.info("Defaulting role to CUSTOMER for user '{}'.", user.getUsername());
        } else {
            logger.info("Role '{}' assigned to user '{}'.", user.getRole(), user.getUsername());
        }

        // Save the user to the database
        Users savedUser = userRepository.save(user);
        logger.info("User '{}' registered successfully with ID: {}", savedUser.getUsername(), savedUser.getId());

        return savedUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Loading user by username: {}", username);

        // Fetch user by username
        Users user = userRepository.findByUsername(username);

        if (user == null) {
            logger.error("User not found with username: {}", username);
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        logger.info("User '{}' found. Preparing UserDetails object.", username);

        // Return a UserDetails object
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRole().getRoleName()) // Use the role from the enum
                .build();
    }
}
