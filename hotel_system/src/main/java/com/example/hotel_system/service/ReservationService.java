package com.example.hotel_system.service;

import com.example.hotel_system.dto.ReservationDTO;
import com.example.hotel_system.dto.ReservationResponseDTO;
import com.example.hotel_system.entity.*;
import com.example.hotel_system.enums.ReservationStatus;
import com.example.hotel_system.enums.RoomStatus; // Import thêm Enum RoomStatus
import com.example.hotel_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import để quản lý giao dịch

import java.math.BigDecimal; // Import để tính tiền
import java.time.temporal.ChronoUnit; // Import để tính ngày
import java.util.List;
import java.util.UUID;

@Service
public class ReservationService {
    @Autowired private ReservationRepository reservationRepo;
    @Autowired private GuestRepository guestRepo;
    @Autowired private RoomRepository roomRepo;

    // --- 1. TẠO ĐẶT PHÒNG (Có thêm tính tiền tự động) ---
    public ReservationResponseDTO createReservation(ReservationDTO request) {
        // Tìm Guest và Room
        Guest guest = guestRepo.findById(request.getGuestId())
                .orElseThrow(() -> new RuntimeException("Guest not found"));
        Room room = roomRepo.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // Kiểm tra phòng trống
        List<Room> availableRooms = roomRepo.findAvailableRooms(
                request.getCheckInDate(), request.getCheckOutDate());

        boolean isAvailable = availableRooms.stream()
                .anyMatch(r -> r.getId().equals(room.getId()));

        if (!isAvailable) {
            throw new RuntimeException("Room is not available for selected dates!");
        }

        // Khởi tạo Reservation
        Reservation reservation = new Reservation();
        reservation.setGuest(guest);
        reservation.setRoom(room);
        reservation.setCheckInDate(request.getCheckInDate());
        reservation.setCheckOutDate(request.getCheckOutDate());
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setConfirmationNumber("HTL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        // === LOGIC MỚI: TÍNH TỔNG TIỀN TỰ ĐỘNG ===
        long days = ChronoUnit.DAYS.between(request.getCheckInDate(), request.getCheckOutDate());
        if (days < 1) days = 1; // Khách ở ít nhất 1 đêm

        BigDecimal pricePerNight = room.getRoomType().getBasePrice();
        BigDecimal total = pricePerNight.multiply(BigDecimal.valueOf(days));

        // Lưu ý: Đảm bảo bạn đã thêm field totalPrice vào Entity Reservation như hướng dẫn trước
        reservation.setTotalPrice(total);

        // Lưu vào DB
        Reservation savedReservation = reservationRepo.save(reservation);
        return convertToDTO(savedReservation);
    }

    // --- 2. CHECK-IN (Khách đến nhận phòng) ---
    @Transactional // Quan trọng: Nếu lỗi thì rollback cả 2 hành động
    public void checkInGuest(Long reservationId) {
        Reservation res = reservationRepo.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if (res.getStatus() != ReservationStatus.CONFIRMED) {
            throw new RuntimeException("Only CONFIRMED reservations can be checked in!");
        }

        // Cập nhật trạng thái đặt phòng
        res.setStatus(ReservationStatus.CHECKED_IN);

        // Cập nhật trạng thái phòng -> ĐANG CÓ KHÁCH
        Room room = res.getRoom();
        room.setStatus(RoomStatus.OCCUPIED);

        reservationRepo.save(res);
        roomRepo.save(room);
    }

    // --- 3. CHECK-OUT (Khách trả phòng) ---
    @Transactional
    public void checkOutGuest(Long reservationId) {
        Reservation res = reservationRepo.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        // Cập nhật trạng thái đặt phòng
        res.setStatus(ReservationStatus.CHECKED_OUT);

        // Cập nhật trạng thái phòng -> CẦN BẢO TRÌ/DỌN DẸP (Chưa available ngay)
        Room room = res.getRoom();
        room.setStatus(RoomStatus.MAINTENANCE);

        reservationRepo.save(res);
        roomRepo.save(room);
    }

    // --- Helper: Chuyển Entity sang DTO ---
    private ReservationResponseDTO convertToDTO(Reservation res) {
        ReservationResponseDTO dto = new ReservationResponseDTO();
        dto.setId(res.getId());
        dto.setConfirmationNumber(res.getConfirmationNumber());
        dto.setGuestName(res.getGuest().getFirstName() + " " + res.getGuest().getLastName());
        dto.setRoomNumber(res.getRoom().getRoomNumber());
        dto.setCheckInDate(res.getCheckInDate());
        dto.setCheckOutDate(res.getCheckOutDate());
        dto.setStatus(res.getStatus().toString());

        // Trả về tổng tiền cho Frontend xem
        dto.setTotalPrice(res.getTotalPrice());

        return dto;
    }
}