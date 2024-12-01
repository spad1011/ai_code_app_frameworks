package org.acme.ai_code_spsp.security;

import org.acme.ai_code_spsp.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/internal/users/**").hasRole("ADMIN")
                        .requestMatchers("/pets/**", "/households/**", "/graphql").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/statistics/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/public/**").permitAll()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/graphql") // Disable CSRF for GraphQL POST requests
                )
                .formLogin(withDefaults()) // Default form-based authentication
                .httpBasic(withDefaults()); // Basic auth for REST API

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserServiceImpl userServiceImpl) {
        return username -> userServiceImpl
                .findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        user.isUnlocked(),
                        true,
                        true,
                        true,
                        List.of(new SimpleGrantedAuthority(user.getRole()))
                ))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
