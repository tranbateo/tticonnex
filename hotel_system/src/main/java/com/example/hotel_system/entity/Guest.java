package com.example.hotel_system.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data // Lombok tự sinh Getter/Setter
@Table(name = "guests")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    // Điểm tích lũy (BR-103)
    private Integer loyaltyPoints = 0;

    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted = false;
}