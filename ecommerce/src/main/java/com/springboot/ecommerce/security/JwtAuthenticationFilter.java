package com.springboot.ecommerce.security;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final String SECRET_KEY;

    public JwtAuthenticationFilter(@Value("${jwt.secret}") String secretKey) {
        this.SECRET_KEY = secretKey;
    }

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, java.io.IOException {
        String jwtToken = resolveToken(request);

        if (jwtToken != null && validateToken(jwtToken)) {
            try {
                Claims claims = Jwts.parserBuilder()
                                    .setSigningKey(SECRET_KEY.getBytes())
                                    .build()
                                    .parseClaimsJws(jwtToken)
                                    .getBody();

                List<String> roles = claims.get("roles", List.class);
                if (roles != null) {
                    logger.info("Roles extracted from token: {}", roles);
                } else {
                    logger.error("Roles are null in the JWT token");
                }
            } catch (Exception e) {
                logger.error("Error parsing JWT token", e);
            }
        }

        chain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            logger.error("Invalid JWT token", e);
            return false;
        }
    }
}
