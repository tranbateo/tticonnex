package com.example.hotel_system.controller;

import com.example.hotel_system.dto.GuestRequestDTO;
import com.example.hotel_system.dto.GuestResponseDTO;
import com.example.hotel_system.service.GuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/guests")
@Tag(name = "Guest Management", description = "APIs quản lý thông tin khách hàng chuyên nghiệp")
public class GuestController {

    @Autowired
    private GuestService guestService;

    @Operation(summary = "Lấy danh sách khách hàng (Có phân trang)",
            description = "Trả về danh sách khách hàng theo trang. Mặc định 10 khách/trang.")
    @GetMapping
    public ResponseEntity<Page<GuestResponseDTO>> getAllGuests(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(guestService.getAllGuests(pageable));
    }

    @Operation(summary = "Tạo khách hàng mới",
            description = "Thêm khách mới với các quy tắc validate (Email, SĐT...)")
    @PostMapping
    public ResponseEntity<GuestResponseDTO> createGuest(
            @Valid @RequestBody GuestRequestDTO request) {
        return new ResponseEntity<>(guestService.createGuest(request), HttpStatus.CREATED);
    }
}