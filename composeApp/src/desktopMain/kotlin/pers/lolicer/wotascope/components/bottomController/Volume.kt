package pers.lolicer.wotascope.components.bottomController

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.onClick
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import pers.lolicer.wotascope.components.videoStatus.AudioStatus
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer
import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.volume_0
import wotascope.composeapp.generated.resources.volume_1
import wotascope.composeapp.generated.resources.volume_2


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Volume(
    modifier: Modifier,
    mediaPlayerList: List<EmbeddedMediaPlayer>
){
    val isAllMute = remember { mutableStateOf(false) }
    val progress = remember { mutableStateOf(AudioStatus.globalVolumeProp) }

    // for(mediaPlayer in mediaPlayerList){
    //     print(mediaPlayer.audio().volume().toString() + ' ')
    // }
    // println()

    Row(
        modifier = Modifier.then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            modifier = Modifier.onClick{
                if(mediaPlayerList.isAllMute()){
                    mediaPlayerList.forEach {
                        it.audio().isMute = false
                        isAllMute.value = false
                    }
                }
                else{
                    mediaPlayerList.forEach {
                        it.audio().isMute = true
                        isAllMute.value = true
                    }
                }
            },
            painter =
                painterResource(resource =
                    if(isAllMute.value)
                        Res.drawable.volume_0
                    else
                        if(AudioStatus.globalVolumeProp <= 0.66)
                            Res.drawable.volume_1
                        else
                            Res.drawable.volume_2
                ),
            contentDescription = "音量开关",
            tint = Color.White
        )

        Slider(
            modifier = Modifier.height(4.dp),
            value = progress.value,
            onValueChange = {
                progress.value = it
                AudioStatus.globalVolumeProp = it
                for(mediaPlayer in mediaPlayerList){
                    val oldVolume = AudioStatus.mutableMap[mediaPlayer]!!
                    mediaPlayer.audio().setVolume((oldVolume * it).toInt())
                }
            },
            thumb = {Box{}},
            track = {
                Row(
                    modifier = Modifier
                        .clip(shape = RectangleShape)
                        .height(4.dp)
                ){
                    Box(modifier = Modifier.fillMaxWidth(progress.value).height(4.dp).background(color = Color(	0, 191, 255)))
                    Box(modifier = Modifier.fillMaxWidth().height(4.dp).background(color = Color(212, 212, 212)))
                }
            }
        )
    }
}

fun List<EmbeddedMediaPlayer>.isAllMute(): Boolean {
    this.forEach {
        if(!it.audio().isMute) return false
    }
    return true
}