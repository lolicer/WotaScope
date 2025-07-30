package pers.lolicer.wotascope.components.titleBar.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.onClick
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.title_escape
import kotlin.system.exitProcess

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun EscapeButton(
    titleHeight: Dp,
    iconSize: Dp
){
    var active by remember {mutableStateOf(false)}
    var isPressed by remember {mutableStateOf(false)}

    Box(
        modifier = Modifier.Companion.size(titleHeight),
        contentAlignment = Alignment.Companion.Center
    ) {
        Icon(
            modifier = Modifier.Companion
                .size(if(!active || isPressed) (iconSize - 6.dp) else iconSize)
                .pointerHoverIcon(PointerIcon.Companion.Hand)
                .onClick {exitProcess(0)}
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
            painter = painterResource(Res.drawable.title_escape),
            contentDescription = "退出程序",
            tint = Color.Companion.White
        )
    }
}