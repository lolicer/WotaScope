package pers.lolicer.wotascope.components.titleBar.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Popup
import pers.lolicer.wotascope.components.titleBar.components.overlapControl.MiniController
import pers.lolicer.wotascope.components.titleBar.components.overlapControl.OverlapSwitch
import pers.lolicer.wotascope.status.MediaPlayerListStatus

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun OverlapControlButton(
    titleHeight: Dp
){
    var active by remember {mutableStateOf(false)}
    var isPressed by remember {mutableStateOf(false)}

    var expanded by remember { mutableStateOf(false) }
    var anchorBounds by remember { mutableStateOf<Rect?>(null) } // 在按钮位置设置锚点

    Box {
        Text(
            "重叠",
            color = Color.Companion.White,
            fontSize = 14.sp,
            modifier = Modifier.Companion
                .offset(y = 4.dp)
                .height(titleHeight - 8.dp)
                .onGloballyPositioned { coordinates ->
                    val position = coordinates.localToWindow(Offset.Zero)
                    val size = coordinates.size.toSize()
                    anchorBounds = Rect(position, size)
                }
                .pointerHoverIcon(PointerIcon.Companion.Hand)
                .onPointerEvent(PointerEventType.Companion.Enter) { active = true }
                .onPointerEvent(PointerEventType.Companion.Exit) { active = false }
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
                .onClick {
                    expanded = !expanded
                }
                .background(color = if(active) Color(56, 58, 61) else Color.Companion.Transparent)
                .drawBehind {
                    val textHeight = size.height
                    val textWidth = size.width
                    val strokeWidth = 1f

                    if(active && !isPressed) {
                        drawLine(
                            color = Color.Companion.White,
                            start = Offset(0f, textHeight - 2),
                            end = Offset(textWidth, textHeight - 2),
                            strokeWidth = strokeWidth
                        )
                    }
                }
        )

        if(anchorBounds != null) {
            Popup(
                onDismissRequest = {
                    if(!active) expanded = false
                },
                offset = IntOffset(0, anchorBounds!!.bottom.toInt())
            ) {
                AnimatedVisibility(
                    visible = expanded && anchorBounds != null,
                    enter = expandVertically(),
                    exit = shrinkVertically{ -(it + 10) }
                ) {
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(color = Color(43, 45, 48))
                            .width(titleHeight * 4 + 8.dp * 2)
                            .border(
                                width = 1.dp,
                                color = Color(67, 68, 69),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                    ) {
                        OverlapSwitch(titleHeight, titleHeight * 4)
                        if(MediaPlayerListStatus.list.value.size == 2) {
                            Divider(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp),
                                color = Color(188, 190, 196)
                            )
                            MiniController(titleHeight, titleHeight * 4)
                        }
                    }
                }
            }
        }
    }
}