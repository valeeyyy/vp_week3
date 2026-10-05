package com.valerie.vp_week3.soal1.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.valerie.vp_week3.soal1.model.GameState
import com.valerie.vp_week3.soal1.viewmodel.ReactionViewModel

@Composable
fun ReactionGameScreen(viewModel: ReactionViewModel) {
    val gameState by viewModel.gameState.collectAsState()
    val trialResults by viewModel.trialResults.collectAsState()
    val currentReactionTime by viewModel.currentReactionTime.collectAsState()

    when (gameState) {
        GameState.START -> StartScreen(
            trialResults = trialResults,
            onStartClicked = { viewModel.onStartClicked() }
        )
        GameState.WAITING -> WaitingScreen(
            onScreenClicked = { viewModel.onWaitingScreenClicked() }
        )
        GameState.READY -> ReadyScreen(
            onScreenClicked = { viewModel.onReadyScreenClicked() }
        )
        GameState.TRIAL_RESULT -> TrialResultScreen(
            trialNumber = trialResults.size,
            timeMs = currentReactionTime,
            trialResults = trialResults,
            onContinueClicked = { viewModel.onContinueClicked() }
        )
        GameState.FINAL_RESULT -> FinalResultScreen(
            averageTime = viewModel.getAverageTime(),
            category = viewModel.getCategory(viewModel.getAverageTime()),
            trialResults = trialResults,
            onRestartClicked = { viewModel.onRestartClicked() }
        )
        GameState.FAIL -> FailScreen(
            trialResults = trialResults,
            onTryAgainClicked = { viewModel.onTryAgainClicked() }
        )
    }
}
