package com.example.myapplication.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Màu cố định giúp các mẫu giống sách, kể cả khi máy bật dark mode.
@Composable
fun MyApplicationTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF6751BB),
            onPrimary = Color.White,
            background = Color(0xFFF7F5FB),
            surface = Color.White,
            onSurface = Color(0xFF211B34),
            onBackground = Color(0xFF211B34)
        ),
        typography = Typography,
        content = content
    )
}
