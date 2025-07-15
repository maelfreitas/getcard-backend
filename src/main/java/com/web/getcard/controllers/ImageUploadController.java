package com.web.getcard.controllers;

import com.web.getcard.services.GoogleDriveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/upload")
public class ImageUploadController {

    private final GoogleDriveService driveService;

    public ImageUploadController(GoogleDriveService driveService) {
        this.driveService = driveService;
    }

    @PostMapping("/profile-image")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = driveService.uploadFile(file);
            Map<String, String> response = Map.of("url", imageUrl);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Falha ao enviar imagem: " + e.getMessage()));
        }
    }
}


