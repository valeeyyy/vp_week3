package com.valerie.vp_week3.ui.view

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Coffee
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale

fun formatRpInline(amount: Int): String {
    return "Rp ${NumberFormat.getNumberInstance(Locale("id", "ID")).format(amount)}"
}

@Composable
fun Soal1Bonus() {
    var quantity by remember { mutableIntStateOf(3) }
    var isLarge by remember { mutableStateOf(false) }
    var context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF3E0).copy(alpha = 0.5f))
            .padding(horizontal = 24.dp, vertical = 42.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Kopi Kenangan Senja",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF441F0E)
            )
            Text(
                text = "☕",
                fontSize = 22.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(2.dp, Color(0xFFF5D9B4))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF7A3616)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Coffee,
                        contentDescription = "Coffee",
                        tint = Color(0xFFE3BA8F),
                        modifier = Modifier.size(72.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Caramel Latte",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF441F0E)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Espresso shot, steamed milk & caramel syrup",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF835336),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Rp 25.000 / cup",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD85827)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "PILIHAN UKURAN:",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF441F0E)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        1.5.dp,
                        if (!isLarge) Color(0xFFD85827) else Color(0xFFF5D9B4),
                        RoundedCornerShape(12.dp)
                    )
                    .background(if (!isLarge) Color.White else Color(0xFFFCF0E2))
                    .clickable { isLarge = false }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Regular (+0)",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (!isLarge) Color(0xFFD85827) else Color(0xFF835336)
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        1.5.dp,
                        if (isLarge) Color(0xFFD85827) else Color(0xFFF5D9B4),
                        RoundedCornerShape(12.dp)
                    )
                    .background(if (isLarge) Color.White else Color(0xFFFCF0E2))
                    .clickable { isLarge = true }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Large (+6k)",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isLarge) Color(0xFFD85827) else Color(0xFF835336)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, Color(0xFFF5D9B4), RoundedCornerShape(12.dp))
                .background(Color.White, RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Jumlah Pesanan:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF441F0E)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .border(2.dp, Color(0xFFD4C8C1), RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .clickable { if (quantity > 1) quantity-- },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("-", fontSize = 24.sp, color = Color(0xFF441F0E), fontWeight = FontWeight.Medium)
                    }

                    Text(
                        text = "$quantity",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF441F0E)
                    )

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .border(2.dp, Color(0xFFD4C8C1), RoundedCornerShape(12.dp))
                            .background(Color(0xFFFDE1B9))
                            .clickable { quantity++ },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("+", fontSize = 24.sp, color = Color(0xFFD85827), fontWeight = FontWeight.Medium)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawRoundRect(
                        color = Color(0xFFF5D9B4),
                        style = Stroke(
                            width = 6f,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f), 0f)
                        ),
                        cornerRadius = CornerRadius(12.dp.toPx())
                    )
                }
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Subtotal:", fontSize = 16.sp, fontWeight = FontWeight.Normal, color = Color(0xFF835336))
                    Text(formatRpInline((if (isLarge) 31000 else 25000) * quantity), fontSize = 16.sp, fontWeight = FontWeight.Normal, color = Color(0xFF835336))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Pajak Resto (10%):", fontSize = 16.sp, fontWeight = FontWeight.Normal, color = Color(0xFF835336))
                    Text(formatRpInline((((if (isLarge) 31000 else 25000) * quantity) * 0.10).toInt()), fontSize = 16.sp, fontWeight = FontWeight.Normal, color = Color(0xFF835336))
                }
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = Color(0xFFF5D9B4), thickness = 1.dp)
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total Tagihan:", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF441F0E))
                    Text(formatRpInline(((if (isLarge) 31000 else 25000) * quantity) + (((if (isLarge) 31000 else 25000) * quantity) * 0.10).toInt()), fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD85827))
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Add to cart button
        Button(
            onClick = {
                Toast.makeText(context, "Pesanan ditambahkan ke keranjang!", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD85827)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("TAMBAH KE KERANJANG", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Soal1BonusPreview() {
    Soal1Bonus()
}
