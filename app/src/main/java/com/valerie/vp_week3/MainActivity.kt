package com.valerie.vp_week3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat.enableEdgeToEdge
import com.valerie.vp_week3.ui.theme.Vp_week3Theme
import com.valerie.vp_week3.ui.view.Soal1Bonus
import com.valerie.vp_week3.ui.view.Soal1View
import com.valerie.vp_week3.ui.view.Soal1View
import com.valerie.vp_week3.ui.view.Soal2
import com.valerie.vp_week3.ui.view.Soal2Bonus
import com.valerie.vp_week3.ui.view.Soal3
import com.valerie.vp_week3.ui.view.Soal4

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Vp_week3Theme {
                // uncomment pada tampilan (view) yang ingin dijalankan:

                Soal1View()
                // Soal1Bonus()
                // Soal2()
                // Soal2Bonus()
                // Soal3()
                // Soal4()
            }
        }
    }
}