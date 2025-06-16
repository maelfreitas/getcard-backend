package com.web.getcard.controllers;

import com.web.getcard.entities.User;
import com.web.getcard.repositories.UserRepository;
import com.web.getcard.security.JwtUtil;
import com.web.getcard.services.UserService;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, JwtUtil jwtUtil,
                          AuthenticationManager authenticationManager, UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public User register(@RequestBody UserRegistrationRequest request) {
        return userService.createUser(request.getUser(), request.getCardCode());
    }

    @Data
    public static class UserRegistrationRequest {
        private User user;
        private String cardCode;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new BadCredentialsException("Usuário não encontrado");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Senha incorreta");
        }

        String token = jwtUtil.generateToken(email);

        return Map.of(
                "token", token,
                "userId", user.getId(),
                "userName", user.getUsername()
        );
    }

}

