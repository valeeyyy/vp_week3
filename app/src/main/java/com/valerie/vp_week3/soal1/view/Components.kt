package com.valerie.vp_week3.soal1.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TrialResultsTable(
    trialResults: List<Long>,
    modifier: Modifier = Modifier,
    averageTime: Long? = null
) {
    Column(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Trial Results",
            color = Color(0xFF1976D2),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            for (i in 1..3) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = i.toString(),
                        color = Color(0xFF4CAF50),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    val timeText = if (i <= trialResults.size) {
                        "${trialResults[i - 1]}ms"
                    } else {
                        "-"
                    }
                    Text(
                        text = timeText,
                        color = Color.Black,
                        fontSize = 12.sp
                    )
                }
            }
        }
        if (averageTime != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Average Score",
                color = Color(0xFF1976D2),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${averageTime}ms",
                color = Color(0xFFFF9800),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
