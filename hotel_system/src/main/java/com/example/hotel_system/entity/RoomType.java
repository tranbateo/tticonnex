package com.example.hotel_system.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "room_types")
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // VD: Deluxe, Standard

    private BigDecimal basePrice; // Giá cơ bản
    private Integer maxOccupancy; // Số người tối đa
}