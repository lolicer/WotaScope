package pers.lolicer.wotascope.components.bottomController.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
import androidx.compose.material3.DropdownMenu
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import pers.lolicer.wotascope.components.bottomController.model.SpeedOptions
import pers.lolicer.wotascope.status.MediaPlayerListStatus

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun SpeedButton(
    size: Dp
){
    var active by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    val speedString = remember { mutableStateOf("1×") }

    Box(
        modifier = Modifier
            .size(size)
            .background(color = if(!active) Color.Transparent else Color(56, 58, 61))
            .pointerHoverIcon(PointerIcon.Hand)
            .onPointerEvent(PointerEventType.Enter) { active = true }
            .onPointerEvent(PointerEventType.Exit) { active = false },
        contentAlignment = Alignment.Center
    ){
        Text(
            modifier = Modifier
                .onClick{
                    expanded = !expanded
                },
            text = speedString.value,
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
        DropdownMenu(
            modifier = Modifier,
            containerColor = Color(43, 45, 48),
            offset = DpOffset(x = -(size / 2), y = 0.dp),
            expanded = expanded,
            onDismissRequest = {expanded = false}
        ){
            SpeedOptions.forEach { option ->
                DropdownMenuItem(
                    modifier = Modifier
                        .pointerHoverIcon(PointerIcon.Hand)
                        .width(size * 3/2)
                        .height(size * 2/3),
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
                if(option.value != 0.25f) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        color = Color.DarkGray
                    )
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