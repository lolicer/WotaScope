package pers.lolicer.wotascope.components.singleVideoBar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import pers.lolicer.wotascope.components.singleVideoBar.videoPlayerWithoutSwingPanel.SingleVideoFloatWindows
import pers.lolicer.wotascope.components.singleVideoBar.videoPlayerWithoutSwingPanel.VideoPlayer
import pers.lolicer.wotascope.components.videoStatus.SelectStatusMap
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SingleVideoPanelItem(
    path: String,
    onMediaPlayer: (EmbeddedMediaPlayer) -> Unit,
    onSelectedChanged: (Boolean) -> Unit,
    constraint: Modifier
){
    val mediaPlayer: MutableState<EmbeddedMediaPlayer?> = remember { mutableStateOf(null) }
    val isReady = remember { mutableStateOf(false) }

    val isSelected = remember { mutableStateOf(true) }
    var active by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .background(Color(30, 31, 34))
            .padding(1.dp, 0.dp, 1.dp, 4.dp)
            .border(
                if(isSelected.value) 2.dp else (-1).dp,
                Color(46, 193, 221),
                shape = RoundedCornerShape(2)
            )
            .onPointerEvent(PointerEventType.Enter){ active = true }
            .onPointerEvent(PointerEventType.Move){ active = true }
            .onPointerEvent(PointerEventType.Exit){ active = false }
            .then(constraint)
    ){
        Column (
            modifier = Modifier
                .background(Color(30, 31, 34))
                .padding(4.dp, 4.dp, 4.dp, 8.dp)
        ){
            var screenSize by remember { mutableStateOf(IntSize.Zero) }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .onSizeChanged{ screenSize = it }
            ){
                if(isReady.value){
                    SingleVideoFloatWindows(
                        modifier = Modifier.zIndex(2f),
                        mediaPlayer = mediaPlayer.value!!,
                        isHovered = active && isSelected.value,
                        screenSize
                    )
                }
                VideoPlayer(
                    modifier = Modifier.zIndex(1f),
                    mrl = path,
                    mediaPlayer = mediaPlayer,
                    isSelected = isSelected
                )
            }
            if(mediaPlayer.value != null){
                SingleVideoController(mediaPlayer.value!!)
            }
        }

        LaunchedEffect(isSelected.value){
            if(mediaPlayer.value != null){
                onSelectedChanged(isSelected.value)

                if(!isSelected.value){
                    mediaPlayer.value!!.controls().setPause(true)
                }

                if(SelectStatusMap.mutableMap.containsKey(mediaPlayer.value)){
                    SelectStatusMap.mutableMap[mediaPlayer.value!!] = isSelected.value
                }
                else{
                    println("SelectStatusMap.mutableMap:${SelectStatusMap.mutableMap}")
                    println("But mediaPlayer.value: ${mediaPlayer.value}")
                    throw Exception("SelectCommandMap ERROR")
                }
            }
        }
        val hasReturned = remember { mutableStateOf(false) }
        LaunchedEffect(mediaPlayer.value){
            if(mediaPlayer.value != null && !hasReturned.value){
                // println("mediaPlayer.value = ${mediaPlayer.value}")
                onMediaPlayer(mediaPlayer.value!!)
                hasReturned.value = true

                isReady.value = true
            }
        }
    }
}
