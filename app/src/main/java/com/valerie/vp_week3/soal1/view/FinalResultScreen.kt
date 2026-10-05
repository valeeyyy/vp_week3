package com.valerie.vp_week3.soal1.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.valerie.vp_week3.soal1.model.ReactionCategory

@Composable
fun FinalResultScreen(
    averageTime: Long,
    category: ReactionCategory,
    trialResults: List<Long>,
    onRestartClicked: () -> Unit
) {
    val backgroundColor = when (category) {
        ReactionCategory.FAST -> Color(0xFF00E676)
        ReactionCategory.GOOD -> Color(0xFF2196F3)
        ReactionCategory.STANDARD -> Color(0xFFFF9800)
        ReactionCategory.SNAIL -> Color(0xFFFF5722)
    }

    val emoji = when (category) {
        ReactionCategory.FAST -> "🫡"
        ReactionCategory.GOOD -> "👍"
        ReactionCategory.STANDARD -> "😐"
        ReactionCategory.SNAIL -> "👎"
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .clickable { onRestartClicked() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = category.message,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = emoji,
                fontSize = 120.sp
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Average: ${averageTime}ms",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Click to Start New Test",
                color = Color.White,
                fontSize = 16.sp
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        ) {
            TrialResultsTable(
                trialResults = trialResults,
                averageTime = averageTime
            )
        }
    }
}
