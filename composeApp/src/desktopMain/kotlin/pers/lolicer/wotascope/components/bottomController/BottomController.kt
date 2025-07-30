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
import pers.lolicer.wotascope.components.bottomController.components.FastForwardButton
import pers.lolicer.wotascope.components.bottomController.components.PauseButton
import pers.lolicer.wotascope.components.bottomController.components.RewindButton
import pers.lolicer.wotascope.components.bottomController.components.SkipBackButton
import pers.lolicer.wotascope.components.bottomController.components.SkipForwardButton
import pers.lolicer.wotascope.components.bottomController.components.SpeedButton
import pers.lolicer.wotascope.components.bottomController.components.Volume
import pers.lolicer.wotascope.components.videoStatus.MediaPlayerListStatus
import pers.lolicer.wotascope.components.videoStatus.isAllFinished
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter

@Composable
fun BottomController(
    controllerHeight: Dp
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
        Volume(Modifier.width(controllerHeight * 3), /* mediaPlayerList */)
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Center
        ){
            SkipBackButton(Modifier.size(controllerHeight), /* mediaPlayerList */)
            RewindButton(Modifier.size(controllerHeight), /* mediaPlayerList */)
            PauseButton(Modifier.size(controllerHeight), /* mediaPlayerList ,*/ isAnyVideoPlaying)
            FastForwardButton(Modifier.size(controllerHeight), /* mediaPlayerList */)
            SkipForwardButton(Modifier.size(controllerHeight), /* mediaPlayerList */)
        }

        Spacer(Modifier.width(controllerHeight * 2))
        SpeedButton(Modifier.size(controllerHeight))
        Spacer(Modifier.width((controllerHeight.value * 0.5).dp))
    }

    // val map = remember { mutableStateOf(MediaPlayerListStatus.mutableMap.value) }
    // LaunchedEffect(Unit) {
    //     snapshotFlow { MediaPlayerListStatus.mutableMap.value } // 转为不可变 Map
    //         .collect { newMap ->
    //             println("test")
    //             map.value = newMap
    //         }
    // }

    MediaPlayerListStatus.mutableMap.value.forEach{ elem ->
        val mediaPlayer = elem.key
        println("tian_jia_JianTing")
        DisposableEffect(mediaPlayer){
            val listener = object : MediaPlayerEventAdapter() {
                override fun playing(mediaPlayer: MediaPlayer?) {
                    isAnyVideoPlaying.value = true
                }

                override fun paused(mediaPlayer: MediaPlayer?) {
                    var res = false
                    MediaPlayerListStatus.mutableMap.value.forEach {elem ->
                        if(elem.key.status().isPlaying) {
                            res = true
                        }
                    }
                    isAnyVideoPlaying.value = res
                }

                override fun finished(mediaPlayer: MediaPlayer) {
                    // FinishStatusMap.mutableMap[mediaPlayer as EmbeddedMediaPlayer] = true
                    MediaPlayerListStatus.mutableMap.value[mediaPlayer]?.isFinished = true
                    println("$mediaPlayer JieShu Le")

                    if(MediaPlayerListStatus.isAllFinished()){
                        isAnyVideoPlaying.value = false
                    }
                }
            }
            if(mediaPlayer.media().isValid) {
                mediaPlayer.events().addMediaPlayerEventListener(listener)
            }
            onDispose { mediaPlayer.events().removeMediaPlayerEventListener(listener) }
        }
    }
}

// fun List<EmbeddedMediaPlayer>.isAnyPlaying(): Boolean{
//     for(mediaPlayer in this){
//         val isSelected = MediaPlayerListStatus.mutableMap.value[mediaPlayer]?.isSelected
//         if(isSelected == true && mediaPlayer.status().isPlaying){
//             return true
//         }
//     }
//     return false
// }