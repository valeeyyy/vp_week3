package com.valerie.vp_week3.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.math.max

private enum class PlayType {
    TINT,
    WORD
}

private enum class MatchPhase {
    Home,
    Preparation,
    Active,
    Finished
}

private enum class Palette(val hexColor: Color) {
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
    var currentPhase by remember { mutableStateOf(MatchPhase.Home) }
    var lastScore by remember { mutableIntStateOf(0) }
    var highestScore by rememberSaveable { mutableIntStateOf(0) }

    when (currentPhase) {
        MatchPhase.Home -> {
            HomeMenu { currentPhase = MatchPhase.Preparation }
        }

        MatchPhase.Preparation -> {
            ReadySetGo(
                onFinish = { currentPhase = MatchPhase.Active }
            )
        }

        MatchPhase.Active -> {
            ActiveMatch(
                onMatchEnd = { finalPts ->
                    lastScore = finalPts
                    highestScore = max(highestScore, finalPts)
                    currentPhase = MatchPhase.Finished
                }
            )
        }

        MatchPhase.Finished -> {
            PostGameScreen(
                achievedScore = lastScore,
                topScore = highestScore,
                onRetry = { currentPhase = MatchPhase.Active },
                onQuit = { currentPhase = MatchPhase.Home }
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
            fontSize = 24.sp
        )

        Button(
            onClick = onBeginTap, 
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFBAC8D1),
                contentColor = Color.DarkGray,
            )
        ) {
            Text("Start Game")
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
            fontSize = 72.sp
        )
    }
}

@Composable
private fun ActiveMatch(onMatchEnd: (pts: Int) -> Unit) {
    var challengeType by remember { mutableStateOf(PlayType.TINT) }
    var remainingTime by remember { mutableLongStateOf(5000L) }
    var corrects by remember { mutableIntStateOf(0) }
    var mistakes by remember { mutableIntStateOf(0) }

    var labelStr by remember { mutableStateOf(Palette.RED) }
    var labelColor by remember { mutableStateOf(Palette.BLUE) }
    var leftIsLabel by remember { mutableStateOf(true) }

    val populateRound = {
        var nextLabel: Palette
        var nextColor: Palette
        do {
            nextLabel = Palette.entries.random()
            nextColor = Palette.entries.random()
        } while (nextLabel == nextColor)

        labelStr = nextLabel
        labelColor = nextColor
        challengeType = if (Random.nextBoolean()) PlayType.TINT else PlayType.WORD
        leftIsLabel = Random.nextBoolean()
        remainingTime = 5000L
    }

    val verifySelection = { picked: Palette ->
        val expected = if (challengeType == PlayType.TINT) labelColor else labelStr
        if (picked == expected) {
            corrects++
        } else {
            mistakes++
        }

        if (mistakes >= 3) {
            onMatchEnd(corrects)
        } else {
            populateRound()
        }
    }

    LaunchedEffect(Unit) {
        populateRound()
        corrects = 0
        mistakes = 0
    }

    LaunchedEffect(key1 = remainingTime) {
        if (remainingTime > 0L) {
            delay(100L)
            remainingTime -= 100L
        } else {
            mistakes++
            if (mistakes >= 3) {
                onMatchEnd(corrects)
            } else {
                populateRound()
            }
        }
    }

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
                "Mode : ${if(challengeType == PlayType.TINT) "COLOR" else "TEXT"}",
                textAlign = TextAlign.Center,
                fontSize = 24.sp
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
                        fontSize = 24.sp
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
                        "$mistakes/3",
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp
                    )
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                "Time Left : ${remainingTime / 1000} s",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 24.sp
            )
            Text(
                text = labelStr.name,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 48.sp,
                color = labelColor.hexColor,
                fontWeight = FontWeight.Bold
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
                Button(onClick = { verifySelection(labelStr) }, colors = btnColor) {
                    Text(labelStr.name)
                }
                Button(onClick = { verifySelection(labelColor) }, colors = btnColor) {
                    Text(labelColor.name)
                }
            } else {
                Button(onClick = { verifySelection(labelColor) }, colors = btnColor) {
                    Text(labelColor.name)
                }
                Button(onClick = { verifySelection(labelStr) }, colors = btnColor) {
                    Text(labelStr.name)
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
            Text("Restart Game")
        }
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = onQuit, 
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFBAC8D1),
                contentColor = Color.Black
            )
        ) {
            Text("Exit")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Soal3Preview() {
    Soal3()
}
