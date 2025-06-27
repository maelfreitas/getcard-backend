package com.web.getcard.services;

import com.web.getcard.entities.Experience;
import com.web.getcard.entities.Profile;
import com.web.getcard.repositories.ExperienceRepository;
import com.web.getcard.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final ProfileRepository profileRepository;

    public ExperienceService(ExperienceRepository experienceRepository, ProfileRepository profileRepository) {
        this.experienceRepository = experienceRepository;
        this.profileRepository = profileRepository;
    }

    public Experience addExperienceToProfile(int profileId, Experience experience) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile não encontrado"));

        experience.setProfile(profile);
        return experienceRepository.save(experience);
    }

    public List<Experience> getExperiencesByProfile(int profileId) {
        return experienceRepository.findByProfileIdOrderByEndYearDesc(profileId);
    }


    public void deleteExperience(int experienceId) {
        experienceRepository.deleteById(experienceId);
    }
}

