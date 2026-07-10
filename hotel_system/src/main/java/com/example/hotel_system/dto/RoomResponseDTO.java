package com.example.hotel_system.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class RoomResponseDTO {
    private Long id;
    private String roomNumber;
    private String status;

    // Thông tin loại phòng đi kèm (Flattening)
    private String roomTypeName;
    private BigDecimal price;
}