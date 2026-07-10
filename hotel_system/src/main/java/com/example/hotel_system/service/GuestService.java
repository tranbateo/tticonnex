package com.example.hotel_system.service;

import com.example.hotel_system.dto.GuestRequestDTO;
import com.example.hotel_system.dto.GuestResponseDTO;
import com.example.hotel_system.entity.Guest;
import com.example.hotel_system.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GuestService {

    @Autowired
    private GuestRepository guestRepository;

    // 1. Lấy danh sách có phân trang (Pagination)
    public Page<GuestResponseDTO> getAllGuests(Pageable pageable) {
        Page<Guest> guestPage = guestRepository.findAll(pageable);
        // Chuyển đổi từng Guest Entity -> GuestResponseDTO
        return guestPage.map(this::convertToResponseDTO);
    }

    // 2. Tạo khách mới từ DTO
    public GuestResponseDTO createGuest(GuestRequestDTO request) {
        if (guestRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email đã tồn tại!");
        }

        Guest guest = new Guest();
        guest.setFirstName(request.getFirstName());
        guest.setLastName(request.getLastName());
        guest.setEmail(request.getEmail());
        guest.setPhone(request.getPhone());
        guest.setLoyaltyPoints(0);

        Guest savedGuest = guestRepository.save(guest);
        return convertToResponseDTO(savedGuest);
    }

    // Hàm phụ: Chuyển Entity sang DTO (Helper method)
    private GuestResponseDTO convertToResponseDTO(Guest guest) {
        GuestResponseDTO dto = new GuestResponseDTO();
        dto.setId(guest.getId());
        dto.setFullName(guest.getFirstName() + " " + guest.getLastName());
        dto.setEmail(guest.getEmail());
        dto.setPhone(guest.getPhone());
        dto.setLoyaltyPoints(guest.getLoyaltyPoints());
        return dto;
    }
}