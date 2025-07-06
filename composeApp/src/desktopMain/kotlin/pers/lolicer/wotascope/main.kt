package pers.lolicer.wotascope

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    val windowState = rememberWindowState()

    Window(
        state = windowState,
        onCloseRequest = ::exitApplication,
        title = "WotaScope",
        undecorated = true
    ) {
        App(windowState, this)
    }
}