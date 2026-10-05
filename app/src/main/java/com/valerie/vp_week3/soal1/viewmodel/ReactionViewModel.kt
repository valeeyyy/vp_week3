package com.valerie.vp_week3.soal1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valerie.vp_week3.soal1.model.GameState
import com.valerie.vp_week3.soal1.model.ReactionCategory
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReactionViewModel : ViewModel() {
    private val _gameState = MutableStateFlow(GameState.START)
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    private val _trialResults = MutableStateFlow<List<Long>>(emptyList())
    val trialResults: StateFlow<List<Long>> = _trialResults.asStateFlow()

    private val _currentReactionTime = MutableStateFlow(0L)
    val currentReactionTime: StateFlow<Long> = _currentReactionTime.asStateFlow()

    private var waitJob: Job? = null
    private var startTime: Long = 0L

    fun onStartClicked() {
        _gameState.value = GameState.WAITING
        startRandomDelay()
    }

    private fun startRandomDelay() {
        waitJob?.cancel()
        waitJob = viewModelScope.launch {
            val delayMs = (500..4500).random().toLong()
            delay(delayMs)
            _gameState.value = GameState.READY
            startTime = System.currentTimeMillis()
        }
    }

    fun onWaitingScreenClicked() {
        waitJob?.cancel()
        _gameState.value = GameState.FAIL
    }

    fun onReadyScreenClicked() {
        val time = System.currentTimeMillis() - startTime
        _currentReactionTime.value = time
        val newResults = _trialResults.value.toMutableList()
        newResults.add(time)
        _trialResults.value = newResults

        if (newResults.size >= 3) {
            _gameState.value = GameState.FINAL_RESULT
        } else {
            _gameState.value = GameState.TRIAL_RESULT
        }
    }

    fun onContinueClicked() {
        _gameState.value = GameState.START
    }

    fun onTryAgainClicked() {
        _gameState.value = GameState.START
    }

    fun onRestartClicked() {
        _trialResults.value = emptyList()
        _currentReactionTime.value = 0L
        _gameState.value = GameState.START
    }

    fun getAverageTime(): Long {
        val results = _trialResults.value
        if (results.isEmpty()) return 0L
        return results.sum() / results.size
    }

    fun getCategory(average: Long): ReactionCategory {
        return when {
            average < 180 -> ReactionCategory.FAST
            average < 280 -> ReactionCategory.GOOD
            average < 450 -> ReactionCategory.STANDARD
            else -> ReactionCategory.SNAIL
        }
    }
}
