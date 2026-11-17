# Visual hierarchy

Ứng dụng Kotlin + Jetpack Compose mô phỏng phần **Visual hierarchy, trang in 6–11** của UI/UX Playbook (tương ứng trang 7–12 trong trình đọc PDF).

## Mở và chạy

1. Trong Android Studio, chọn **Open** và mở thư mục `Demo` này.
2. Đợi Gradle Sync hoàn tất. Giữ Gradle JDK là **Embedded JDK** của Android Studio.
3. Chọn máy ảo hoặc điện thoại Android 7.0 trở lên (API 24), rồi nhấn **Run app**.

Project giữ nguyên phiên bản Gradle, Android Gradle Plugin, SDK và Compose trong project ban đầu. Không thêm thư viện bên ngoài. Lần đầu đồng bộ/build có thể cần Internet; app chạy offline.

## Các mục demo

| Mục | Trang in | Cách trình bày |
| --- | --- | --- |
| Ví dụ đầu | 6–7 | Chưa tối ưu: chọn Cùng màu hoặc Chỉ đổi màu. Tối ưu: Checkout nổi bật, Continue shopping nền xám, Remove dạng liên kết. |
| Tránh ưu tiên thông tin không quan trọng | 8 | Hai tab riêng: Ảnh quá lớn và Số lượng nổi bật. Mỗi tab chỉ hiện một mẫu, giữ giỏ hàng và vị trí cuộn riêng. |
| Ưu tiên thông tin quan trọng | 9 | Chuyển giữa nhãn lớn và giá trị lớn. Vuốt ngang để xem Earnings, Sales, Views. |
| Dùng màu sắc để thể hiện thứ bậc và mức độ quan trọng | 10 | Đảo độ đậm giữa tiêu đề và mô tả. Nút Contact us là phần minh họa thêm cho ý dùng màu xanh ở cuối trang sách. |
| Dùng kích thước và dấu hiệu trực quan để phân biệt thông tin | 11 | Chuyển từ các dòng nhãn/giá trị sang giá lớn, nhãn For sale, biểu tượng và ảnh người liên hệ. |

Giao diện tối giản: tiêu đề “Visual hierarchy”, danh sách 5 mục, nút chuyển chế độ và các mẫu giao diện. Không hiển thị số trang, nhãn DEMO, phần giới thiệu, chú thích hoặc các đoạn giải thích. Nội dung các mẫu giữ tiếng Anh; các nút điều khiển app bằng tiếng Việt.

## Thao tác khi thuyết trình

- **Remove** xóa sản phẩm và tính lại tổng tiền.
- **Khôi phục** đưa cả ba sản phẩm về trạng thái ban đầu.
- **Checkout** mở hộp thoại tổng tiền; khi giỏ trống, nút bị vô hiệu hóa.
- **Continue shopping** và **Contact us** hiện thông báo ngắn.
- Nút quay lại của app và nút Back của Android đều trở về danh sách.
- Trạng thái đang xem, chế độ so sánh và giỏ hàng được giữ khi Activity được tạo lại, ví dụ khi xoay máy.

## Đọc code

- `MainActivity.kt`: màn hình chính, chọn bài, chuyển Chưa tối ưu/Tối ưu, nút quay lại.
- `Lessons.kt`: tên 5 mục hiển thị trên màn hình chính.
- `CartDemo.kt`: các kiểu giỏ hàng; dữ liệu, xóa sản phẩm, tính tổng và khôi phục.
- `MetricsDemo.kt`: ba thẻ chỉ số và biểu đồ minh họa bằng Canvas.
- `InfoDemos.kt`: thẻ dịch vụ luật và thẻ bất động sản.
- `ui/theme/Theme.kt`: màu giao diện cố định để việc so sánh không bị đổi theo màu hệ thống.
- `res/drawable-nodpi`: ảnh trái cây, ngôi nhà và người liên hệ lấy từ tài liệu người dùng cung cấp.
- `res/drawable/ic_*.xml`: biểu tượng vector đơn giản.

Không sử dụng ViewModel, cơ sở dữ liệu, API hay thư viện Navigation. Mỗi ví dụ là một hàm `@Composable`; các thuộc tính màu, cỡ chữ, cỡ ảnh thay đổi theo trạng thái Boolean hoặc `CartStyle`.

## Chi tiết bám theo nguồn

- Ảnh minh họa lấy từ PDF và thư mục `Ảnh`; chữ, nút và bố cục được dựng lại bằng Compose, điều chỉnh kích thước cho điện thoại.
- Giữ đúng dữ liệu trong từng hình. Hai mẫu bất động sản của sách có giá khác nhau: **£1,273,279** và **£3,273,279**.
- Giữ cả hai cách hiển thị Views: **2121** ở mẫu chưa tối ưu và **2,121** ở mẫu tối ưu.
- Biểu đồ và biểu tượng được vẽ lại gần với hình tham chiếu; không phải dữ liệu kinh doanh thực.

## Kiểm tra bằng dòng lệnh

```powershell
.\gradlew.bat :app:assembleDebug :app:lintDebug :app:testDebugUnitTest
# Khi đã bật máy ảo hoặc kết nối điện thoại:
.\gradlew.bat :app:connectedDebugAndroidTest
```

APK debug được tạo tại `app/build/outputs/apk/debug/app-debug.apk`.

Bản APK đã build sẵn: `output/VisualHierarchy.apk`. Ảnh xem nhanh: `output/preview.png`; ảnh chụp từng mẫu: `output/screenshots`.

Đã kiểm tra ngày 17/11/2026 trên máy ảo Medium Phone API 37: build thành công, lint không có lỗi (chỉ còn ba thông báo phiên bản thư viện mới), 5 kiểm thử chức năng trong `VisualHierarchyTest` đều đạt. Đã xem ảnh chụp các mẫu để kiểm tra bố cục. Chưa kiểm tra trên điện thoại vật lý.
