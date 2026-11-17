package com.example.myapplication

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Navy = Color(0xFF0C083E)
private val PropertyGray = Color(0xFF827E96)

@Composable
fun LawDemo(optimized: Boolean) {
    Surface(shape = RoundedCornerShape(26.dp), color = Color.White, shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth().testTag("law_card")) {
        Column(Modifier.padding(28.dp)) {
            Box(Modifier.width(106.dp).height(62.dp)) {
                Box(Modifier.size(58.dp).background(Color(0xFFE3E2EC), CircleShape), contentAlignment = Alignment.Center) {
                    DemoIcon(R.drawable.ic_scale, Color(0xFF10144A), 34)
                }
                Box(Modifier.padding(start = 48.dp).size(58.dp).background(Color(0xFFE5ECE3), CircleShape), contentAlignment = Alignment.Center) {
                    DemoIcon(R.drawable.ic_gavel, Color(0xFF14480C), 32)
                }
            }
            Text("Personal Injury Law", color = if (optimized) Color.Black else Color(0xFF858585),
                fontSize = 21.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(top = 22.dp, bottom = 24.dp))
            Text("Attorneys in this field handle cases related to accidents, injuries, and wrongful deaths, seeking compensation for victims",
                color = if (optimized) Color(0xFF858585) else Color.Black, fontSize = 17.sp, lineHeight = 29.sp,
                modifier = Modifier.testTag("law_description"))
        }
    }
}

@Composable
fun ColorActionDemo(optimized: Boolean) {
    val context = LocalContext.current
    Button(onClick = { Toast.makeText(context, "Liên hệ", Toast.LENGTH_SHORT).show() },
        shape = RoundedCornerShape(10.dp), modifier = Modifier.fillMaxWidth().heightIn(min = 50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = if (optimized) Color(0xFF2166E8) else Color(0xFFE5E5E8),
            contentColor = if (optimized) Color.White else Color(0xFF676773))) {
        Text("Contact us", fontSize = 16.sp)
    }
}

@Composable
fun PropertyDemo(optimized: Boolean) {
    Surface(shape = RoundedCornerShape(20.dp), color = Color.White, shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth().testTag("property_card")) {
        Column {
            Image(painterResource(R.drawable.villa), contentDescription = "Luxury 5-bed Villa",
                contentScale = ContentScale.Crop, modifier = Modifier.fillMaxWidth().aspectRatio(1.65f))
            Column(Modifier.padding(22.dp)) {
                if (optimized) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Sách dùng hai giá khác nhau; giữ nguyên theo từng ảnh.
                        Text("£3,273,279", color = Navy, fontSize = 29.sp, fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f).testTag("property_price"))
                        Text("For sale", color = Color(0xFF9C7200), fontSize = 13.sp, fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.background(Color(0xFFFAF2E5), CircleShape).padding(12.dp, 9.dp))
                    }
                    Text("Luxury 5-bed Villa", color = Navy, fontSize = 20.sp, fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = 16.dp, bottom = 18.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        DemoIcon(R.drawable.ic_location, PropertyGray, 19)
                        Text("29 Terrace Rd, BH2 5EL", color = PropertyGray, fontSize = 14.sp,
                            modifier = Modifier.padding(start = 8.dp))
                    }
                    HorizontalDivider(Modifier.padding(vertical = 22.dp), color = Color(0xFFE0DEE8))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        PropertyFeature(R.drawable.ic_ruler, "1634", "sqft")
                        PropertyFeature(R.drawable.ic_bed, "5", "bed.")
                        PropertyFeature(R.drawable.ic_shower, "3", "bath.")
                    }
                    HorizontalDivider(Modifier.padding(vertical = 22.dp), color = Color(0xFFE0DEE8))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(painterResource(R.drawable.jennifer), contentDescription = "Jennifer Bloom",
                            contentScale = ContentScale.Crop, alignment = Alignment.TopCenter,
                            modifier = Modifier.size(48.dp).clip(CircleShape))
                        Column(Modifier.padding(start = 12.dp)) {
                            Text("Jennifer Bloom", color = Navy, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                            Text("+44 235 123 321", color = PropertyGray, fontSize = 14.sp, modifier = Modifier.padding(top = 7.dp))
                        }
                    }
                } else {
                    Text("Luxury 5-Bed Villa", color = Navy, fontSize = 19.sp, fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = 4.dp, bottom = 20.dp))
                    PropertyLine("Type:", "For sale")
                    PropertyLine("Price:", "£1,273,279")
                    PropertyLine("Location:", "29 Terrace Rd, BH2 5EL")
                    PropertyLine("Size:", "1634 sqft")
                    PropertyLine("Bedrooms:", "5")
                    PropertyLine("Bathrooms:", "3")
                    HorizontalDivider(Modifier.padding(top = 5.dp, bottom = 15.dp), color = Color(0xFFE0DEE8))
                    PropertyLine("Contact person:", "Jennifer Bloom")
                    PropertyLine("Phone number:", "+44 235 123 321")
                }
            }
        }
    }
}

@Composable
private fun PropertyLine(label: String, value: String) {
    Row(Modifier.fillMaxWidth().padding(bottom = 16.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(label, color = Navy, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
        Text(value, color = Color(0xFF757184), fontSize = 14.sp, modifier = Modifier.weight(1.15f))
    }
}

@Composable
private fun PropertyFeature(icon: Int, value: String, unit: String) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        DemoIcon(icon, PropertyGray, 18)
        Text(value, color = Navy, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        Text(unit, color = PropertyGray, fontSize = 13.sp)
    }
}
