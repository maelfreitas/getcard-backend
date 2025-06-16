package com.web.getcard.repositories;

import com.web.getcard.entities.Profile;
import com.web.getcard.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    Optional<Profile> findByUser(User user);
}
