package com.valerie.vp_week3.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.text.DecimalFormat

@Composable
fun Soal2() {
    var totalCurrency by remember { mutableFloatStateOf(0f) }
    var currencyPerTap by remember { mutableFloatStateOf(1f) }
    var priceToUpgrade by remember { mutableIntStateOf(10) }
    var isTapping by remember { mutableStateOf(false) }
    var tapCounter by remember { mutableFloatStateOf(0f) }

    val numberFormatter = DecimalFormat("#.#")
    val isEligibleForUpgrade = totalCurrency >= priceToUpgrade

    fun buyUpgrade() {
        if (totalCurrency >= priceToUpgrade) {
            totalCurrency -= priceToUpgrade
            priceToUpgrade *= 2
            currencyPerTap *= 1.5f
        }
    }

    if (isTapping) {
        LaunchedEffect(key1 = tapCounter) {
            delay(300L)
            isTapping = false
        }
    }

    val context = LocalContext.current
    
    val bgResId = remember { context.resources.getIdentifier("cat_background", "drawable", context.packageName) }
    val catAaaResId = remember { context.resources.getIdentifier("cat_aaa", "drawable", context.packageName) }
    val catMmmResId = remember { context.resources.getIdentifier("cat_mmm", "drawable", context.packageName) }

    Box(modifier = Modifier.fillMaxSize()) {
        if (bgResId != 0) {
            Image(
                painter = painterResource(id = bgResId),
                contentDescription = "Main Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Box(modifier = Modifier.fillMaxSize().background(Color.DarkGray))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black.copy(alpha = 0.25f))
        ) {}

        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clip(RoundedCornerShape(25.dp))
                    .background(color = Color.White.copy(alpha = 0.3f))
                    .width(170.dp)
                    .padding(vertical = 25.dp)
            ) {
                Text(
                    text = "Your Coins",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = numberFormatter.format(totalCurrency),
                    color = Color.Green,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${numberFormatter.format(currencyPerTap)} coins per tap",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Tap The Cat!",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                
                val currentCatImg = if (isTapping) catAaaResId else catMmmResId
                
                if (currentCatImg != 0) {
                    Image(
                        painter = painterResource(id = currentCatImg),
                        contentDescription = "Tap the Cat",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(200.dp)
                            .height(200.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                totalCurrency += currencyPerTap
                                isTapping = true
                                tapCounter++
                            }
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .width(200.dp)
                            .height(200.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White)
                            .clickable {
                                totalCurrency += currencyPerTap
                                isTapping = true
                                tapCounter++
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(if (isTapping) "🙀" else "😺", fontSize = 100.sp)
                    }
                }
                
                Text(
                    text = if (isTapping) "Meow!" else "Purr~",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(color = Color.White, shape = RoundedCornerShape(24.dp))
                    .width(325.dp)
                    .padding(vertical = 24.dp, horizontal = 16.dp)
            ) {
                Text(
                    text = "Give Me Your Coin",
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Next upgrade: ${numberFormatter.format(currencyPerTap * 1.5f)} coins per tap",
                    color = Color.Black.copy(alpha = 0.7f),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Button(
                    onClick = { buyUpgrade() },
                    enabled = isEligibleForUpgrade,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50),
                        contentColor = Color.White,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.White.copy(alpha = 0.7f)
                    ),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
                ) {
                    val remainingCoins = priceToUpgrade - totalCurrency
                    Text(
                        text = if (isEligibleForUpgrade) {
                            "Pay for $priceToUpgrade coins"
                        } else {
                            "Find ${numberFormatter.format(remainingCoins)} more coins"
                        },
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Soal2Preview() {
    Soal2()
}
