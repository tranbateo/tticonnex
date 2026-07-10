package com.example.hotel_system.controller;

import com.example.hotel_system.dto.RoomRequestDTO;
import com.example.hotel_system.dto.RoomResponseDTO;
import com.example.hotel_system.enums.RoomStatus;
import com.example.hotel_system.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@Tag(name = "Room Management", description = "Quản lý phòng ở (Full 11 Chức năng)")
public class RoomController {

    @Autowired private RoomService roomService;

    // 1. Tạo phòng mới
    @Operation(summary = "1. Tạo phòng mới")
    @PostMapping
    public ResponseEntity<RoomResponseDTO> createRoom(@Valid @RequestBody RoomRequestDTO request) {
        return new ResponseEntity<>(roomService.createRoom(request), HttpStatus.CREATED);
    }

    // 2. Lấy tất cả phòng (Phân trang)
    @Operation(summary = "2. Lấy tất cả danh sách phòng")
    @GetMapping
    public ResponseEntity<Page<RoomResponseDTO>> getAllRooms(
            @PageableDefault(size = 10, sort = "roomNumber") Pageable pageable) {
        return ResponseEntity.ok(roomService.getAllRooms(pageable));
    }

    // 3. Lấy chi tiết 1 phòng
    @Operation(summary = "3. Lấy chi tiết phòng theo ID")
    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDTO> getRoomById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    // 4. Cập nhật phòng
    @Operation(summary = "4. Cập nhật thông tin phòng")
    @PutMapping("/{id}")
    public ResponseEntity<RoomResponseDTO> updateRoom(
            @PathVariable Long id, @Valid @RequestBody RoomRequestDTO request) {
        return ResponseEntity.ok(roomService.updateRoom(id, request));
    }

    // 5. Xóa phòng
    @Operation(summary = "5. Xóa phòng")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    // 6. Cập nhật trạng thái phòng (Patch)
    @Operation(summary = "6. Cập nhật trạng thái phòng (Ví dụ: MAINTENANCE)")
    @PatchMapping("/{id}/status")
    public ResponseEntity<RoomResponseDTO> updateRoomStatus(
            @PathVariable Long id, @RequestParam RoomStatus status) {
        return ResponseEntity.ok(roomService.updateRoomStatus(id, status));
    }

    // 7. Tìm phòng trống theo ngày
    @Operation(summary = "7. Tìm phòng trống (Check-in/Check-out)")
    @GetMapping("/available")
    public ResponseEntity<List<RoomResponseDTO>> findAvailableRooms(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut) {
        return ResponseEntity.ok(roomService.searchAvailableRooms(checkIn, checkOut));
    }

    // 8. Lọc phòng theo Trạng thái
    @Operation(summary = "8. Lọc phòng theo trạng thái (AVAILABLE, OCCUPIED...)")
    @GetMapping("/status/{status}")
    public ResponseEntity<Page<RoomResponseDTO>> getRoomsByStatus(
            @PathVariable RoomStatus status,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(roomService.getRoomsByStatus(status, pageable));
    }

    // 9. Lọc phòng theo Loại phòng
    @Operation(summary = "9. Lọc phòng theo Loại phòng (ID)")
    @GetMapping("/room-type/{roomTypeId}")
    public ResponseEntity<Page<RoomResponseDTO>> getRoomsByRoomType(
            @PathVariable Long roomTypeId,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(roomService.getRoomsByRoomType(roomTypeId, pageable));
    }

    // 10. Đếm số lượng phòng theo trạng thái
    @Operation(summary = "10. Đếm số lượng phòng theo trạng thái")
    @GetMapping("/count/status/{status}")
    public ResponseEntity<Long> countRoomsByStatus(@PathVariable RoomStatus status) {
        return ResponseEntity.ok(roomService.countRoomsByStatus(status));
    }
}