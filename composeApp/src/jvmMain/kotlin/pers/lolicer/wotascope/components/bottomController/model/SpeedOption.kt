package pers.lolicer.wotascope.components.bottomController.model

import androidx.compose.ui.graphics.Color

data class SpeedOption(
    val displayText: String,
    val value: Float,
    val color: Color = Color.White
)

val SpeedOptions = listOf(
    SpeedOption("2×", 2f),
    SpeedOption("1.5×", 1.5f),
    SpeedOption("1×", 1f),
    SpeedOption("0.75×", 0.75f),
    SpeedOption("0.5×", 0.5f),
    SpeedOption("0.25×", 0.25f, Color.Red)
)