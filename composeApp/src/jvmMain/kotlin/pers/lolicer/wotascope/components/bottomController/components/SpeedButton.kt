package pers.lolicer.wotascope.components.bottomController.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.onClick
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch
import pers.lolicer.wotascope.components_new.status.MediaPlayerListStatus

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun SpeedButton(
    modifier: Modifier
){
    val scope = rememberCoroutineScope()
    var active by remember { mutableStateOf(false) }

    val idx = remember { mutableStateOf(2) }
    val speedMap = mapOf(
        Pair(0, Pair("2×", 2f)),
        Pair(1, Pair("1.5×", 1.5f)),
        Pair(2, Pair("1×", 1f)),
        Pair(3, Pair("0.75×", 0.75f)),
        Pair(4, Pair("0.5×", 0.5f)),
        Pair(5, Pair("0.25×", 0.25f))
    )

    Box(
        modifier = modifier
            .background(color = if(!active) Color.Transparent else Color(56, 58, 61)),
        contentAlignment = Alignment.Center
    ){
        Text(
            modifier = Modifier
                .pointerHoverIcon(PointerIcon.Hand)
                .onPointerEvent(PointerEventType.Enter) { active = true }
                .onPointerEvent(PointerEventType.Exit) { active = false }
                .onClick{
                    if(MediaPlayerListStatus.list.value.isNotEmpty()){
                        var rate = idx.value + 1
                        if(rate == 6) rate = 0

                        MediaPlayerListStatus.list.value.forEach { elem ->
                            if(elem.first.status().isPlayable){
                                if(elem.first.status().isPlaying){
                                    elem.first.controls().setPause(true)
                                    scope.launch {
                                        elem.first.controls().setRate(speedMap[rate]!!.second)
                                        elem.first.controls().setPause(false)
                                    }
                                }
                                else{
                                    scope.launch {
                                        elem.first.controls().setRate(speedMap[rate]!!.second)
                                    }
                                }
                                idx.value = rate
                            }
                            else{
                                println("Can ont play this video.")
                            }
                        }
                    }
                },
            text = speedMap[idx.value]!!.first,
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
    }
}