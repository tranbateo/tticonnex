package com.example.hotel_system.service;

import com.example.hotel_system.dto.RoomRequestDTO;
import com.example.hotel_system.dto.RoomResponseDTO;
import com.example.hotel_system.entity.Room;
import com.example.hotel_system.entity.RoomType;
import com.example.hotel_system.enums.RoomStatus;
import com.example.hotel_system.repository.RoomRepository;
import com.example.hotel_system.repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    @Autowired private RoomRepository roomRepository;
    @Autowired private RoomTypeRepository roomTypeRepository;

    // 1. Lấy tất cả phòng
    public Page<RoomResponseDTO> getAllRooms(Pageable pageable) {
        return roomRepository.findAll(pageable).map(this::convertToDTO);
    }

    // 2. Lấy phòng theo ID
    public RoomResponseDTO getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng: " + id));
        return convertToDTO(room);
    }

    // 3. Tạo phòng mới
    public RoomResponseDTO createRoom(RoomRequestDTO request) {
        RoomType type = roomTypeRepository.findById(request.getRoomTypeId())
                .orElseThrow(() -> new RuntimeException("Loại phòng không tồn tại!"));
        Room room = new Room();
        room.setRoomNumber(request.getRoomNumber());
        room.setRoomType(type);
        room.setStatus(RoomStatus.AVAILABLE);
        return convertToDTO(roomRepository.save(room));
    }

    // 4. Cập nhật thông tin phòng
    public RoomResponseDTO updateRoom(Long id, RoomRequestDTO request) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Phòng không tồn tại"));
        RoomType type = roomTypeRepository.findById(request.getRoomTypeId())
                .orElseThrow(() -> new RuntimeException("Loại phòng không tồn tại"));

        room.setRoomNumber(request.getRoomNumber());
        room.setRoomType(type);
        return convertToDTO(roomRepository.save(room));
    }

    // 5. Xóa phòng (Soft Delete hoặc Hard Delete tùy bạn - ở đây làm Hard Delete cơ bản)
    public void deleteRoom(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new RuntimeException("Phòng không tồn tại để xóa");
        }
        roomRepository.deleteById(id);
    }

    // 6. Cập nhật TRẠNG THÁI phòng (Ví dụ: Chuyển sang Dọn dẹp/Bảo trì)
    public RoomResponseDTO updateRoomStatus(Long id, RoomStatus newStatus) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Phòng không tồn tại"));
        room.setStatus(newStatus);
        return convertToDTO(roomRepository.save(room));
    }

    // 7. Lấy danh sách phòng theo Trạng Thái
    public Page<RoomResponseDTO> getRoomsByStatus(RoomStatus status, Pageable pageable) {
        return roomRepository.findByStatus(status, pageable).map(this::convertToDTO);
    }

    // 8. Lấy danh sách phòng theo Loại Phòng
    public Page<RoomResponseDTO> getRoomsByRoomType(Long roomTypeId, Pageable pageable) {
        return roomRepository.findByRoomTypeId(roomTypeId, pageable).map(this::convertToDTO);
    }

    // 9. Đếm số lượng phòng theo trạng thái
    public Long countRoomsByStatus(RoomStatus status) {
        return roomRepository.countByStatus(status);
    }

    // 10. Tìm phòng trống (Logic tìm kiếm theo ngày) -> Trả về List DTO
    public List<RoomResponseDTO> searchAvailableRooms(LocalDate checkIn, LocalDate checkOut) {
        return roomRepository.findAvailableRooms(checkIn, checkOut).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Helper: Convert Entity -> DTO
    private RoomResponseDTO convertToDTO(Room room) {
        RoomResponseDTO dto = new RoomResponseDTO();
        dto.setId(room.getId());
        dto.setRoomNumber(room.getRoomNumber());
        dto.setStatus(room.getStatus().toString());
        dto.setRoomTypeName(room.getRoomType().getName());
        dto.setPrice(room.getRoomType().getBasePrice());
        return dto;
    }
}