package com.web.getcard.repositories;

import com.web.getcard.entities.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience, Integer> {
    List<Experience> findByProfileId(int profileId);
}
