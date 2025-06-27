package com.web.getcard.services;

import com.web.getcard.entities.Card;
import com.web.getcard.entities.Profile;
import com.web.getcard.entities.User;
import com.web.getcard.repositories.CardRepository;
import com.web.getcard.repositories.ProfileRepository;
import com.web.getcard.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository, CardRepository cardRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }

    public Profile createOrUpdateProfile(int userId, Profile profileData) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }

        User user = userOptional.get();
        Optional<Profile> existingProfile = profileRepository.findByUserId(userId);

        Profile profile = existingProfile.orElse(new Profile());
        profile.setUser(user);
        profile.setName(profileData.getName());
        profile.setBio(profileData.getBio());
        profile.setPhone(profileData.getPhone());
        profile.setEmail(profileData.getEmail());
        profile.setProfileImageUrl(profileData.getProfileImageUrl());
        profile.setInstagram(profileData.getInstagram());
        if (!profile.getInstagram().startsWith("http")) {
            profile.setInstagram("https://" + profileData.getInstagram());
        }
        profile.setLinkedin(profileData.getLinkedin());
        if (!profile.getLinkedin().startsWith("http")) {
            profile.setLinkedin("https://" + profileData.getLinkedin());
        }
        profile.setTheme(profileData.getTheme());



        return profileRepository.save(profile);
    }

    public Optional<Profile> getProfileByUserId(int userId) {
        return profileRepository.findByUserId(userId);
    }

    public Optional<Profile> getProfileByCardCode(String cardCode) {
        Optional<Card> cardOpt = cardRepository.findByCode(cardCode);
        if (cardOpt.isEmpty() || cardOpt.get().getUser() == null) {
            return Optional.empty();
        }

        int userId = cardOpt.get().getUser().getId();
        return profileRepository.findByUserId(userId);
    }

    public Profile createEmptyProfileForUser(User user) {
        Profile profile = new Profile();
        profile.setUser(user);
        profile.setName(user.getUsername());
        return profileRepository.save(profile);
    }

    public Profile updateProfile(int userId, Profile updatedProfile) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        profile.setName(updatedProfile.getName());
        profile.setBio(updatedProfile.getBio());
        profile.setPhone(updatedProfile.getPhone());
        profile.setEmail(updatedProfile.getEmail());
        profile.setProfileImageUrl(updatedProfile.getProfileImageUrl());
        profile.setInstagram(updatedProfile.getInstagram());
        if (!profile.getInstagram().startsWith("http")) {
            profile.setInstagram("https://" + updatedProfile.getInstagram());
        }
        profile.setLinkedin(updatedProfile.getLinkedin());
        if (!profile.getLinkedin().startsWith("http")) {
            profile.setLinkedin("https://" + updatedProfile.getLinkedin());
        }
        profile.setTheme(updatedProfile.getTheme());

        return profileRepository.save(profile);
    }
}
