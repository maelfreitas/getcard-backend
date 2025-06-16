package com.web.getcard.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String phone;
    private String bio;
    private String profileImageUrl;

    @Column(columnDefinition = "TEXT")
    private String socialLinks; // Pode guardar links de redes sociais como JSON ou String

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
