package com.example.spring3_demo.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.spring3_demo.jwt.JwtService;
import com.example.spring3_demo.user.Role;
import com.example.spring3_demo.user.User;
import com.example.spring3_demo.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
            
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(deleteSigns(request.getUsername()), deleteSigns(request.getPassword())));
        UserDetails user= userRepository.findByUsername(deleteSigns(request.getUsername()))
            .orElseThrow(() -> new RuntimeException("User not found"));
            String token = jwtService.getToken(user);
            return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
            
            User user = User.builder()
                .username(deleteSigns(request.getUsername()))
                .password(passwordEncoder.encode(deleteSigns(request.getPassword())))
                .firstname(deleteSigns(request.getFirstname()))
                .lastname(deleteSigns(request.getLastname()))
                .country(deleteSigns(request.getCountry()))
                .role(Role.USER)
                .build();

            userRepository.save(user);

            return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();

    }

    private String deleteSigns(String s) { // Prevent SQL injection
        return s.replaceAll("[^a-zA-Z0-9]", "");
    }

    

}
