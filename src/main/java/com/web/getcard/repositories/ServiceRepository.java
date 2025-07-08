package com.web.getcard.repositories;

import com.web.getcard.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Integer> {
    List<Service> findByProfileId(int profileId);
    Optional<Service> findById(int serviceId);
}
