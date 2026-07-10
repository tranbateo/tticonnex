package com.example.hotel_system.entity;

import com.example.hotel_system.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String confirmationNumber; // Mã đặt phòng

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    // trường tổng tiền
    private BigDecimal totalPrice;

}