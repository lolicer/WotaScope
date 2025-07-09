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
import uk.co.caprica.vlcj.player.base.MediaPlayer
import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.volume_0
import wotascope.composeapp.generated.resources.volume_1
import wotascope.composeapp.generated.resources.volume_2


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Volume(
    modifier: Modifier,
    mediaPlayerList: List<MediaPlayer>
){
    val volumeSize = remember { mutableStateOf(1f) }

    for(mediaPlayer in mediaPlayerList){
        print(mediaPlayer.audio().volume().toString() + ' ')
    }

    Row(
        modifier = Modifier.then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            modifier = Modifier.onClick{
                if(volumeSize.value > 0 + 1e-7){
                    for(mediaPlayer in mediaPlayerList){
                        mediaPlayer.audio().setVolume(0)
                    }
                    volumeSize.value = 0f
                }
            },
            painter =
                painterResource(resource =
                    if(volumeSize.value <= 0 + 1e-7)
                        Res.drawable.volume_0
                    else
                        if(volumeSize.value <= 0.66)
                            Res.drawable.volume_1
                        else
                            Res.drawable.volume_2
                ),
            contentDescription = "音量开关",
            tint = Color.White
        )

        Slider(
            modifier = Modifier.height(4.dp),
            value = volumeSize.value,
            onValueChange = {
                volumeSize.value = it
                for(mediaPlayer in mediaPlayerList){
                    mediaPlayer.audio().setVolume((volumeSize.value * 100).toInt())
                }
            },
            thumb = {Box{}},
            track = {
                Row(
                    modifier = Modifier
                        .clip(shape = RectangleShape)
                        .height(4.dp)
                ){
                    Box(modifier = Modifier.fillMaxWidth(volumeSize.value).height(4.dp).background(color = Color(	0, 191, 255)))
                    Box(modifier = Modifier.fillMaxWidth().height(4.dp).background(color = Color(212, 212, 212)))
                }
            }
        )
    }
}
