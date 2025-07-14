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
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pers.lolicer.wotascope.components.videoStatus.SelectStatusMap
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer

@Composable
fun BottomController(
    controllerHeight: Dp,
    mediaPlayerList: List<EmbeddedMediaPlayer>
){
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
            PauseButton(Modifier.size(controllerHeight), mediaPlayerList)
            FastForwardButton(Modifier.size(controllerHeight), mediaPlayerList)
            SkipForwardButton(Modifier.size(controllerHeight), mediaPlayerList)
        }
        SpeedButton(Modifier.size(controllerHeight))
        Spacer(Modifier.width((controllerHeight.value * 0.5).dp))
    }
}

fun List<MediaPlayer>.isAnyPlaying(): Boolean{
    for(mediaPlayer in this){
        if(SelectStatusMap.mutableMap[mediaPlayer] == true && mediaPlayer.status().isPlaying){
            return true
        }
    }
    return false
}

fun MutableMap<MediaPlayer, MutableState<Boolean>>.isAllFinished(): Boolean{
    for(finishStatus in this){
        if(SelectStatusMap.mutableMap[finishStatus.key] == true && !finishStatus.value.value){
            return false
        }
    }

    println("Dou JieShu Le")
    return true

    // AI说写下面一行就行了，看不懂，先不这么写
    // return values.all{it.value}
}