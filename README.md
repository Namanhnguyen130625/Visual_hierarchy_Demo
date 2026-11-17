# Visual hierarchy

Ứng dụng Kotlin + Jetpack Compose mô phỏng phần **Visual hierarchy.

## Mở và chạy

1. Trong Android Studio, chọn **Open** và mở thư mục `Demo` này.
2. Đợi Gradle Sync hoàn tất. Giữ Gradle JDK là **Embedded JDK** của Android Studio.
3. Chọn máy ảo hoặc điện thoại Android 7.0 trở lên (API 24), rồi nhấn **Run app**.

## Các mục demo

| Mục | Trang in | Cách trình bày |
| --- | --- | --- |
| Ví dụ đầu | Chưa tối ưu: chọn Cùng màu hoặc Chỉ đổi màu. Tối ưu: Checkout nổi bật, Continue shopping nền xám, Remove dạng liên kết. |
| Tránh ưu tiên thông tin không quan trọng có tab riêng: Ảnh quá lớn và Số lượng nổi bật. Mỗi tab chỉ hiện một mẫu, giữ giỏ hàng và vị trí cuộn riêng. |
| Ưu tiên thông tin quan trọng. Chuyển giữa nhãn lớn và giá trị lớn. Vuốt ngang để xem Earnings, Sales, Views. |
| Dùng màu sắc để thể hiện thứ bậc và mức độ quan trọng Đảo độ đậm giữa tiêu đề và mô tả. Nút Contact us là phần minh họa thêm cho ý dùng màu xanh ở cuối trang sách. |
| Dùng kích thước và dấu hiệu trực quan để phân biệt thông tin Chuyển từ các dòng nhãn/giá trị sang giá lớn, nhãn For sale, biểu tượng và ảnh người liên hệ. |

## Đọc code

- `MainActivity.kt`: màn hình chính, chọn bài, chuyển Chưa tối ưu/Tối ưu, nút quay lại.
- `Lessons.kt`: tên 5 mục hiển thị trên màn hình chính.
- `CartDemo.kt`: các kiểu giỏ hàng; dữ liệu, xóa sản phẩm, tính tổng và khôi phục.
- `MetricsDemo.kt`: ba thẻ chỉ số và biểu đồ minh họa bằng Canvas.
- `InfoDemos.kt`: thẻ dịch vụ luật và thẻ bất động sản.
- `ui/theme/Theme.kt`: màu giao diện cố định để việc so sánh không bị đổi theo màu hệ thống.
- `res/drawable-nodpi`: ảnh trái cây, ngôi nhà và người liên hệ lấy từ tài liệu người dùng cung cấp.
- `res/drawable/ic_*.xml`: biểu tượng vector đơn giản.
