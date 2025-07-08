package com.web.getcard.services;

import com.web.getcard.entities.Product;
import com.web.getcard.entities.Service;
import com.web.getcard.entities.Profile;
import com.web.getcard.repositories.ServiceRepository;
import com.web.getcard.repositories.ProfileRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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




    public Service updateService(int serviceId, Service updatedService) {
        Service existing = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        existing.setTitle(updatedService.getTitle());
        existing.setDescription(updatedService.getDescription());
        return serviceRepository.save(existing);
    }

    public void deleteService(int serviceId) {
        serviceRepository.deleteById(serviceId);
    }
}

