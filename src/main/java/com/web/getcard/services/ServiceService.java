package com.web.getcard.services;

import com.web.getcard.entities.Service;
import com.web.getcard.entities.Profile;
import com.web.getcard.repositories.ServiceRepository;
import com.web.getcard.repositories.ProfileRepository;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final ProfileRepository profileRepository;

    public ServiceService(ServiceRepository serviceRepository, ProfileRepository profileRepository) {
        this.serviceRepository = serviceRepository;
        this.profileRepository = profileRepository;
    }

    public Service addServiceToProfile(int profileId, Service service) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile não encontrado"));

        service.setProfile(profile);
        return serviceRepository.save(service);
    }

    public List<Service> getServicesByProfile(int profileId) {
        return serviceRepository.findByProfileId(profileId);
    }


    public void deleteService(int serviceId) {
        serviceRepository.deleteById(serviceId);
    }
}

