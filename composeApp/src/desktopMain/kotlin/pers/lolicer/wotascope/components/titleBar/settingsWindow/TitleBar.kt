package pers.lolicer.wotascope.components.titleBar.settingsWindow

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.onClick
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope
import org.jetbrains.compose.resources.painterResource
import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.title_escape


@Composable
fun WindowScope.TitleBar(
    titleHeight: Dp,
    showSettingsWindow: MutableState<Boolean>
) = WindowDraggableArea{
    val iconSize = 24.dp

    Row(
        modifier = Modifier.height(titleHeight).fillMaxWidth().background(Color(60, 63, 65)),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Spacer(modifier = Modifier.size(titleHeight))
        Text(
            text = "设置",
            modifier = Modifier.fillMaxHeight().offset(y = 2.dp),
            color = Color.White,
            fontWeight = Bold
        )
        CloseButton(titleHeight, iconSize, showSettingsWindow)
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun CloseButton(
    titleHeight: Dp,
    iconSize: Dp,
    showSettingsWindow: MutableState<Boolean>
){
    var active by remember { mutableStateOf(false) }
    var isPressed by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.size(titleHeight),
        contentAlignment = Alignment.Center
    ){
        Icon(
            modifier = Modifier
                .size(if(!active || isPressed) (iconSize - 6.dp) else iconSize)
                .pointerHoverIcon(PointerIcon.Hand)
                .onClick{ showSettingsWindow.value = false }
                .onPointerEvent(PointerEventType.Enter) { active = true }
                .onPointerEvent(PointerEventType.Exit) { active = false }
                .pointerInput(Unit){
                    awaitPointerEventScope {
                        while(true){
                            val event = awaitPointerEvent()
                            when (event.type) {
                                PointerEventType.Press -> isPressed = true    // 鼠标按下
                                PointerEventType.Release -> isPressed = false // 鼠标抬起
                                else -> {}
                            }
                        }
                    }
                }
                .background(color = if(!active || isPressed) Color.Transparent else Color(56, 58, 61)),
            painter = painterResource(Res.drawable.title_escape),
            contentDescription = "退出设置",
            tint = Color.White
        )
    }
}