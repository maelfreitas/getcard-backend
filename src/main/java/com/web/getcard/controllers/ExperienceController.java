package com.web.getcard.controllers;

import com.web.getcard.entities.Experience;
import com.web.getcard.services.ExperienceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/experience")
public class ExperienceController {

    private final ExperienceService experienceService;

    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @PostMapping("/add/{profileId}")
    public Experience addExperience(@PathVariable int profileId, @RequestBody Experience experience) {
        return experienceService.addExperienceToProfile(profileId, experience);
    }

    @GetMapping("/list/{profileId}")
    public List<Experience> getExperiences(@PathVariable int profileId) {
        return experienceService.getExperiencesByProfile(profileId);
    }

    @DeleteMapping("/{experienceId}")
    public void deleteExperience(@PathVariable int experienceId) {
        experienceService.deleteExperience(experienceId);
    }
}

