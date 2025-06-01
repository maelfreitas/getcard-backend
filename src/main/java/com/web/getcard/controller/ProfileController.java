package com.web.getcard.controller;

import com.google.firebase.auth.FirebaseToken;
import com.web.getcard.model.Profile;
import com.web.getcard.service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping
@RequiredArgsConstructor
@CrossOrigin
public class ProfileController {

    private final ProfileService service;

    // Página pública via QR/NFC
    @GetMapping("/visit/{id}")
    public Profile getPublicProfile(@PathVariable String id) throws ExecutionException, InterruptedException {
        return service.getProfileByQrOrNfc(id);
    }

    // Cadastro inicial
    @PostMapping("/register")
    public String register(@RequestBody Profile profile) throws ExecutionException, InterruptedException {
        return service.saveProfile(profile);
    }

    // Obter perfil (simples, sem auth ainda)
    @GetMapping("/profile/{id}")
    public Profile getProfile(@PathVariable String id) throws ExecutionException, InterruptedException {
        return service.getProfileById(id);
    }

    // Atualizar perfil
    @PutMapping("/profile/{id}")
    public String updateProfile(@PathVariable String id, @RequestBody Profile profile) throws ExecutionException, InterruptedException {
        profile.setId(id);
        return service.updateProfile(profile);
    }

    @GetMapping("/profile/me")
    public Profile getMyProfile(HttpServletRequest request) throws Exception {
        String uid = ((FirebaseToken) request.getAttribute("firebaseUser")).getUid();
        return service.getProfileByUid(uid);
    }

    @PutMapping("/profile/me")
    public String updateMyProfile(@RequestBody Profile profile, HttpServletRequest request) throws Exception {
        String uid = ((FirebaseToken) request.getAttribute("firebaseUser")).getUid();
        profile.setUid(uid);
        return service.updateProfile(profile);
    }
}
