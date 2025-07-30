package pers.lolicer.wotascope.components.titleBar.components.settingsWindow

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.rememberDialogState
import kotlin.Boolean

@Composable
fun SettingsWindow(showSettingsWindow: MutableState<Boolean>){
    val windowState = rememberDialogState()

    DialogWindow(
        state = windowState,
        onCloseRequest = {},
        title = "设置",
        undecorated = true,
        transparent = true
    ) {
        val windowScope = this
        val titleHeight = 30.dp

        val focusManager = LocalFocusManager.current
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .border(
                    width = 1.dp,
                    color = Color(67, 68, 69),
                    shape = RoundedCornerShape(8.dp)
                )
                .pointerInput(Unit) {
                    detectTapGestures {
                        focusManager.clearFocus() // 点击任何位置都清除焦点
                    }
                }
        ){
            windowScope.TitleBar(titleHeight, showSettingsWindow)
            MainSettingPanel(windowState.size.height - titleHeight)
        }
    }
}