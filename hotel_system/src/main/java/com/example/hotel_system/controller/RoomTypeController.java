package com.example.hotel_system.controller;

import com.example.hotel_system.entity.RoomType;
import com.example.hotel_system.service.RoomTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/room-types")
@Tag(name = "Room Type Management", description = "Quản lý loại phòng")
public class RoomTypeController {
    @Autowired
    private RoomTypeService roomTypeService;

    @PostMapping
    public ResponseEntity<RoomType> createRoomType(@RequestBody RoomType roomType) {
        return new ResponseEntity<>(roomTypeService.createRoomType(roomType), HttpStatus.CREATED);
    }

    @GetMapping
    public List<RoomType> getAllRoomTypes() {
        return roomTypeService.getAllRoomTypes();
    }
}