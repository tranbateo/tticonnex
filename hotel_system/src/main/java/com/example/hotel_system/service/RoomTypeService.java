package com.example.hotel_system.service;

import com.example.hotel_system.entity.RoomType;
import com.example.hotel_system.repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoomTypeService {
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    public RoomType createRoomType(RoomType roomType) {
        return roomTypeRepository.save(roomType);
    }

    public List<RoomType> getAllRoomTypes() {
        return roomTypeRepository.findAll();
    }
}