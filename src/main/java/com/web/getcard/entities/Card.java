package com.web.getcard.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String code; // Pode ser o QR Code ou identificação NFC

    private String valCode;
    private boolean active = false;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
