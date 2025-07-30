package pers.lolicer.wotascope.components.titleBar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope
import androidx.compose.ui.window.WindowState
import pers.lolicer.wotascope.components.titleBar.components.AddButton
import pers.lolicer.wotascope.components.titleBar.components.EscapeButton
import pers.lolicer.wotascope.components.titleBar.components.MaximizeButton
import pers.lolicer.wotascope.components.titleBar.components.MinimizeButton
import pers.lolicer.wotascope.components.titleBar.components.SettingsButton
import pers.lolicer.wotascope.components.titleBar.components.TitleIcon

@Composable
fun WindowScope.TitleBar(
    titleHeight: Dp,
    windowState: WindowState,
    paths: MutableState<List<String>>,
    onMaximizeButtonClick: () -> Unit
) = WindowDraggableArea{
    val iconSize = 24.dp

    Row(
        Modifier.height(titleHeight).fillMaxWidth().background(Color(43, 45, 48))
    ){
        TitleIcon(titleHeight)
        Spacer(modifier = Modifier.width(8.dp))
        AddButton(titleHeight, paths, {}, {})
        Spacer(modifier = Modifier.width(8.dp))
        SettingsButton(titleHeight)

        Spacer(modifier = Modifier.weight(1f))

        MinimizeButton(titleHeight, iconSize, windowState)
        MaximizeButton(titleHeight, iconSize, windowState, onMaximizeButtonClick)
        EscapeButton(titleHeight, iconSize)
    }
}