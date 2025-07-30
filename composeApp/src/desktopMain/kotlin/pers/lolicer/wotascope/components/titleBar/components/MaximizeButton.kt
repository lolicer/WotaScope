package pers.lolicer.wotascope.components.titleBar.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.onClick
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import com.russhwolf.settings.set
import org.jetbrains.compose.resources.painterResource
import pers.lolicer.wotascope.settings.SettingsKeys
import pers.lolicer.wotascope.settings.SettingsManager
import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.title_maximize
import wotascope.composeapp.generated.resources.title_restore
import java.awt.GraphicsEnvironment

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun MaximizeButton(
    titleHeight: Dp,
    iconSize: Dp,
    windowState: WindowState,
    onClick: () ->Unit
){
    var active by remember {mutableStateOf(false)}
    var isPressed by remember {mutableStateOf(false)}

    val screenWidth = with(LocalDensity.current) {
        GraphicsEnvironment.getLocalGraphicsEnvironment().maximumWindowBounds.width.toDp()
    }
    val screenHeight = with(LocalDensity.current) {
        GraphicsEnvironment.getLocalGraphicsEnvironment().maximumWindowBounds.height.toDp()
    }

    val previousPosition = remember {mutableStateOf(windowState.position)}
    val previousWidth = remember {mutableStateOf(windowState.size.width)}
    val previousHeight = remember {mutableStateOf(windowState.size.height)}
    val isMaximized = remember {mutableStateOf(false)}

    LaunchedEffect(windowState.position) {
        if(!isMaximized.value)
            previousPosition.value = windowState.position
    }
    LaunchedEffect(windowState.size) {
        val width = windowState.size.width
        val height = windowState.size.height
        if(width == screenWidth && height == screenHeight) {
            isMaximized.value = true
        } else {
            previousWidth.value = width
            previousHeight.value = height

            SettingsManager.settings[SettingsKeys.MAIN_WINDOW_WIDTH] = width.value.toInt()
            SettingsManager.settings[SettingsKeys.MAIN_WINDOW_HEIGHT] = height.value.toInt()
        }
    }

    Box(
        modifier = Modifier.Companion
            .size(titleHeight)
            .pointerHoverIcon(PointerIcon.Companion.Hand),
        contentAlignment = Alignment.Companion.Center
    ) {
        Icon(
            modifier = Modifier.Companion
                .size(if(!active || isPressed) (iconSize - 6.dp) else iconSize)
                .onClick {
                    if(!isMaximized.value) {
                        windowState.size = DpSize(screenWidth, screenHeight)
                        windowState.position = WindowPosition(0.dp, 0.dp)
                    } else {
                        windowState.size = DpSize(previousWidth.value, previousHeight.value)
                        windowState.position = previousPosition.value
                    }

                    isMaximized.value = !isMaximized.value

                    onClick()
                }
                .onPointerEvent(PointerEventType.Companion.Enter) {active = true}
                .onPointerEvent(PointerEventType.Companion.Exit) {active = false}
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while(true) {
                            val event = awaitPointerEvent()
                            when(event.type) {
                                PointerEventType.Companion.Press -> isPressed = true    // 鼠标按下
                                PointerEventType.Companion.Release -> isPressed = false // 鼠标抬起
                                else -> {}
                            }
                        }
                    }
                }
                .background(
                    color = if(!active || isPressed) Color.Companion.Transparent else Color(
                        56,
                        58,
                        61
                    )
                ),
            painter = if(!isMaximized.value) painterResource(Res.drawable.title_maximize) else painterResource(
                Res.drawable.title_restore
            ),
            contentDescription = if(!isMaximized.value) "最大化" else "还原窗口大小",
            tint = Color.Companion.White
        )
    }

}