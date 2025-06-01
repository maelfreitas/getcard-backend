package com.web.getcard.model;


import lombok.Data;
import java.time.Instant;

@Data
public class Card {
    private String id;          // cardId (NFC ou QRCode)
    private String uid;         // UID do Firebase
    private Instant createdAt;
    private boolean activated;
}



