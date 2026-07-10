package com.example.hotel_system.controller;

import com.example.hotel_system.dto.ReservationDTO;
import com.example.hotel_system.dto.ReservationResponseDTO;
import com.example.hotel_system.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservations")
@Tag(name = "Reservation Management", description = "Quản lý đặt phòng, Check-in, Check-out")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    // 1. API Tạo đơn đặt phòng
    @Operation(summary = "Tạo đơn đặt phòng mới", description = "Tạo booking mới, kiểm tra phòng trống và tự động tính tiền")
    @PostMapping
    public ResponseEntity<ReservationResponseDTO> createReservation(@Valid @RequestBody ReservationDTO request) {
        return new ResponseEntity<>(reservationService.createReservation(request), HttpStatus.CREATED);
    }

    // 2. API Check-in (MỚI THÊM)
    @Operation(summary = "Check-in (Nhận phòng)", description = "Chuyển trạng thái đơn sang CHECKED_IN và phòng sang OCCUPIED")
    @PostMapping("/{id}/check-in")
    public ResponseEntity<String> checkIn(@PathVariable Long id) {
        reservationService.checkInGuest(id);
        return ResponseEntity.ok("Check-in thành công! Khách đã nhận phòng.");
    }

    // 3. API Check-out (MỚI THÊM)
    @Operation(summary = "Check-out (Trả phòng)", description = "Chuyển trạng thái đơn sang CHECKED_OUT và phòng sang MAINTENANCE (chờ dọn)")
    @PostMapping("/{id}/check-out")
    public ResponseEntity<String> checkOut(@PathVariable Long id) {
        reservationService.checkOutGuest(id);
        return ResponseEntity.ok("Check-out thành công! Phòng đang chờ dọn dẹp.");
    }
}