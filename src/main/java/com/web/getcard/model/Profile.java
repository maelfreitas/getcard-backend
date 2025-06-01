package com.web.getcard.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Profile {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String bio;
    private String avatarUrl;
    private String nfcId;
    private String qrCodeId;
    private List<Map<String, String>> links;
    private String uid; // UID do Firebase

}


