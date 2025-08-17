package pers.lolicer.wotascope.components.titleBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope
import androidx.compose.ui.window.WindowState
import kotlinx.coroutines.delay
import pers.lolicer.wotascope.components.titleBar.components.AddButton
import pers.lolicer.wotascope.components.titleBar.components.EncodingStatusText
import pers.lolicer.wotascope.components.titleBar.components.EscapeButton
import pers.lolicer.wotascope.components.titleBar.components.MaximizeButton
import pers.lolicer.wotascope.components.titleBar.components.MinimizeButton
import pers.lolicer.wotascope.components.titleBar.components.SettingsButton
import pers.lolicer.wotascope.components.titleBar.components.TitleIcon
import pers.lolicer.wotascope.status.EncodingState

@Composable
fun WindowScope.TitleBar(
    titleHeight: Dp,
    windowState: WindowState,
    onMaximizeButtonClick: () -> Unit
) = WindowDraggableArea{
    val iconSize = 24.dp

    Row(
        modifier = Modifier
            .height(titleHeight)
            .fillMaxWidth()
            .background(Color(43, 45, 48)),
        horizontalArrangement = Arrangement.SpaceBetween
    ){

        val encodingState = remember { mutableStateOf<EncodingState?>(null) }
        var showCompletion by remember { mutableStateOf(false) }

        Row{
            TitleIcon(titleHeight)
            Spacer(modifier = Modifier.width(8.dp))
            AddButton(titleHeight, { encodingState.value = EncodingState.ENCODING }, { encodingState.value = EncodingState.COMPLETED })
            Spacer(modifier = Modifier.width(8.dp))
            SettingsButton(titleHeight)
        }

        // Spacer(modifier = Modifier.fillMaxHeight().width(10.dp).background(Color.Green))
        EncodingStatusText(titleHeight, encodingState, showCompletion)

        Row{
            MinimizeButton(titleHeight, iconSize, windowState)
            MaximizeButton(titleHeight, iconSize, windowState, onMaximizeButtonClick)
            EscapeButton(titleHeight, iconSize)
        }

        LaunchedEffect(encodingState.value){
            if(encodingState.value == EncodingState.COMPLETED){
                delay(500)
                showCompletion = true
                delay(2500)
                if(encodingState.value != EncodingState.ENCODING){
                    showCompletion = false
                    encodingState.value = null
                }
            }
        }
    }
}