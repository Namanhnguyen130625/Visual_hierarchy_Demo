package com.example.myapplication

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MetricsDemo(optimized: Boolean) {
    // Giữ ba thẻ nằm ngang như hình sách; điện thoại có thể vuốt ngang.
    Row(Modifier.horizontalScroll(rememberScrollState()).testTag("metrics"), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        MetricCard("Earnings", "$4,981.00", R.drawable.ic_message, Color(0xFF38BF88), optimized)
        MetricCard("Sales", "591", R.drawable.ic_sales, Pink, optimized)
        MetricCard("Views", if (optimized) "2,121" else "2121", R.drawable.ic_clock, Color(0xFFFA9315), optimized)
    }
}

@Composable
private fun MetricCard(label: String, value: String, icon: Int, iconColor: Color, optimized: Boolean) {
    Box(Modifier.width(300.dp).height(205.dp).clip(RoundedCornerShape(26.dp)).background(Color(0xFF201D2F))) {
        Column(Modifier.padding(22.dp)) {
            Row(verticalAlignment = Alignment.Top) {
                Column(Modifier.weight(1f)) {
                    Text(label, color = if (optimized) Color(0xFFD0CED4) else Color.White,
                        fontSize = if (optimized) 16.sp else 30.sp,
                        fontWeight = if (optimized) FontWeight.Normal else FontWeight.SemiBold)
                    Row(Modifier.padding(top = 7.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(value, color = if (optimized) Color.White else Color(0xFFD0CED4),
                            fontSize = if (optimized) 28.sp else 17.sp,
                            fontWeight = if (optimized) FontWeight.SemiBold else FontWeight.Normal,
                            modifier = Modifier.testTag("metric_$label"))
                        if (label == "Earnings") {
                            Text(" ↗ 3.2%", color = Color(0xFF36C58C), fontSize = if (optimized) 14.sp else 15.sp)
                        }
                    }
                }
                Box(Modifier.size(26.dp).background(iconColor, CircleShape), contentAlignment = Alignment.Center) {
                    DemoIcon(icon, Color.White, 16)
                }
            }
        }
        TrendLine(label == "Sales", Modifier.fillMaxWidth().height(88.dp).align(Alignment.BottomCenter).padding(bottom = 14.dp))
    }
}

@Composable
private fun TrendLine(dip: Boolean, modifier: Modifier) {
    Canvas(modifier.semantics { contentDescription = "Illustrative trend chart" }) {
        // Tọa độ 0..1 giúp biểu đồ co theo kích thước thẻ.
        val path = Path()
        fun line(x: Float, y: Float) = path.lineTo(size.width * x, size.height * y)
        fun curve(x1: Float, y1: Float, x2: Float, y2: Float, x3: Float, y3: Float) =
            path.cubicTo(size.width*x1, size.height*y1, size.width*x2, size.height*y2, size.width*x3, size.height*y3)
        if (dip) {
            path.moveTo(0f, size.height * .32f)
            line(.06f,.32f); curve(.12f,.32f,.1f,.08f,.16f,.12f)
            curve(.22f,.14f,.18f,.62f,.28f,.5f); curve(.36f,.32f,.36f,.86f,.43f,.8f)
            curve(.5f,.72f,.47f,1f,.55f,.92f); curve(.63f,.45f,.65f,1f,.72f,.64f)
            curve(.76f,.2f,.77f,.72f,.8f,.38f); curve(.82f,.08f,.85f,.14f,.88f,.23f)
            curve(.94f,.36f,.95f,.02f,1f,.03f)
        } else {
            path.moveTo(0f, size.height * .94f)
            line(.05f,.94f); curve(.1f,.94f,.08f,.63f,.16f,.66f)
            curve(.22f,.7f,.22f,.35f,.28f,.4f); curve(.33f,.4f,.31f,.84f,.38f,.67f)
            curve(.41f,.64f,.37f,.14f,.44f,.31f); curve(.49f,.5f,.46f,.07f,.54f,.13f)
            curve(.61f,.36f,.6f,-.02f,.66f,.06f); curve(.72f,.34f,.71f,.08f,.78f,.34f)
            curve(.84f,.68f,.87f,.19f,.91f,.43f); curve(.94f,.48f,.92f,.84f,.98f,.62f)
            line(1f,.6f)
        }
        drawPath(path, color = Color.White, style = Stroke(width = 1.7.dp.toPx()))
    }
}
