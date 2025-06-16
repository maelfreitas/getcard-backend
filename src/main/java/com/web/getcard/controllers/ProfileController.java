package com.web.getcard.controllers;

import com.web.getcard.entities.Profile;
import com.web.getcard.services.ProfileService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/{userId}")
    public Profile saveOrUpdateProfile(@PathVariable int userId, @RequestBody Profile profileData) {
        return profileService.createOrUpdateProfile(userId, profileData);
    }

    @GetMapping("/{userId}")
    public Optional<Profile> getProfile(@PathVariable int userId) {
        return profileService.getProfileByUserId(userId);
    }
}
