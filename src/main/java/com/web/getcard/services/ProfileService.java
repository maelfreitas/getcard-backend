package com.web.getcard.services;

import com.web.getcard.entities.Profile;
import com.web.getcard.entities.User;
import com.web.getcard.repositories.ProfileRepository;
import com.web.getcard.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    public Profile createOrUpdateProfile(int userId, Profile profileData) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();
        Optional<Profile> existingProfile = profileRepository.findByUser(user);

        Profile profile = existingProfile.orElse(new Profile());
        profile.setUser(user);
        profile.setBio(profileData.getBio());
        profile.setPhone(profileData.getPhone());
        profile.setProfileImageUrl(profileData.getProfileImageUrl());
        profile.setSocialLinks(profileData.getSocialLinks());

        return profileRepository.save(profile);
    }

    public Optional<Profile> getProfileByUserId(int userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        return userOpt.flatMap(profileRepository::findByUser);
    }
}
