package com.example.hotel_system.entity;

import com.example.hotel_system.enums.RoomStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String roomNumber; // VD: 101, 202

    @Enumerated(EnumType.STRING)
    private RoomStatus status; // Trạng thái phòng

    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;
}