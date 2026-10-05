package com.valerie.vp_week3.soal1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.valerie.vp_week3.soal1.view.ReactionGameScreen
import com.valerie.vp_week3.soal1.viewmodel.ReactionViewModel

class Soal1MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[ReactionViewModel::class.java]
        setContent {
            ReactionGameScreen(viewModel = viewModel)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReactionGameScreenPreview() {
    ReactionGameScreen(viewModel = ReactionViewModel())
}