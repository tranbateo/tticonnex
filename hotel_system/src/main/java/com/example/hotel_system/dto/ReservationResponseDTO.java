package com.example.hotel_system.dto;

import lombok.Data;
import java.time.LocalDate;
import java.math.BigDecimal;

@Data
public class ReservationResponseDTO {
    private Long id;
    private String confirmationNumber; // Mã đặt phòng
    private String guestName;          // Tên khách (thay vì cả object Guest)
    private String roomNumber;         // Số phòng (thay vì cả object Room)
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String status;
    private BigDecimal totalPrice;
}