package com.web.getcard.controllers;

import com.web.getcard.entities.Profile;
import com.web.getcard.entities.User;
import com.web.getcard.repositories.UserRepository;
import com.web.getcard.services.ProfileService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;
    private final UserRepository userRepository;

    public ProfileController(ProfileService profileService, UserRepository userRepository) {
        this.profileService = profileService;
        this.userRepository = userRepository;
    }

    // Atualizar ou criar o perfil
    @PostMapping("/{userId}")
    public Profile updateProfile(@PathVariable int userId, @RequestBody Profile profile) {
        return profileService.createOrUpdateProfile(userId, profile);
    }

    @GetMapping("/me")
    public Profile getMyProfile(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return profileService.getProfileByUserId(user.getId())
                .orElseGet(() -> profileService.createEmptyProfileForUser(user));
    }

    @PutMapping("/me")
    public Profile updateMyProfile(@RequestBody Profile updatedProfile, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return profileService.updateProfile(user.getId(), updatedProfile);
    }

    // Buscar perfil por userId (privado - só quando o próprio usuário quiser editar)
    @GetMapping("/{userId}")
    public Profile getProfile(@PathVariable int userId) {
        return profileService.getProfileByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));
    }

    @GetMapping("/public/{cardCode}")
    public Profile getPublicProfile(@PathVariable String cardCode) {
        return profileService.getProfileByCardCode(cardCode)
                .orElseThrow(() -> new RuntimeException("Perfil público não encontrado"));
    }

}
