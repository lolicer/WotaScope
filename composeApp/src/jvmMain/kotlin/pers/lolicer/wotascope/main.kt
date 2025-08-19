package pers.lolicer.wotascope

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberTrayState
import androidx.compose.ui.window.rememberWindowState
import org.jetbrains.compose.resources.painterResource
import pers.lolicer.wotascope.settings.SettingsKeys
import pers.lolicer.wotascope.settings.SettingsManager.settings
import pers.lolicer.wotascope.status.GlobalTrayState
import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.compose_multiplatform
import wotascope.composeapp.generated.resources.wotascope_icon

fun main() = application {
    val windowState = rememberWindowState()
    windowState.position = WindowPosition(Alignment.Center)
    windowState.size = DpSize(
        settings.getIntOrNull(SettingsKeys.MAIN_WINDOW_WIDTH)!!.coerceAtLeast(90).dp,
        settings.getIntOrNull(SettingsKeys.MAIN_WINDOW_HEIGHT)!!.coerceAtLeast(60).dp
    )


    val trayState = rememberTrayState()
    GlobalTrayState.trayState = trayState

    Window(
        state = windowState,
        onCloseRequest = ::exitApplication,
        title = "WotaScope",
        icon = painterResource(Res.drawable.wotascope_icon),
        undecorated = true,
        transparent = true
    ) {
        App(windowState, this)
    }

    Tray(
        state = trayState,
        icon = painterResource(Res.drawable.compose_multiplatform),
        menu = {
            Item(
                "Exit",
                onClick = { exitApplication() }
            )
        }
    )
}