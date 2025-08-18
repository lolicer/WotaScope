package pers.lolicer.wotascope.components.bottomController.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Popup
import pers.lolicer.wotascope.components.bottomController.model.SpeedOptions
import pers.lolicer.wotascope.status.MediaPlayerListStatus

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun SpeedButton(
    size: Dp
){
    var active by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var anchorBounds by remember { mutableStateOf<Rect?>(null) } // 在按钮位置设置锚点

    val speedString = remember { mutableStateOf("1×") }

    Box(
        modifier = Modifier
            .size(size)
            .background(color = if(!active) Color.Transparent else Color(56, 58, 61)),
        contentAlignment = Alignment.Center
    ){
        Text(
            modifier = Modifier
                .pointerHoverIcon(PointerIcon.Hand)
                .onPointerEvent(PointerEventType.Enter) { active = true }
                .onPointerEvent(PointerEventType.Exit) { active = false }
                .onGloballyPositioned { coordinates ->
                    val position = coordinates.localToWindow(Offset.Zero)
                    val size = coordinates.size.toSize()
                    anchorBounds = Rect(position, size)
                }
                .onClick{
                    expanded = !expanded
                },
            text = speedString.value,
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )

        if(anchorBounds != null){
            Popup(
                onDismissRequest = {
                    if(!active) expanded = false
                },
                offset = IntOffset(0, -(size.value.toInt() * 4)) // y值为-(手动计算好的展开菜单的高度)
            ) {
                AnimatedVisibility(
                    visible = expanded && anchorBounds != null,
                    enter = slideInHorizontally(initialOffsetX = {it}),
                    exit = slideOutHorizontally(targetOffsetX = {it + 10})
                ) {
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(color = Color(43, 45, 48))
                            .width(size * 3/2)
                            .border(
                                width = 1.dp,
                                color = Color(67, 68, 69),
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        SpeedOptions.forEach { option ->
                            DropdownMenuItem(
                                modifier = Modifier
                                    .pointerHoverIcon(PointerIcon.Hand)
                                    .height(size * 2/3)
                                    .fillMaxWidth(),
                                text = {
                                    Text(
                                        text = option.displayText,
                                        color = option.color
                                    )
                                },
                                onClick = {
                                    setSpeed(value = option.value)
                                    speedString.value = option.displayText
                                    expanded = !expanded
                                }
                            )
                            if(option.value != 0.25f){
                                HorizontalDivider(
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                    color = Color.DarkGray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * 设置所有视频的速度。
 */
fun setSpeed(
    value: Float
){
    MediaPlayerListStatus.speed = value
    MediaPlayerListStatus.list.value.forEach { elem ->
        if(elem.first.status().isPlayable){
            elem.first.controls().setRate(value)
        }
        else{
            println("暂时无法播放此视频，将延迟更改速度。")
        }
    }
    if(MediaPlayerListStatus.list.value.isEmpty()){
        println("暂时无法播放此视频，将延迟更改速度。")
    }
}