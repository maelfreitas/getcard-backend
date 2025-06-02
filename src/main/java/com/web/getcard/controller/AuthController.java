package com.web.getcard.controller;


import com.web.getcard.model.User;
import com.web.getcard.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            String uid = userService.createUser(user);
            return ResponseEntity.ok("Usuário cadastrado com sucesso. UID: " + uid);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao cadastrar: " + e.getMessage());
        }
    }

}

