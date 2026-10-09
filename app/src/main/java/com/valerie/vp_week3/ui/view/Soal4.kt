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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.max

private const val BEST_OF = 5
private const val POINTS_TO_WIN = BEST_OF / 2 + 1

private enum class State {
    INITIAL,
    PICK,
    REVEAL,
    FINISHED
}

private enum class Rps(val emojiStr: String, val uiName: String) {
    ROCK("✊", "ROCK"),
    PAPER("✋", "PAPER"),
    SCISSOR("✌️", "SCISSORS");

    fun beats(other: Rps): Boolean = when (this) {
        ROCK -> other == SCISSOR
        PAPER -> other == ROCK
        SCISSOR -> other == PAPER
    }
}

private enum class Result(val displayStr: String) {
    WIN("You Win!"),
    LOSE("You Lose"),
    DRAW("Draw")
}

private fun evaluateRound(userMove: Rps, botMove: Rps): Result = when {
    userMove == botMove -> Result.DRAW
    userMove.beats(botMove) -> Result.WIN
    else -> Result.LOSE
}

@Composable
fun Soal4() {
    var status by rememberSaveable { mutableStateOf(State.INITIAL) }
    var userPts by rememberSaveable { mutableIntStateOf(0) }
    var botPts by rememberSaveable { mutableIntStateOf(0) }
    var highestPts by rememberSaveable { mutableStateOf<Int?>(null) }

    var userMove by rememberSaveable { mutableStateOf<Rps?>(null) }
    var botMove by rememberSaveable { mutableStateOf<Rps?>(null) }
    var roundState by rememberSaveable { mutableStateOf<Result?>(null) }
    var shuffledActions by remember { mutableStateOf(Rps.entries.shuffled()) }

    fun startRound() {
        userMove = null
        botMove = null
        roundState = null
        shuffledActions = Rps.entries.shuffled()
        status = State.PICK
    }

    fun startMatch() {
        userPts = 0
        botPts = 0
        startRound()
    }

    fun pickMove(move: Rps) {
        if (status != State.PICK) return

        val cpuMove = Rps.entries.random()
        val result = evaluateRound(move, cpuMove)

        userMove = move
        botMove = cpuMove
        roundState = result
        when (result) {
            Result.WIN -> userPts++
            Result.LOSE -> botPts++
            Result.DRAW -> Unit
        }
        status = State.REVEAL
    }

    if (status == State.REVEAL) {
        LaunchedEffect(Unit) {
            delay(700)
            if (userPts >= POINTS_TO_WIN || botPts >= POINTS_TO_WIN) {
                val diff = userPts - botPts
                highestPts = max(highestPts ?: diff, diff)
                status = State.FINISHED
            } else {
                startRound()
            }
        }
    }

    when (status) {
        State.INITIAL -> {
            StartScreen(onTapStart = { startMatch() })
        }

        State.PICK, State.REVEAL -> {
            ActiveMatch(
                userPts = userPts,
                botPts = botPts,
                isShowingResult = status == State.REVEAL,
                userMove = userMove,
                botMove = botMove,
                roundState = roundState,
                shuffledActions = shuffledActions,
                onPick = { pickMove(it) }
            )
        }

        State.FINISHED -> {
            EndScreen(
                userPts = userPts,
                botPts = botPts,
                highestPts = highestPts ?: 0,
                onRetry = { startMatch() },
                onQuit = {
                    userPts = 0
                    botPts = 0
                    status = State.INITIAL
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
            "Best of $BEST_OF",
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ActiveMatch(
    userPts: Int,
    botPts: Int,
    isShowingResult: Boolean,
    userMove: Rps?,
    botMove: Rps?,
    roundState: Result?,
    shuffledActions: List<Rps>,
    onPick: (Rps) -> Unit
) {
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
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.semantics(mergeDescendants = true) {
                    contentDescription = "You picked ${userMove?.uiName}, computer picked ${botMove?.uiName}"
                }
            ) {
                Text(userMove?.emojiStr ?: "❔", fontSize = 64.sp)
                Text("VS", fontSize = 32.sp)
                Text(botMove?.emojiStr ?: "❔", fontSize = 64.sp)
            }
            Spacer(Modifier.height(32.dp))
            Text(
                roundState?.displayStr ?: "",
                fontSize = 32.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.semantics { contentDescription = "Round result: ${roundState?.displayStr}" }
            )
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
                        onClick = { onPick(act) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFBAC8D1),
                            contentColor = Color.Black,
                        ),
                        modifier = Modifier
                            .height(40.dp)
                            .semantics { contentDescription = "Pick ${act.uiName}" }
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
            fontSize = 24.sp,
            fontWeight = FontWeight.Light
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
            fontWeight = FontWeight.Light
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
