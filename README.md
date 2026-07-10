# 🏨 Hotel Management System (Spring Boot REST API)

> Hệ thống quản lý khách sạn toàn diện được xây dựng bằng Java Spring Boot. Hỗ trợ quản lý Khách hàng, Phòng ở và Quy trình Đặt phòng từ A-Z.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green)
![Database](https://img.shields.io/badge/Database-MySQL%2FH2-blue)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-brightgreen)

## 📖 Giới thiệu
Dự án cung cấp bộ RESTful API chuẩn nghiệp vụ để vận hành một khách sạn, bao gồm các chức năng cốt lõi:
1.  **Quản lý Khách hàng (Guest):** Lưu trữ thông tin, tích điểm, xóa mềm (Soft Delete).
2.  **Quản lý Phòng (Room):** Quản lý trạng thái, loại phòng, tìm kiếm nâng cao.
3.  **Đặt phòng (Reservation):** Tự động tính tiền, quy trình Check-in/Check-out, ngăn chặn đặt trùng phòng.

## 🛠️ Công nghệ sử dụng
* **Backend:** Java 17, Spring Boot, Spring Data JPA, Hibernate.
* **Database:** MySQL (hoặc H2 Database).
* **API Documentation:** OpenAPI 3.0 (Swagger UI).
* **Tools:** Maven, Lombok, Postman.
* **Frontend (Basic):** HTML5, Bootstrap 5, JavaScript (Fetch API).

---

## ✨ Tính năng nổi bật (Key Features)

### 1. Phân hệ Khách Hàng (Guest Module)
* ✅ **CRUD đầy đủ:** Thêm, sửa, xóa, xem danh sách.
* ✅ **Soft Delete:** Không xóa vĩnh viễn dữ liệu. Khách hàng khi xóa sẽ được đánh dấu `isDeleted = true` để bảo toàn lịch sử giao dịch.
* ✅ **Validation:** Kiểm tra định dạng Email, số điện thoại, ngăn chặn trùng lặp Email.
* ✅ **Pagination:** Hỗ trợ phân trang cho danh sách khách hàng lớn.

### 2. Phân hệ Phòng (Room Module)
* ✅ **Quản lý trạng thái:** Theo dõi trạng thái phòng (`AVAILABLE`, `OCCUPIED`, `MAINTENANCE`).
* ✅ **Tìm kiếm nâng cao:**
    * Tìm phòng trống theo khoảng thời gian (Check-in/Check-out).
    * Lọc phòng theo trạng thái, loại phòng.
* ✅ **Thống kê:** Đếm số lượng phòng theo từng trạng thái (Hỗ trợ Dashboard).
* ✅ **DTO Pattern:** API trả về dữ liệu phẳng, gọn gàng (bao gồm cả giá tiền và tên loại phòng).

### 3. Phân hệ Đặt Phòng (Reservation Module) - ⭐️ Tính năng cao cấp
* ✅ **Auto Pricing:** Hệ thống tự động tính tổng tiền dựa trên: `(Ngày đi - Ngày đến) * Giá phòng`.
* ✅ **Ngăn chặn trùng lặp (Double Booking):** Hệ thống tự động kiểm tra xem phòng có trống trong khoảng thời gian khách chọn hay không trước khi cho phép đặt.
* ✅ **Quy trình Check-in/Check-out (State Machine):**
    * **Check-in:** Chuyển trạng thái đơn sang `CHECKED_IN` -> Phòng tự động chuyển sang `OCCUPIED`.
    * **Check-out:** Chuyển trạng thái đơn sang `CHECKED_OUT` -> Phòng tự động chuyển sang `MAINTENANCE` (chờ dọn dẹp).
* ✅ **Validation Logic:** Chặn đặt phòng ngày quá khứ, ngày check-out phải sau check-in.

---

## 🚀 Cài đặt và Chạy ứng dụng

### Yêu cầu
* JDK 17 trở lên.
* Maven.
* MySQL (hoặc dùng cấu hình mặc định).

### Các bước thực hiện
1.  **Clone dự án:** Tải file zip 
  

2.  **Cấu hình Database (file `application.properties`):**
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/hotel_db
    spring.datasource.username=root
    spring.datasource.password=your_password
    spring.jpa.hibernate.ddl-auto=update
    ```

3.  **Chạy ứng dụng:**
    ```bash
    mvn spring-boot:run
    ```

4.  **Truy cập:**
    * **Swagger UI (Tài liệu API):** `http://localhost:8080/swagger-ui.html`
    * **Frontend Demo:** Mở file `index.html` trong thư mục `Hotel_Frontend`.

---

## 📚 API Endpoints chính

| Method | Endpoint | Mô tả |
| :--- | :--- | :--- |
| **GUEST** | | |
| `POST` | `/api/v1/guests` | Tạo khách hàng mới |
| `GET` | `/api/v1/guests` | Lấy danh sách khách (có phân trang) |
| **ROOM** | | |
| `GET` | `/api/v1/rooms/available` | Tìm phòng trống theo ngày |
| `GET` | `/api/v1/rooms/status/{status}` | Lọc phòng theo trạng thái |
| `GET` | `/api/v1/rooms/count/status/{status}` | Đếm số lượng phòng theo trạng thái |
| **RESERVATION** | | |
| `POST` | `/api/v1/reservations` | Đặt phòng (Tính tiền tự động) |
| `POST` | `/api/v1/reservations/{id}/check-in` | Check-in (Nhận phòng) |
| `POST` | `/api/v1/reservations/{id}/check-out` | Check-out (Trả phòng) |

---
## 📂 Cấu trúc dự án

```text
com.example.hotel_system
 ┣ 📂 config          # Cấu hình (CORS, Swagger...)
 ┣ 📂 controller      # Xử lý API Request (Tiếp nhận yêu cầu)
 ┣ 📂 dto             # Data Transfer Objects (Dữ liệu vào/ra)
 ┣ 📂 entity          # Các thực thể Database (Guest, Room, Reservation)
 ┣ 📂 enums           # Định nghĩa trạng thái (RoomStatus, ReservationStatus)
 ┣ 📂 repository      # Tầng giao tiếp với Database (JPA)
 ┗ 📂 service         # Tầng xử lý Logic nghiệp vụ chính, Swagger...) 
```
---


## 👨‍💻 Tác giả
* **Dev:** Trần Bá Tài
* **Contact:** 22010012@st.phenikaa-uni.edu.vn

*Project được xây dựng với mục đích học tập và nghiên cứu kiến trúc Microservices & REST API.*

# tticonnex
 
