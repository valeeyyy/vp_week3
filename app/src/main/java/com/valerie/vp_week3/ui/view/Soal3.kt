package com.valerie.vp_week3.ui.view

import android.os.SystemClock
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.math.max

private const val ROUND_TIME_MS = 5000L
private const val MAX_MISTAKES = 3

private val GameButtonModifier = Modifier.height(64.dp).widthIn(min = 120.dp)

private enum class Mode {
    COLOR,
    TEXT
}

private enum class Choice {
    INK,
    WORD
}

private enum class GameState {
    Home,
    Countdown,
    Running,
    GameOver
}

private enum class ColorName(val hexColor: Color) {
    RED(Color.Red),
    BLUE(Color.Blue),
    GREEN(Color.Green),
    YELLOW(Color(0xFFFFCC4A)),
    BLACK(Color.Black),
    PURPLE(Color(0xFF800080)),
    ORANGE(Color(0xFFFF6600)),
}

@Composable
fun Soal3() {
    var currentPhase by remember { mutableStateOf(GameState.Home) }
    var lastScore by remember { mutableIntStateOf(0) }
    var highestScore by rememberSaveable { mutableIntStateOf(0) }

    when (currentPhase) {
        GameState.Home -> {
            HomeMenu { currentPhase = GameState.Countdown }
        }

        GameState.Countdown -> {
            ReadySetGo(
                onFinish = { currentPhase = GameState.Running }
            )
        }

        GameState.Running -> {
            ActiveMatch(
                onMatchEnd = { finalPts ->
                    lastScore = finalPts
                    highestScore = max(highestScore, finalPts)
                    currentPhase = GameState.GameOver
                }
            )
        }

        GameState.GameOver -> {
            PostGameScreen(
                achievedScore = lastScore,
                topScore = highestScore,
                onRetry = { currentPhase = GameState.Countdown },
                onQuit = { currentPhase = GameState.Home }
            )
        }
    }
}

@Composable
private fun HomeMenu(onBeginTap: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically)
    ) {
        Text(
            "Welcome \n to \n Color Word Matching",
            textAlign = TextAlign.Center,
            fontSize = 26.sp,
            fontWeight = FontWeight.Light,
            color = Color.Black
        )

        Button(
            onClick = onBeginTap, 
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFBAC8D1),
                contentColor = Color.DarkGray,
            )
        ) {
            Text(
                text = "Start Game",
                fontSize = 18.sp
            )
        }
    }
}

@Composable
private fun ReadySetGo(onFinish: () -> Unit) {
    var displayTxt by remember { mutableStateOf("3") }

    LaunchedEffect(key1 = true) {
        for (count in 3 downTo 1) {
            displayTxt = count.toString()
            delay(1000L)
        }

        displayTxt = "Start!"
        delay(700L)

        onFinish()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = displayTxt,
            textAlign = TextAlign.Center,
            fontSize = 40.sp,
            fontWeight = FontWeight.Light
        )
    }
}

@Composable
private fun ActiveMatch(onMatchEnd: (pts: Int) -> Unit) {
    var challengeType by remember { mutableStateOf(Mode.entries.random()) }
    var remainingTime by remember { mutableLongStateOf(ROUND_TIME_MS) }
    var corrects by remember { mutableIntStateOf(0) }
    var mistakes by remember { mutableIntStateOf(0) }
    var roundNo by remember { mutableIntStateOf(0) }

    var labelStr by remember { mutableStateOf(ColorName.entries.random()) }
    var labelColor by remember { mutableStateOf(ColorName.entries.filter { it != labelStr }.random()) }
    var leftIsLabel by remember { mutableStateOf(Random.nextBoolean()) }

    fun populateRound() {
        val nextLabel = ColorName.entries.random()
        labelStr = nextLabel
        labelColor = ColorName.entries.filter { it != nextLabel }.random()
        challengeType = Mode.entries.random()
        leftIsLabel = Random.nextBoolean()
        remainingTime = ROUND_TIME_MS
        roundNo++
    }

    fun submitAnswer(forRound: Int, picked: Choice?) {
        if (forRound != roundNo || mistakes >= MAX_MISTAKES) return

        val expected = if (challengeType == Mode.COLOR) Choice.INK else Choice.WORD
        if (picked == expected) {
            corrects++
        } else {
            mistakes++
        }

        if (mistakes >= MAX_MISTAKES) {
            onMatchEnd(corrects)
        } else {
            populateRound()
        }
    }

    LaunchedEffect(key1 = roundNo) {
        val startedAt = SystemClock.elapsedRealtime()
        remainingTime = ROUND_TIME_MS
        while (remainingTime > 0L) {
            delay(100L)
            remainingTime = max(0L, ROUND_TIME_MS - (SystemClock.elapsedRealtime() - startedAt))
        }
        submitAnswer(roundNo, null)
    }

    val shownRound = roundNo

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(128.dp, Alignment.CenterVertically)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Mode : ${challengeType.name}",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Light
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.CheckBox,
                        contentDescription = "Correct",
                        tint = Color(0xFF80B046),
                        modifier = Modifier.size(28.dp)
                    )
                    Text(
                        "$corrects",
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Light
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = "Incorrect",
                        tint = Color(0xFFE8462D),
                        modifier = Modifier.size(28.dp)
                    )
                    Text(
                        "$mistakes/$MAX_MISTAKES",
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Light
                    )
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                "${(remainingTime + 999) / 1000} s",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Light
            )
            Text(
                text = labelStr.name,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 48.sp,
                color = labelColor.hexColor,
                fontWeight = FontWeight.Light
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val btnColor = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFBAC8D1),
                contentColor = Color.Black
            )
            
            if (leftIsLabel) {
                Button(onClick = { submitAnswer(shownRound, Choice.WORD) }, modifier = GameButtonModifier, colors = btnColor) {
                    Text(labelStr.name, fontWeight = FontWeight.Normal)
                }
                Button(onClick = { submitAnswer(shownRound, Choice.INK) }, modifier = GameButtonModifier, colors = btnColor) {
                    Text(labelColor.name, fontWeight = FontWeight.Normal)
                }
            } else {
                Button(onClick = { submitAnswer(shownRound, Choice.INK) }, modifier = GameButtonModifier, colors = btnColor) {
                    Text(labelColor.name, fontWeight = FontWeight.Normal)
                }
                Button(onClick = { submitAnswer(shownRound, Choice.WORD) }, modifier = GameButtonModifier, colors = btnColor) {
                    Text(labelStr.name, fontWeight = FontWeight.Normal)
                }
            }
        }
    }
}

@Composable
private fun PostGameScreen(achievedScore: Int, topScore: Int, onRetry: () -> Unit, onQuit: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Game Over!", fontSize = 32.sp, fontWeight = FontWeight.Light)
        Spacer(Modifier.height(48.dp))
        Text(
            "You're Score \n $achievedScore",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Light
        )
        Spacer(Modifier.height(16.dp))
        Text(
            "Best Score \n $topScore",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Light
        )
        Spacer(Modifier.height(48.dp))
        Button(
            onClick = onRetry, 
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFBAC8D1),
                contentColor = Color.Black
            )
        ) {
            Text(
                text = "Restart Game",
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )
        }
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = onQuit, 
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFBAC8D1),
                contentColor = Color.Black
            )
        ) {
            Text(
                text = "Exit",
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Soal3Preview() {
    Soal3()
}
