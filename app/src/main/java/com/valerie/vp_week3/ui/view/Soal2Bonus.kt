package com.valerie.vp_week3.ui.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

private val JournalBackground = Color(0xFF121826)
private val CardBackground = Color(0xFF222A3A)
private val CardBorder = Color(0xFF363F52)
private val LabelGray = Color(0xFFA3AAB8)
private val TextWhite = Color(0xFFF1F3F7)
private val PlaceholderGray = Color(0xFF6E7689)
private val AccentBlue = Color(0xFF7AB8EE)
private val DarkInk = Color(0xFF0F1626)

private val satisfactionLevels = listOf("Biasa", "Seru", "Luar Biasa")

@Composable
fun Soal2Bonus() {
    var favoriteSpot by rememberSaveable { mutableStateOf("Fjellheisen Cable Car") }
    var experience by rememberSaveable {
        mutableStateOf("Pemandangan langit malam hijau aurora spektakuler dari puncak gunung...")
    }
    var notes by rememberSaveable { mutableStateOf("") }
    var satisfaction by rememberSaveable { mutableStateOf("Luar Biasa") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(JournalBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 42.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                TripBanner()

                JournalField(
                    label = "SPOT WISATA FAVORIT",
                    value = favoriteSpot,
                    onValueChange = { favoriteSpot = it },
                    placeholder = "(Ketik spot wisata favorit...)",
                    singleLine = true,
                    imeAction = ImeAction.Next
                )

                JournalField(
                    label = "APA YANG PALING KAMU NIKMATI?",
                    value = experience,
                    onValueChange = { experience = it },
                    placeholder = "(Ceritakan pengalamanmu...)",
                    minLines = 3
                )

                JournalField(
                    label = "CATATAN TAMBAHAN / PERLENGKAPAN",
                    value = notes,
                    onValueChange = { notes = it },
                    placeholder = "(Ketik perlengkapan ekstra di sini...)"
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "TINGKAT KEPUASAN:",
                    color = LabelGray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    satisfactionLevels.forEach { level ->
                        SatisfactionChip(
                            label = level,
                            selected = satisfaction == level,
                            onClick = { satisfaction = level }
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(color = LabelGray)) { append("Status: ") }
                        withStyle(SpanStyle(color = AccentBlue, fontWeight = FontWeight.Bold)) {
                            append("Draft Tersimpan")
                        }
                    },
                    fontSize = 15.sp
                )

                FloatingActionButton(
                    onClick = {
                        val spot = favoriteSpot.trim()
                        val message = if (spot.isEmpty()) {
                            "Isi Spot Wisata Favorit terlebih dahulu!"
                        } else {
                            "Jurnal perjalanan ke $spot berhasil disimpan!"
                        }
                        scope.launch {
                            snackbarHostState.currentSnackbarData?.dismiss()
                            snackbarHostState.showSnackbar(message)
                        }
                    },
                    modifier = Modifier.size(65.dp),
                    shape = RoundedCornerShape(20.dp),
                    containerColor = AccentBlue,
                    contentColor = DarkInk,
                    elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp)
                ) {
                    Canvas(
                        modifier = Modifier
                            .size(16.dp)
                            .semantics { contentDescription = "Simpan jurnal" }
                    ) {
                        val stroke = 4.dp.toPx()
                        val mid = size.width / 2
                        drawLine(DarkInk, Offset(0f, mid), Offset(size.width, mid), stroke, StrokeCap.Butt)
                        drawLine(DarkInk, Offset(mid, 0f), Offset(mid, size.height), stroke, StrokeCap.Butt)
                    }
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = 20.dp, end = 20.dp, bottom = 130.dp)
        ) { data ->
            Snackbar(
                snackbarData = data,
                shape = RoundedCornerShape(14.dp),
                containerColor = CardBackground,
                contentColor = TextWhite
            )
        }
    }
}

@Composable
private fun TripBanner() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(
                Brush.linearGradient(listOf(Color(0xFF2D4691), Color(0xFF4A7FC0)))
            )
            .padding(horizontal = 22.dp, vertical = 20.dp)
    ) {
        Text(
            text = "LOG PERJALANAN",
            color = Color(0xFFB9D3F5),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )
        Text(
            text = "Tromsø, Norway ❄️",
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Ekspedisi Aurora Borealis",
            color = Color(0xFFDCE8F8),
            fontSize = 18.sp
        )
    }
}

@Composable
private fun JournalField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    singleLine: Boolean = false,
    minLines: Int = 1,
    imeAction: ImeAction = ImeAction.Default
) {
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(CardBackground)
            .border(1.dp, CardBorder, RoundedCornerShape(14.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { focusRequester.requestFocus() }
            .padding(top = 16.dp)
    ) {
        Text(
            text = label,
            color = LabelGray,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            singleLine = singleLine,
            minLines = if (singleLine) 1 else minLines,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = imeAction
            ),
            textStyle = TextStyle(color = TextWhite, fontSize = 17.sp, lineHeight = 24.sp),
            placeholder = {
                Text(text = placeholder, color = PlaceholderGray, fontSize = 17.sp)
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = AccentBlue,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite
            )
        )
    }
}

@Composable
private fun SatisfactionChip(label: String, selected: Boolean, onClick: () -> Unit) {
    val shape = CircleShape
    Box(
        modifier = Modifier
            .clip(shape)
            .background(if (selected) AccentBlue else Color.Transparent)
            .border(1.5.dp, if (selected) AccentBlue else CardBorder, shape)
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (selected) "$label ★" else label,
            color = if (selected) DarkInk else Color(0xFFC5CBD6),
            fontSize = 16.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Soal2BonusPreview() {
    Soal2Bonus()
}
