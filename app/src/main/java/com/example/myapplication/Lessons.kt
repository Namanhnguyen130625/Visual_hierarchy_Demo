package com.example.myapplication

// Chỉ giữ tên để hiển thị trên màn hình chính.
data class Lesson(val title: String)

val lessons = listOf(
    Lesson("Ví dụ đầu"),
    Lesson("Tránh ưu tiên thông tin không quan trọng"),
    Lesson("Ưu tiên thông tin quan trọng"),
    Lesson("Dùng màu sắc để thể hiện thứ bậc và mức độ quan trọng"),
    Lesson("Dùng kích thước và dấu hiệu trực quan để phân biệt thông tin")
)
