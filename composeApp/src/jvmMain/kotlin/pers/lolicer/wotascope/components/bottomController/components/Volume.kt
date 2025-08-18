package pers.lolicer.wotascope.components.bottomController.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.onClick
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.painterResource
import pers.lolicer.wotascope.components.BiliSlider
import pers.lolicer.wotascope.status.MediaPlayerListStatus
import pers.lolicer.wotascope.status.isAllMute
import pers.lolicer.wotascope.status.volume
import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.volume_0
import wotascope.composeapp.generated.resources.volume_1
import wotascope.composeapp.generated.resources.volume_2


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Volume(
    modifier: Modifier
){
    val isAllMute = remember { mutableStateOf(false) }
    val progress = remember { mutableStateOf(MediaPlayerListStatus.globalVolumeProp) }

    Row(
        modifier = Modifier.then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            modifier = Modifier.onClick{
                if(MediaPlayerListStatus.isAllMute()){
                    MediaPlayerListStatus.list.value.forEach { elem ->
                        elem.first.audio().isMute = false
                        isAllMute.value = false
                    }
                }
                else{
                    MediaPlayerListStatus.list.value.forEach { elem ->
                        elem.first.audio().isMute = true
                        isAllMute.value = true
                    }
                }
            },
            painter =
                painterResource(resource =
                    if(isAllMute.value)
                        Res.drawable.volume_0
                    else
                        if(MediaPlayerListStatus.globalVolumeProp <= 0.66)
                            Res.drawable.volume_1
                        else
                            Res.drawable.volume_2
                ),
            contentDescription = "音量开关",
            tint = Color.White
        )

        BiliSlider(
            value = progress.value,
            onValueChange = {
                progress.value = it
                MediaPlayerListStatus.globalVolumeProp = it
                for(elem in MediaPlayerListStatus.list.value){
                    val mediaPlayer = elem.first
                    val oldVolume = mediaPlayer.volume
                    mediaPlayer.audio().setVolume((oldVolume * it).toInt())
                }
            },
            onValueChangeFinished = {},
        )
    }
}