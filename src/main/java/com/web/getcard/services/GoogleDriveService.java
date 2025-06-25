package com.web.getcard.services;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.drive.model.File;

import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.ServiceAccountCredentials;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.UUID;

@Service
public class GoogleDriveService {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private final Drive drive;

    @Value("${google.service.account.key.path}")
    private String serviceAccountKeyPath;

    @Value("${google.drive.folder-id}")
    private String folderId;

    public GoogleDriveService(@Value("${google.service.account.key.path}") String serviceAccountKeyPath,
                              @Value("${google.drive.folder-id}") String folderId) throws Exception {
        this.serviceAccountKeyPath = serviceAccountKeyPath;
        this.folderId = folderId;

        var creds = ServiceAccountCredentials
                .fromStream(new FileInputStream(serviceAccountKeyPath))
                .createScoped(Collections.singletonList("https://www.googleapis.com/auth/drive"));

        var httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        this.drive = new Drive.Builder(httpTransport, JSON_FACTORY, new HttpCredentialsAdapter(creds))
                .setApplicationName("MeuAppDriveUploader")
                .build();
    }

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        String fileName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();

        File fileMetadata = new File();
        fileMetadata.setName(fileName);
        fileMetadata.setParents(Collections.singletonList(folderId));

        java.io.File convFile = java.io.File.createTempFile("upload", null);
        multipartFile.transferTo(convFile);

        var mediaContent = new FileContent(multipartFile.getContentType(), convFile);

        File uploaded = drive.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();

        // Torna público
        Permission anyone = new Permission()
                .setType("anyone")
                .setRole("reader")
                .setAllowFileDiscovery(false);
        drive.permissions().create(uploaded.getId(), anyone).execute();

        return "https://lh3.googleusercontent.com/d/" + uploaded.getId();


    }
}


