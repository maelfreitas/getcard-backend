package com.web.getcard.controllers;

import com.web.getcard.entities.Card;
import com.web.getcard.entities.User;
import com.web.getcard.repositories.CardRepository;
import com.web.getcard.repositories.UserRepository;
import com.web.getcard.services.UserService;
import org.springframework.security.core.Authentication;
import lombok.Data;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;


    public UserController(UserRepository userRepository, CardRepository cardRepository, UserService userService) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
        this.userService = userService;
    }
    @PostMapping("/register")
    public User registerUser(@RequestBody UserRegistrationRequest request) {
        return userService.createUser(request.getUser(), request.getCardCode(), request.getValCode());
    }


    @Data
    public static class UserRegistrationRequest {
        private User user;
        private String cardCode;
        private String valCode;

    }

    @GetMapping("/me")
    public Map<String, Object> getMyUserData(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        Optional<Card> card = cardRepository.findByUser(user);

        return Map.of(
                "id", user.getId(),
                "username", user.getUsername(),
                "email", user.getEmail(),
                "cardCode", card.map(Card::getCode).orElse(null)
        );
    }

}
