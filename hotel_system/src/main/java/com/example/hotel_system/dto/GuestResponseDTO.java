package com.example.hotel_system.dto;

import lombok.Data;

@Data
public class GuestResponseDTO {
    private Long id;
    private String fullName; // Gộp họ và tên cho đẹp
    private String email;
    private String phone;
    private Integer loyaltyPoints;
}