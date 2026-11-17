package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

val Ink = Color(0xFF211B34)
val Muted = Color(0xFF777284)
val Accent = Color(0xFF6751BB)
val PageBackground = Color(0xFFF7F5FB)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT)
        )
        setContent { MyApplicationTheme { VisualHierarchyApp() } }
    }
}

@Composable
fun VisualHierarchyApp() {
    // Một Activity, một biến chọn màn hình; không cần thư viện điều hướng.
    var selectedLesson by rememberSaveable { mutableIntStateOf(-1) }
    val homeScroll = rememberLazyListState()
    BackHandler(enabled = selectedLesson >= 0) { selectedLesson = -1 }

    Scaffold(
        containerColor = PageBackground,
        topBar = { AppHeader(selectedLesson) { selectedLesson = -1 } }
    ) { padding ->
        if (selectedLesson < 0) {
            LazyColumn(
                state = homeScroll,
                modifier = Modifier.fillMaxSize().padding(padding).testTag("home"),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(lessons) { index, lesson ->
                    Surface(
                        onClick = { selectedLesson = index }, shape = RoundedCornerShape(18.dp),
                        color = Color.White, border = BorderStroke(1.dp, Color(0xFFECE8F3)),
                        modifier = Modifier.fillMaxWidth().testTag("lesson_$index")
                    ) {
                        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Box(Modifier.size(42.dp).background(Color(0xFFF0EBFA), RoundedCornerShape(12.dp)),
                                contentAlignment = Alignment.Center) {
                                Text("0${index + 1}", color = Accent, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            }
                            Text(lesson.title, fontSize = 16.sp, lineHeight = 22.sp, fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.weight(1f).padding(horizontal = 14.dp))
                            DemoIcon(R.drawable.ic_chevron, tint = Muted, size = 18)
                        }
                    }
                }
            }
        } else {
            // key làm mỗi mục có trạng thái riêng và mở lại từ đầu.
            key(selectedLesson) { LessonScreen(selectedLesson, Modifier.padding(padding)) }
        }
    }
}

@Composable
private fun AppHeader(index: Int, onBack: () -> Unit) {
    Surface(color = Color.White) {
        Row(Modifier.fillMaxWidth().statusBarsPadding().height(64.dp).padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            if (index >= 0) {
                IconButton(onClick = onBack, modifier = Modifier.testTag("back")) {
                    Icon(painterResource(R.drawable.ic_back), contentDescription = "Quay lại", tint = Ink)
                }
            } else {
                Box(Modifier.size(36.dp).background(Accent, RoundedCornerShape(10.dp)), contentAlignment = Alignment.Center) {
                    DemoIcon(R.drawable.ic_layers, Color.White, 21)
                }
            }
            Text("Visual hierarchy", fontSize = 18.sp, fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 12.dp))
        }
    }
}

@Composable
private fun LessonScreen(index: Int, modifier: Modifier = Modifier) {
    var optimized by rememberSaveable { mutableStateOf(false) }
    var colorsOnly by rememberSaveable { mutableStateOf(false) }
    var quantityExample by rememberSaveable { mutableStateOf(false) }
    // Mỗi tab giữ giỏ hàng và vị trí cuộn riêng khi chuyển qua lại.
    val exampleStates = rememberSaveableStateHolder()
    Column(modifier.fillMaxSize()) {
        if (index == 1) {
            ExampleTabs(quantityExample) { quantityExample = it }
        } else {
            ModeSwitch(optimized) { optimized = it }
        }
        exampleStates.SaveableStateProvider(quantityExample) {
            Column(
                Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp).testTag("lesson_content"),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                when (index) {
                    0 -> {
                        if (!optimized) {
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                FilterChip(selected = !colorsOnly, onClick = { colorsOnly = false }, label = { Text("Cùng màu") })
                                FilterChip(selected = colorsOnly, onClick = { colorsOnly = true }, label = { Text("Chỉ đổi màu") },
                                    modifier = Modifier.testTag("colors_only"))
                            }
                        }
                        CartDemo(when { optimized -> CartStyle.Optimized; colorsOnly -> CartStyle.ColorsOnly; else -> CartStyle.Equal })
                    }
                    1 -> {
                        CartDemo(if (quantityExample) CartStyle.LargeQuantity else CartStyle.LargeImages)
                    }
                    2 -> {
                        MetricsDemo(optimized)
                    }
                    3 -> {
                        LawDemo(optimized)
                        ColorActionDemo(optimized)
                    }
                    4 -> {
                        PropertyDemo(optimized)
                    }
                }
            }
        }
    }
}

@Composable
private fun ExampleTabs(quantityExample: Boolean, onChange: (Boolean) -> Unit) {
    Row(Modifier.padding(start = 20.dp, end = 20.dp, top = 14.dp).fillMaxWidth()
        .clip(RoundedCornerShape(14.dp)).background(Color(0xFFEAE6F1)).padding(4.dp).selectableGroup()) {
        listOf("Ảnh quá lớn", "Số lượng nổi bật").forEachIndexed { index, title ->
            val selected = quantityExample == (index == 1)
            Box(Modifier.weight(1f).clip(RoundedCornerShape(10.dp))
                .background(if (selected) Color.White else Color.Transparent)
                .selectable(selected = selected, role = Role.Tab, onClick = { onChange(index == 1) })
                .testTag(if (index == 0) "example_images" else "example_quantity").padding(vertical = 13.dp),
                contentAlignment = Alignment.Center) {
                Text(title, color = if (selected) Accent else Muted, fontSize = 14.sp,
                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal)
            }
        }
    }
}

@Composable
private fun ModeSwitch(optimized: Boolean, onChange: (Boolean) -> Unit) {
    Row(Modifier.padding(start = 20.dp, end = 20.dp, top = 14.dp).fillMaxWidth()
        .clip(RoundedCornerShape(14.dp)).background(Color(0xFFEAE6F1)).padding(4.dp).selectableGroup()) {
        listOf("Chưa tối ưu", "Tối ưu").forEachIndexed { index, title ->
            val selected = optimized == (index == 1)
            Box(Modifier.weight(1f).clip(RoundedCornerShape(10.dp))
                .background(if (selected) Color.White else Color.Transparent)
                .selectable(selected = selected, role = Role.Tab, onClick = { onChange(index == 1) })
                .testTag(if (index == 0) "mode_before" else "mode_after").padding(vertical = 13.dp),
                contentAlignment = Alignment.Center) {
                Text(title, color = if (selected) Accent else Muted, fontSize = 14.sp,
                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 915)
@Composable
private fun HomePreview() { MyApplicationTheme { VisualHierarchyApp() } }
