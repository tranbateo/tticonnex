package com.example.hotel_system.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoomRequestDTO {
    @NotBlank(message = "Số phòng không được để trống")
    private String roomNumber;

    @NotNull(message = "Phải chọn loại phòng")
    private Long roomTypeId;
}