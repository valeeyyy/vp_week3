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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import kotlin.math.max

private enum class MatchStatus {
    Menu,
    Playing,
    Finished
}

private enum class Action(val emojiStr: String, val uiName: String) {
    STONE("✊", "ROCK"),
    SHEET("✋", "PAPER"),
    SCISSOR("✌️", "SCISSORS");
}

private enum class Outcome(val displayStr: String) {
    VICTORY("You Win!"),
    DEFEAT("You Lose"),
    TIE("Draw")
}

private fun evaluateRound(userMove: Action, botMove: Action): Outcome {
    if (userMove == botMove) {
        return Outcome.TIE
    }

    return when (userMove) {
        Action.STONE ->
            if (botMove == Action.SCISSOR) {
                Outcome.VICTORY
            } else {
                Outcome.DEFEAT
            }

        Action.SHEET -> if (botMove == Action.STONE) {
            Outcome.VICTORY
        } else {
            Outcome.DEFEAT
        }

        Action.SCISSOR -> if (botMove == Action.SHEET) {
            Outcome.VICTORY
        } else {
            Outcome.DEFEAT
        }
    }
}

@Composable
fun Soal4() {
    var status by remember { mutableStateOf(MatchStatus.Menu) }
    var userPts by rememberSaveable { mutableIntStateOf(0) }
    var botPts by rememberSaveable { mutableIntStateOf(0) }
    var highestPts by rememberSaveable { mutableIntStateOf(0) }
    val pointsNeeded = 3

    when (status) {
        MatchStatus.Menu -> {
            StartScreen(
                onTapStart = {
                    userPts = 0
                    botPts = 0
                    status = MatchStatus.Playing
                }
            )
        }

        MatchStatus.Playing -> {
            ActiveMatch(
                userPts = userPts,
                botPts = botPts,
                onPtsUpdate = { newU, newB ->
                    userPts = newU
                    botPts = newB
                    if (userPts >= pointsNeeded || botPts >= pointsNeeded) {
                        highestPts = max(highestPts, userPts)
                        status = MatchStatus.Finished
                    }
                }
            )
        }

        MatchStatus.Finished -> {
            EndScreen(
                userPts = userPts,
                botPts = botPts,
                highestPts = highestPts,
                onRetry = {
                    userPts = 0
                    botPts = 0
                    status = MatchStatus.Playing
                },
                onQuit = {
                    userPts = 0
                    botPts = 0
                    status = MatchStatus.Menu
                }
            )
        }
    }
}

@Composable
private fun ScoreBoard(userPts: Int, botPts: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "🧑 $userPts — $botPts 🤖",
            fontSize = 24.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center
        )
        Text(
            "Best of 5",
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ActiveMatch(userPts: Int, botPts: Int, onPtsUpdate: (Int, Int) -> Unit) {
    var userMove by remember { mutableStateOf<Action?>(null) }
    var botMove by remember { mutableStateOf<Action?>(null) }
    var roundState by remember { mutableStateOf<Outcome?>(null) }
    var isShowingResult by remember { mutableStateOf(false) }

    val shuffledActions = remember(isShowingResult) { Action.entries.shuffled() }

    if (isShowingResult) {
        LaunchedEffect(Unit) {
            delay(700)
            isShowingResult = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 248.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScoreBoard(userPts, botPts)
        Spacer(Modifier.weight(1f))

        if (isShowingResult) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(userMove?.emojiStr ?: "❔", fontSize = 64.sp)
                Text("VS", fontSize = 32.sp)
                Text(botMove?.emojiStr ?: "❔", fontSize = 64.sp)
            }
            Spacer(Modifier.height(32.dp))
            Text(roundState?.displayStr ?: "", fontSize = 32.sp, fontWeight = FontWeight.Normal)
        } else {
            // Selection UI
            Text(
                "Pick your move!",
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("❔", fontSize = 64.sp)
                Text(" VS ", fontSize = 32.sp)
                Text("❔", fontSize = 64.sp)
            }
            Spacer(Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally)
            ) {
                shuffledActions.forEach { act ->
                    Button(
                        onClick = {
                            val move1 = act
                            val move2 = Action.entries.toTypedArray().random()
                            val state = evaluateRound(move1, move2)

                            userMove = move1
                            botMove = move2
                            roundState = state
                            isShowingResult = true

                            var addedUserPts = userPts
                            var addedBotPts = botPts
                            if (state == Outcome.VICTORY) addedUserPts++
                            if (state == Outcome.DEFEAT) addedBotPts++
                            onPtsUpdate(addedUserPts, addedBotPts)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFBAC8D1),
                            contentColor = Color.Black,
                        ),
                        modifier = Modifier
                            .height(40.dp)
                    ) {
                        Text("${act.emojiStr} ${act.uiName}", fontSize = 10.sp)
                    }
                }
            }
        }
        Spacer(Modifier.weight(1f))
    }
}

@Composable
private fun StartScreen(onTapStart: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 248.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ScoreBoard(userPts = 0, botPts = 0)
        Spacer(Modifier.weight(1f))
        Text(
            "Rock • Paper • Scissors",
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )
        Spacer(Modifier.height(64.dp))
        Button(
            onClick = onTapStart,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFBAC8D1),
                contentColor = Color.Black,
            ),
            modifier = Modifier.size(width = 200.dp, height = 60.dp)
        ) {
            Text("Start", fontSize = 18.sp, fontWeight = FontWeight.Normal)
        }
        Spacer(Modifier.weight(1f))
    }
}

@Composable
private fun EndScreen(
    userPts: Int,
    botPts: Int,
    highestPts: Int,
    onRetry: () -> Unit,
    onQuit: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        ScoreBoard(userPts = userPts, botPts = botPts)
        
        Spacer(Modifier.weight(1f))
        
        Text(
            text = when {
                userPts > botPts -> "You Win the Match!"
                userPts < botPts -> "You Lose the Match"
                else -> "It's a Draw"
            },
            fontSize = 32.sp,
            fontWeight = FontWeight.Normal
        )
        Spacer(Modifier.height(16.dp))
        Text(
            "Best Score: $highestPts",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal,
            color = Color.Gray
        )
        Spacer(Modifier.height(48.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFBAC8D1),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .height(60.dp)
                    .width(120.dp)
            ) {
                Text("Restart", fontWeight = FontWeight.Normal)
            }
            Button(
                onClick = onQuit,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFBAC8D1),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .height(60.dp)
                    .width(120.dp)
            ) {
                Text("Exit", fontWeight = FontWeight.Normal)
            }
        }
        Spacer(Modifier.weight(1.5f))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Soal4Preview() {
    Soal4()
}
