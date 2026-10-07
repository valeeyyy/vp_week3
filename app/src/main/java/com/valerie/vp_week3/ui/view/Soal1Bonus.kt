package com.valerie.vp_week3.ui.view

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
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

@Composable
fun Soal1Bonus() {
    var quantity by remember { mutableIntStateOf(3) }
    var isLarge by remember { mutableStateOf(false) }

    val basePrice = if (isLarge) 31000 else 25000
    val subtotal = basePrice * quantity
    val tax = (subtotal * 0.10).toInt()
    val total = subtotal + tax
    val context = LocalContext.current

    val primaryOrange = Color(0xFFD85827)
    val darkBrown = Color(0xFF441F0E)
    val brownBg = Color(0xFF7A3616)
    val lightBrown = Color(0xFF835336)
    val beigeBorder = Color(0xFFF5D9B4)
    val beigeBg = Color(0xFFFCF0E2)
    val plusBg = Color(0xFFFDE1B9)
    val minusBorder = Color(0xFFD4C8C1)
    val coffeeIconColor = Color(0xFFE3BA8F)

    val formatRp = { amount: Int ->
        val formatter = NumberFormat.getNumberInstance(Locale("id", "ID"))
        "Rp ${formatter.format(amount)}"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Kopi Kenangan Senja",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = darkBrown
            )
            Text(
                text = "☕",
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, beigeBorder)
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
                        .background(brownBg),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(72.dp)) {
                        val strokeWidth = 10f
                        drawRoundRect(
                            color = coffeeIconColor,
                            topLeft = Offset(size.width * 0.65f, size.height * 0.4f),
                            size = Size(size.width * 0.25f, size.height * 0.35f),
                            cornerRadius = CornerRadius(16f, 16f),
                            style = Stroke(width = strokeWidth)
                        )
                        drawRoundRect(
                            color = brownBg,
                            topLeft = Offset(size.width * 0.15f, size.height * 0.3f),
                            size = Size(size.width * 0.6f, size.height * 0.55f),
                            cornerRadius = CornerRadius(16f, 16f)
                        )
                        drawRoundRect(
                            color = coffeeIconColor,
                            topLeft = Offset(size.width * 0.15f, size.height * 0.3f),
                            size = Size(size.width * 0.6f, size.height * 0.55f),
                            cornerRadius = CornerRadius(16f, 16f),
                            style = Stroke(width = strokeWidth)
                        )
                        drawRoundRect(
                            color = coffeeIconColor,
                            topLeft = Offset(size.width * 0.35f, size.height * 0.1f),
                            size = Size(size.width * 0.05f, size.height * 0.15f),
                            cornerRadius = CornerRadius(8f, 8f),
                        )
                        drawRoundRect(
                            color = coffeeIconColor,
                            topLeft = Offset(size.width * 0.45f, size.height * 0.1f),
                            size = Size(size.width * 0.05f, size.height * 0.15f),
                            cornerRadius = CornerRadius(8f, 8f),
                        )
                        drawRoundRect(
                            color = coffeeIconColor,
                            topLeft = Offset(size.width * 0.55f, size.height * 0.1f),
                            size = Size(size.width * 0.05f, size.height * 0.15f),
                            cornerRadius = CornerRadius(8f, 8f),
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Caramel Latte",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkBrown
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Espresso shot, steamed milk & caramel syrup",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = lightBrown,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Rp 25.000 / cup",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryOrange
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "PILIHAN UKURAN:",
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = darkBrown
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
                        if (!isLarge) primaryOrange else beigeBorder,
                        RoundedCornerShape(8.dp)
                    )
                    .background(if (!isLarge) Color.White else beigeBg)
                    .clickable { isLarge = false }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Regular (+0)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (!isLarge) primaryOrange else lightBrown
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        1.5.dp,
                        if (isLarge) primaryOrange else beigeBorder,
                        RoundedCornerShape(8.dp)
                    )
                    .background(if (isLarge) Color.White else beigeBg)
                    .clickable { isLarge = true }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Large (+6k)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isLarge) primaryOrange else lightBrown
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, beigeBorder, RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Jumlah Pesanan:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = darkBrown
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .border(1.dp, minusBorder, RoundedCornerShape(6.dp))
                            .background(Color.White)
                            .clickable { if (quantity > 1) quantity-- },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("-", fontSize = 18.sp, color = darkBrown, fontWeight = FontWeight.Medium)
                    }

                    Text(
                        text = "$quantity",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = darkBrown
                    )

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(plusBg)
                            .clickable { quantity++ },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("+", fontSize = 18.sp, color = primaryOrange, fontWeight = FontWeight.Medium)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        val stroke = Stroke(
            width = 3f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f), 0f)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawRoundRect(
                        color = beigeBorder,
                        style = stroke,
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
                    Text("Subtotal:", fontSize = 14.sp, fontWeight = FontWeight.Normal, color = lightBrown)
                    Text(formatRp(subtotal), fontSize = 14.sp, fontWeight = FontWeight.Normal, color = lightBrown)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Pajak Resto (10%):", fontSize = 14.sp, fontWeight = FontWeight.Normal, color = lightBrown)
                    Text(formatRp(tax), fontSize = 14.sp, fontWeight = FontWeight.Normal, color = lightBrown)
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total Tagihan:", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = darkBrown)
                    Text(formatRp(total), fontSize = 16.sp, fontWeight = FontWeight.Bold, color = primaryOrange)
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                Toast.makeText(context, "Pesanan ditambahkan ke keranjang!", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryOrange),
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
