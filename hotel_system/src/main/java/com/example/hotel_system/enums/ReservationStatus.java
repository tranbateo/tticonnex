package com.example.hotel_system.enums;

public enum ReservationStatus {
    PENDING,        // Chờ xác nhận
    CONFIRMED,      // Đã xác nhận
    CHECKED_IN,     // Khách đã nhận phòng
    CHECKED_OUT,    // Khách đã trả phòng
    CANCELLED       // Đã hủy
}