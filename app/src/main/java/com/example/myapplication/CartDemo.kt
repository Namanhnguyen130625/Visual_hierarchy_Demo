package com.example.myapplication

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

val Pink = Color(0xFFF33188)

@Composable
fun DemoIcon(icon: Int, tint: Color, size: Int, modifier: Modifier = Modifier) {
    Icon(painterResource(icon), contentDescription = null, tint = tint, modifier = modifier.size(size.dp))
}

enum class CartStyle { Equal, ColorsOnly, Optimized, LargeImages, LargeQuantity }
private data class Product(val name: String, val cents: Int, val image: Int)
private val products = listOf(
    Product("Avocado", 450, R.drawable.avocado),
    Product("Lime", 420, R.drawable.lime),
    Product("Orange", 250, R.drawable.orange)
)
private fun money(cents: Int) = String.format(Locale.US, "$%.2f", cents / 100.0)

@Composable
fun CartDemo(style: CartStyle) {
    // Dữ liệu ít nên chỉ cần lưu tên các sản phẩm đã xóa.
    var removedNames by rememberSaveable { mutableStateOf(listOf<String>()) }
    var showCheckout by rememberSaveable { mutableStateOf(false) }
    val visibleProducts = products.filter { it.name !in removedNames }
    val total = visibleProducts.sumOf { it.cents }
    val context = LocalContext.current
    val filledRemove = style == CartStyle.Equal || style == CartStyle.ColorsOnly
    val textColor = if (style == CartStyle.Equal) Color(0xFF08172F) else Color(0xFF11350E)
    val remove: (Product) -> Unit = { product -> removedNames = removedNames + product.name }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Surface(shape = RoundedCornerShape(28.dp), color = Color.White, shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth().testTag("cart_${style.name}")) {
            Column(Modifier.padding(24.dp)) {
                Text("Shopping cart", fontSize = 22.sp, fontWeight = FontWeight.Medium, color = textColor,
                    modifier = Modifier.padding(top = 6.dp, bottom = 20.dp))
                visibleProducts.forEach { product ->
                    Row(Modifier.fillMaxWidth().padding(vertical = 15.dp), verticalAlignment = Alignment.CenterVertically) {
                        Image(painterResource(product.image), contentDescription = product.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(if (style == CartStyle.LargeImages) 94.dp else 56.dp).clip(CircleShape))
                        Column(Modifier.weight(1f).padding(start = if (style == CartStyle.LargeImages) 12.dp else 14.dp, end = 4.dp)) {
                            Text(product.name, color = if (style == CartStyle.LargeQuantity) Color(0xFF888888) else textColor,
                                fontSize = 16.sp, fontWeight = if (style == CartStyle.LargeQuantity) FontWeight.Normal else FontWeight.Medium)
                            Text("1 piece", color = if (style == CartStyle.LargeQuantity) Color.Black else Color(0xFF777777),
                                fontSize = if (style == CartStyle.LargeQuantity) 18.sp else 14.sp,
                                fontWeight = if (style == CartStyle.LargeQuantity) FontWeight.Medium else FontWeight.Normal,
                                modifier = Modifier.padding(top = 3.dp))
                            if (filledRemove) {
                                Button(onClick = { remove(product) }, shape = RoundedCornerShape(4.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = if (style == CartStyle.Equal) Color(0xFF18032F) else Color(0xFFEE4B83)),
                                    contentPadding = PaddingValues(horizontal = 10.dp),
                                    modifier = Modifier.padding(top = 5.dp).height(32.dp).testTag("remove_${product.name}")) {
                                    Text("Remove", fontSize = 14.sp, fontWeight = FontWeight.Normal)
                                }
                            } else {
                                TextButton(onClick = { remove(product) }, contentPadding = PaddingValues(0.dp),
                                    modifier = Modifier.height(34.dp).testTag("remove_${product.name}")) {
                                    Text("Remove", color = Pink, fontSize = 13.sp, textDecoration = TextDecoration.Underline)
                                }
                            }
                        }
                        Text(money(product.cents), fontSize = 16.sp, fontWeight = FontWeight.Medium, color = textColor)
                    }
                    HorizontalDivider(color = Color(0xFFF0F0F0))
                }
                if (visibleProducts.isEmpty()) {
                    Text("Your cart is empty.", color = Muted, fontSize = 15.sp, modifier = Modifier.padding(vertical = 32.dp))
                }
                Row(Modifier.fillMaxWidth().padding(top = 24.dp, bottom = 28.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("Total amount", color = Color(0xFF555555), fontSize = 16.sp, modifier = Modifier.weight(1f))
                    Text(money(total), color = Color(0xFF003C2D), fontSize = 17.sp,
                        fontWeight = FontWeight.Medium, modifier = Modifier.testTag("cart_total"))
                }
                val continueColor = when (style) {
                    CartStyle.Equal -> Color(0xFF18032F)
                    CartStyle.ColorsOnly -> Color(0xFF009CC7)
                    else -> Color(0xFFEDEFEF)
                }
                Button(
                    onClick = { Toast.makeText(context, "Tiếp tục mua sắm", Toast.LENGTH_SHORT).show() },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = continueColor,
                        contentColor = if (filledRemove) Color.White else Color(0xFF003C2D)),
                    contentPadding = PaddingValues(8.dp), modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp)
                ) { Text("Continue shopping", fontSize = 16.sp, fontWeight = FontWeight.Normal) }
                Spacer(Modifier.height(14.dp))
                Button(onClick = { showCheckout = true }, enabled = visibleProducts.isNotEmpty(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = when (style) {
                        CartStyle.Equal -> Color(0xFF18032F)
                        CartStyle.ColorsOnly -> Color(0xFF00A328)
                        else -> Color(0xFF159E00)
                    }), contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp).testTag("checkout")) {
                    Text("Checkout", fontSize = 16.sp, fontWeight = FontWeight.Normal)
                }
            }
        }
        TextButton(onClick = { removedNames = emptyList() }, modifier = Modifier.align(Alignment.End).testTag("reset_cart")) {
            Text("Khôi phục", fontSize = 13.sp)
        }
    }
    if (showCheckout) {
        AlertDialog(onDismissRequest = { showCheckout = false }, title = { Text("Thanh toán") },
            text = { Text("Tổng tiền: ${money(total)}") },
            confirmButton = { TextButton(onClick = { showCheckout = false }) { Text("Đóng") } })
    }
}
