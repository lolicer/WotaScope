package pers.lolicer.wotascope.components.bottomController

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pers.lolicer.wotascope.components.videoStatus.FinishStatusMap
import pers.lolicer.wotascope.components.videoStatus.SelectStatusMap
import pers.lolicer.wotascope.components.videoStatus.isAllFinished
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer

@Composable
fun BottomController(
    controllerHeight: Dp,
    mediaPlayerList: List<EmbeddedMediaPlayer>
){
    val isAnyVideoPlaying = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .height(controllerHeight)
            .fillMaxWidth()
            .background(Color(43, 45, 48)),
        verticalAlignment = Alignment.CenterVertically
    ){
        Spacer(Modifier.width((controllerHeight.value * 0.5).dp))
        Volume(Modifier.width(controllerHeight * 3), mediaPlayerList)
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Center
        ){
            SkipBackButton(Modifier.size(controllerHeight), mediaPlayerList)
            RewindButton(Modifier.size(controllerHeight), mediaPlayerList)
            PauseButton(Modifier.size(controllerHeight), mediaPlayerList, isAnyVideoPlaying)
            FastForwardButton(Modifier.size(controllerHeight), mediaPlayerList)
            SkipForwardButton(Modifier.size(controllerHeight), mediaPlayerList)
        }
        SpeedButton(Modifier.size(controllerHeight))
        Spacer(Modifier.width((controllerHeight.value * 0.5).dp))
    }

    for(mediaPlayer in mediaPlayerList){
        DisposableEffect(mediaPlayer){
            val listener = object : MediaPlayerEventAdapter() {
                override fun finished(mediaPlayer: MediaPlayer) {
                    FinishStatusMap.mutableMap[mediaPlayer as EmbeddedMediaPlayer] = true
                    println("$mediaPlayer JieShu Le")

                    if(FinishStatusMap.isAllFinished()){
                        isAnyVideoPlaying.value = false
                    }
                }
            }
            mediaPlayer.events().addMediaPlayerEventListener(listener)
            onDispose { mediaPlayer.events().removeMediaPlayerEventListener(listener) }
        }
    }
}

fun List<EmbeddedMediaPlayer>.isAnyPlaying(): Boolean{
    for(mediaPlayer in this){
        if(SelectStatusMap.mutableMap[mediaPlayer] == true && mediaPlayer.status().isPlaying){
            return true
        }
    }
    return false
}