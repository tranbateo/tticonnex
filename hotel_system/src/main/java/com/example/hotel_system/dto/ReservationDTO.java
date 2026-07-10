package com.example.hotel_system.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ReservationDTO {

    @NotNull(message = "ID khách hàng không được để trống")
    private Long guestId;

    @NotNull(message = "ID phòng không được để trống")
    private Long roomId;

    @NotNull(message = "Ngày nhận phòng là bắt buộc")
    @Future(message = "Ngày nhận phòng phải là ngày trong tương lai") // Luật: Phải là ngày mai trở đi
    private LocalDate checkInDate;

    @NotNull(message = "Ngày trả phòng là bắt buộc")
    @Future(message = "Ngày trả phòng phải là ngày trong tương lai")
    private LocalDate checkOutDate;
}