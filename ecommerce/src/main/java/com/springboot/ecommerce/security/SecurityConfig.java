package com.springboot.ecommerce.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springboot.ecommerce.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;


    @Autowired
    public SecurityConfig(@Lazy JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.info("Initializing BCryptPasswordEncoder bean");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring security filter chain");
        try {
            http.csrf().disable()
                .authorizeHttpRequests(authz -> authz
                    .requestMatchers("/api/users/register", "/api/users/login").permitAll()
                    .requestMatchers("/api/products/**").hasRole("ADMIN")
                    .requestMatchers("/api/products").hasRole("USER")
                    .requestMatchers("/h2-console/**").permitAll()
                    .anyRequest().authenticated()
                )
                .headers().frameOptions().sameOrigin()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Use the injected jwtAuthenticationFilter

            logger.info("Security filter chain configured successfully");
        } catch (Exception e) {
            logger.error("Error while configuring security filter chain", e);
            throw e;
        }
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        logger.info("Initializing AuthenticationManager bean");
        try {
            AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

            authenticationManagerBuilder.userDetailsService(customUserDetailsService)
                                        .passwordEncoder(passwordEncoder());

            logger.info("AuthenticationManager bean initialized successfully");
            return authenticationManagerBuilder.build();
        } catch (Exception e) {
            logger.error("Error while initializing AuthenticationManager bean", e);
            throw e;
        }
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        logger.info("Initializing DaoAuthenticationProvider bean");
        try {
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setUserDetailsService(customUserDetailsService);
            provider.setPasswordEncoder(passwordEncoder());
            logger.info("DaoAuthenticationProvider bean initialized successfully");
            return provider;
        } catch (Exception e) {
            logger.error("Error while initializing DaoAuthenticationProvider bean", e);
            throw e;
        }
    }
}
