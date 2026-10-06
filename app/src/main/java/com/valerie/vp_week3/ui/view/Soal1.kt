package com.valerie.vp_week3.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.valerie.vp_week3.R
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

private const val FOUL = -1L
private const val ROUNDS_NEEDED = 3

private enum class Phase {
    Menu,
    Waiting,
    Go,
    Recap,
    Summary
}

data class Verdict(val text: String, val bg: Color, val pic: Int)

@SuppressLint("MutableCollectionMutableState")
@Composable
fun Soal1View() {
    var phase by remember { mutableStateOf(Phase.Menu) }

    val records by remember { mutableStateOf(mutableListOf<Long>()) }
    var goTimestamp by remember { mutableLongStateOf(0L) }
    var latestTime by remember { mutableLongStateOf(0L) }

    LaunchedEffect(key1 = phase) {
        if (phase == Phase.Waiting) {
            val pause = Random.nextLong(500, 4500)
            delay(pause.milliseconds)

            if (phase == Phase.Waiting) {
                goTimestamp = System.currentTimeMillis()
                phase = Phase.Go
            }
        }
    }

    fun restart() {
        records.clear()
        phase = Phase.Menu
    }

    fun moveOn() {
        val validCount = records.count { it != FOUL }
        phase = if (validCount == ROUNDS_NEEDED) Phase.Summary else Phase.Recap
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                when (phase) {
                    Phase.Menu -> {
                        phase = Phase.Waiting
                    }

                    Phase.Recap -> {
                        if (records.lastOrNull() == FOUL) {
                            records.removeAt(records.lastIndex)
                            phase = Phase.Menu
                        } else {
                            phase = Phase.Waiting
                        }
                    }

                    Phase.Waiting -> {
                        records.add(FOUL)
                        moveOn()
                    }

                    Phase.Go -> {
                        latestTime = System.currentTimeMillis() - goTimestamp
                        records.add(latestTime)
                        moveOn()
                    }

                    Phase.Summary -> {
                        restart()
                    }
                }
            }
    ) {
        when (phase) {
            Phase.Menu -> MenuScreen(records)
            Phase.Waiting -> WaitScreen()
            Phase.Go -> GoScreen()
            Phase.Recap -> {
                if (records.last() == FOUL) {
                    FoulScreen(records)
                } else {
                    val completedTrials = records.count { it != FOUL }
                    RoundScreen(completedTrials, records.last(), records)
                }
            }
            Phase.Summary -> SummaryScreen(records)
        }
    }
}

@Composable
private fun MenuScreen(records: List<Long>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF6FCDDC)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Reaction", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Icon(Icons.Default.FlashOn, contentDescription = "Flash Icon", modifier = Modifier.size(140.dp), tint = Color.White)
            Text(text = "Test", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Medium)
            Spacer(Modifier.height(8.dp))
            Text(text = "Click to Start", color = Color.White.copy(alpha = 0.8f), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        if (records.isNotEmpty()) {
            Spacer(Modifier.height(40.dp))
            ScoreCard(times = records)
        }
    }
}

@Composable
private fun WaitScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFD7D7D7)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Get Ready", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(24.dp))
        Icon(Icons.Default.Warning, contentDescription = "Warning Icon", tint = Color.White, modifier = Modifier.size(120.dp))
        Spacer(Modifier.height(40.dp))
        Text(text = "Wait for the green light...", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Medium)
        Text(text = "DON'T CLICK YET!", color = Color.White, fontSize = 16.sp, modifier = Modifier.padding(top = 30.dp))
    }
}

@Composable
private fun GoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF4CAF51)),
        verticalArrangement = Arrangement.spacedBy(35.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "GO!", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Icon(Icons.AutoMirrored.Filled.DirectionsRun, contentDescription = "Warning Icon", tint = Color.White, modifier = Modifier.size(120.dp))
        Text(text = "CLICK NOW!", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Medium)
        Text(text = "TAP AS FAST AS YOU CAN!", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun FoulScreen(records: List<Long>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFB4141)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "FAIL!", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(40.dp))
        Icon(Icons.Default.ThumbDown, contentDescription = "Thumbs Down", tint = Color.White, modifier = Modifier.size(120.dp))
        Spacer(Modifier.height(35.dp))
        Text(
            text = "You clicked too early, TRY TO \nREAD THE RULE BRO",
            color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Medium, textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(30.dp))
        Text(text = "TRY AGAIN", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        Spacer(Modifier.height(20.dp))
        ScoreCard(times = records)
    }
}

@Composable
private fun RoundScreen(currentTrial: Int, elapsed: Long, records: List<Long>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4CAF51)),
        verticalArrangement = Arrangement.spacedBy(35.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Trial $currentTrial Complete!", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Icon(Icons.Default.CheckCircle, contentDescription = "Warning Icon", tint = Color.White, modifier = Modifier.size(120.dp))
        Text(text = "Time: ${elapsed}ms", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = "Continue to Trial ${currentTrial + 1}", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Medium)
        ScoreCard(times = records)
    }
}

@Composable
private fun SummaryScreen(records: List<Long>) {
    val validTimes = records.filter { it != FOUL }
    val avg = if (validTimes.isNotEmpty()) validTimes.average().toLong() else 0L

    val verdict = when (avg) {
        in 1..179 -> Verdict("DANG YOU ARE SO \nFAST BRO!", Color(0xFF2ECC71), R.drawable.thumb_up)
        in 180..280 -> Verdict("YOUR REFLEX IS \nGOOD", Color(0xFF3498DB), R.drawable.salute)
        in 281..450 -> Verdict("MEH LIKE OTHER \nPERSON", Color(0xFFF39C12), R.drawable.standard)
        else -> Verdict("YOU LIKE A SNAIL \nBRO", Color(0xFFEB6337), R.drawable.thumb_down)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(verdict.bg),
        verticalArrangement = Arrangement.spacedBy(35.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = verdict.text,
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Image(
            painter = painterResource(id = verdict.pic),
            contentDescription = "Image",
            modifier = Modifier
                .padding(top = 20.dp)
                .size(148.dp)
        )
        Text(text = "Average: ${avg}ms", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text(text = "Click to Start New Test", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Medium)
        ScoreCard(times = records, average = avg)
    }
}

@Composable
private fun ScoreCard(times: List<Long>, average: Long? = null) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Trial Results", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF176FD1))
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.width(200.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                (1..ROUNDS_NEEDED).forEach { no ->
                    val entry = times.getOrNull(no - 1)
                    val label = when (entry) {
                        null, FOUL -> "-"
                        else -> "${entry}ms"
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        val isCompleted = entry != null && entry != FOUL
                        Text(
                            text = "$no",
                            fontWeight = FontWeight.Bold,
                            color = if (isCompleted) Color(0xFF4CAF50) else Color.Gray
                        )
                        Text(
                            text = label,
                            color = if (entry == FOUL) Color.Red else Color.Black,
                            fontSize = 14.sp
                        )
                    }
                }
            }
            if (average != null) {
                Spacer(Modifier.height(12.dp))
                Text(
                    "Average Score",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF176FD1)
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    "${average}ms",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFFEE7C2B)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Soal1ViewPreview() {
    Soal1View()
}