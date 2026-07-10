package com.example.hotel_system.repository;

import com.example.hotel_system.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    // Tìm khách hàng theo email (để check trùng lặp)
    Optional<Guest> findByEmail(String email);

    // Tìm theo số điện thoại (nếu cần)
    Optional<Guest> findByPhone(String phone);

    // --- MỚI: Chỉ lấy danh sách khách đang hoạt động (chưa bị xóa mềm) ---
    // Cần đảm bảo Entity Guest đã có trường isDeleted nhé!
    @Query("SELECT g FROM Guest g WHERE g.isDeleted = false")
    List<Guest> findAllActiveGuests();
}