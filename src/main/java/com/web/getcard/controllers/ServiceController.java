package com.web.getcard.controllers;

import com.web.getcard.entities.Service;
import com.web.getcard.repositories.ServiceRepository;
import com.web.getcard.services.ServiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service")
public class ServiceController {

    private final ServiceService serviceService;
    private final ServiceRepository serviceRepository;

    public ServiceController(ServiceService serviceService, ServiceRepository serviceRepository) {
        this.serviceService = serviceService;
        this.serviceRepository = serviceRepository;
    }

    @PostMapping("/add/{profileId}")
    public Service addService(@PathVariable int profileId, @RequestBody Service service) {
        return serviceService.addServiceToProfile(profileId, service);
    }

    @GetMapping("/{serviceId}")
    public Service getProductById(@PathVariable int serviceId) {
        return serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
    }

    @PutMapping("/update/{serviceId}")
    public Service updateService(@PathVariable int serviceId, @RequestBody Service service) {
        return serviceService.updateService(serviceId, service);
    }

    @GetMapping("/list/{profileId}")
    public List<Service> getServices(@PathVariable int profileId) {
        return serviceService.getServicesByProfile(profileId);
    }

    @DeleteMapping("/{serviceId}")
    public void deleteService(@PathVariable int serviceId) {
        serviceService.deleteService(serviceId);
    }
}

