package com.example.hotel_system.repository;

import com.example.hotel_system.entity.Room;
import com.example.hotel_system.enums.RoomStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    // 1. Tìm phòng trống theo ngày (Logic cũ giữ nguyên)
    @Query("SELECT r FROM Room r WHERE r.id NOT IN (" +
            "  SELECT res.room.id FROM Reservation res " +
            "  WHERE res.status != 'CANCELLED' " +
            "  AND ((res.checkInDate < :checkOut) AND (res.checkOutDate > :checkIn))" +
            ")")
    List<Room> findAvailableRooms(@Param("checkIn") LocalDate checkIn,
                                  @Param("checkOut") LocalDate checkOut);

    // 2. Tìm theo trạng thái (Có phân trang)
    Page<Room> findByStatus(RoomStatus status, Pageable pageable);

    // 3. Tìm theo loại phòng (Có phân trang)
    Page<Room> findByRoomTypeId(Long roomTypeId, Pageable pageable);

    // 4. Đếm số lượng phòng theo trạng thái (Dùng cho báo cáo Dashboard)
    long countByStatus(RoomStatus status);
}