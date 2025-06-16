package com.web.getcard.controllers;

import com.web.getcard.entities.User;
import com.web.getcard.services.UserService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public User registerUser(@RequestBody UserRegistrationRequest request) {
        return userService.createUser(request.getUser(), request.getCardCode());
    }


    @Data
    public static class UserRegistrationRequest {
        private User user;
        private String cardCode;

    }

}
