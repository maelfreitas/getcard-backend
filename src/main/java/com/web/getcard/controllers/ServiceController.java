package com.web.getcard.controllers;

import com.web.getcard.entities.Service;
import com.web.getcard.services.ServiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @PostMapping("/add/{profileId}")
    public Service addService(@PathVariable int profileId, @RequestBody Service service) {
        return serviceService.addServiceToProfile(profileId, service);
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

