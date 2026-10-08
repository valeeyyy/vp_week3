package com.valerie.vp_week3.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

private val CoffeeDarkBrown = Color(0xFF441F0E)
private val CoffeeBrown = Color(0xFF835336)
private val CoffeeOrange = Color(0xFFE5662F)
private val CoffeeBeige = Color(0xFFF5D9B4)
private val CoffeeCream = Color(0xFFFCF0E2)
private val SummaryBackground = Color(0xFFFFF3E0)
private val SummaryDash = Color(0xFFEFC68E)
private val SummaryText = Color(0xFF4A3A33)
private val SummaryDivider = Color(0xFFF3DDBE)
private val TotalText = Color(0xFF2B1A12)

private const val REGULAR_PRICE = 25000
private const val LARGE_PRICE = 31000

fun formatRpInline(amount: Int): String {
    return "Rp ${NumberFormat.getNumberInstance(Locale("id", "ID")).format(amount)}"
}

private val SteamingMugIcon: ImageVector by lazy {
    val strokePath: ImageVector.Builder.(String) -> Unit = { data ->
        addPath(
            pathData = addPathNodes(data),
            fill = null,
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        )
    }
    ImageVector.Builder(
        name = "SteamingMug",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        strokePath("M6 2v2")
        strokePath("M10 2v2")
        strokePath("M14 2v2")
        strokePath("M16 8a1 1 0 0 1 1 1v8a4 4 0 0 1-4 4H7a4 4 0 0 1-4-4V9a1 1 0 0 1 1-1h14a4 4 0 1 1 0 8h-1")
    }.build()
}

@Composable
fun Soal1Bonus() {
    var quantity by remember { mutableIntStateOf(3) }
    var isLarge by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val unitPrice = if (isLarge) LARGE_PRICE else REGULAR_PRICE
    val subtotal = unitPrice * quantity
    val tax = subtotal / 10
    val total = subtotal + tax

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF3E0).copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                    color = CoffeeDarkBrown
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
                border = BorderStroke(2.dp, CoffeeBeige)
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
                            .background(
                                Brush.linearGradient(listOf(Color(0xFF6B3920), Color(0xFF7E3D22)))
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = SteamingMugIcon,
                            contentDescription = "Coffee",
                            tint = Color(0xFFF3D9AE),
                            modifier = Modifier.size(80.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Caramel Latte",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = CoffeeDarkBrown
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Espresso shot, steamed milk & caramel syrup",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = CoffeeBrown,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Rp 25.000 / cup",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = CoffeeOrange
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "PILIHAN UKURAN:",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = CoffeeDarkBrown
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SizeOption(label = "Regular (+0)", selected = !isLarge) { isLarge = false }
                SizeOption(label = "Large (+6k)", selected = isLarge) { isLarge = true }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, CoffeeBeige, RoundedCornerShape(12.dp))
                    .background(Color.White, RoundedCornerShape(15.dp))
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
                        color = CoffeeDarkBrown
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.White)
                                .border(2.dp, Color(0xFFD4C8C1), RoundedCornerShape(12.dp))
                                .clickable { if (quantity > 1) quantity-- },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("-", fontSize = 24.sp, color = CoffeeDarkBrown, fontWeight = FontWeight.Medium)
                        }

                        Text(
                            text = "$quantity",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = CoffeeDarkBrown
                        )

                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFFDE1B9))
                                .border(2.dp, Color(0xFFD4C8C1), RoundedCornerShape(12.dp))
                                .clickable { quantity++ },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("+", fontSize = 24.sp, color = CoffeeOrange, fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SummaryBackground, RoundedCornerShape(14.dp))
                    .drawBehind {
                        val strokeWidth = 1.5.dp.toPx()
                        val inset = strokeWidth / 2
                        drawRoundRect(
                            color = SummaryDash,
                            topLeft = Offset(inset, inset),
                            size = Size(size.width - strokeWidth, size.height - strokeWidth),
                            cornerRadius = CornerRadius(14.dp.toPx()),
                            style = Stroke(
                                width = strokeWidth,
                                pathEffect = PathEffect.dashPathEffect(
                                    floatArrayOf(5.dp.toPx(), 4.dp.toPx()),
                                    0f
                                )
                            )
                        )
                    }
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                SummaryRow(label = "Subtotal:", value = formatRpInline(subtotal))
                Spacer(modifier = Modifier.height(6.dp))
                SummaryRow(label = "Pajak Resto (10%):", value = formatRpInline(tax))
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(color = SummaryDivider, thickness = 2.dp)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Total Tagihan:", fontSize = 19.sp, fontWeight = FontWeight.Bold, color = TotalText)
                    Text(formatRpInline(total), fontSize = 19.sp, fontWeight = FontWeight.Bold, color = CoffeeOrange)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val size = if (isLarge) "Large" else "Regular"
                    val message = "$quantity Caramel Latte ($size) ditambahkan ke keranjang! " +
                        "Total: ${formatRpInline(total)}"
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        snackbarHostState.showSnackbar(message)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CoffeeOrange),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("TAMBAH KE KERANJANG", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = 24.dp, end = 24.dp, bottom = 100.dp)
        ) { data ->
            Snackbar(
                snackbarData = data,
                shape = RoundedCornerShape(12.dp),
                containerColor = CoffeeDarkBrown,
                contentColor = Color.White
            )
        }
    }
}

@Composable
private fun RowScope.SizeOption(label: String, selected: Boolean, onClick: () -> Unit) {
    val shape = RoundedCornerShape(12.dp)
    Box(
        modifier = Modifier
            .weight(1f)
            .clip(shape)
            .background(if (selected) Color.White else CoffeeCream)
            .border(if (selected) 2.dp else 1.5.dp, if (selected) CoffeeOrange else CoffeeBeige, shape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = if (selected) CoffeeOrange else CoffeeBrown
        )
    }
}

@Composable
private fun SummaryRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 16.sp, fontWeight = FontWeight.Normal, color = SummaryText)
        Text(value, fontSize = 16.sp, fontWeight = FontWeight.Normal, color = SummaryText)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Soal1BonusPreview() {
    Soal1Bonus()
}
