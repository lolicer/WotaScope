package pers.lolicer.wotascope.components.singleVideoPanel.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Slider
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pers.lolicer.wotascope.status.MediaPlayerListStatus
import pers.lolicer.wotascope.status.isFinished
import pers.lolicer.wotascope.status.volume
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VolumeItem(
    mediaPlayer: EmbeddedMediaPlayer,
    height: Dp
){
    val progressValue = remember{ mutableStateOf(mediaPlayer.volume) }
    if(mediaPlayer.status().isPlayable || mediaPlayer.isFinished){
        Row(
            modifier = Modifier.height(height),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "音量",
                color = Color.White
            )

            Spacer(modifier = Modifier.width(8.dp))

            Slider(
                modifier = Modifier
                    .pointerHoverIcon(PointerIcon.Hand)
                    .width(height * 4),
                thumb = {},
                track = {
                    Row(
                        modifier = Modifier
                            .clip(shape = RectangleShape)
                            .height(4.dp)
                    ){
                        Box(modifier = Modifier.fillMaxWidth(progressValue.value / 100f).height(4.dp).background(color = Color(	0, 191, 255)))
                        Box(modifier = Modifier.fillMaxWidth().height(4.dp).background(color = Color.LightGray))
                    }
                },
                value = progressValue.value / 100f,
                onValueChange = {
                    progressValue.value = (it * 100f).toInt().coerceAtMost(100)

                    if(mediaPlayer.status().isPlayable){
                        mediaPlayer.volume = progressValue.value
                        mediaPlayer.audio().setVolume((progressValue.value * MediaPlayerListStatus.globalVolumeProp).toInt())
                    }
                    else{
                        println("暂时无法更改此视频音量，将延迟更改。")
                    }
                }
            )
        }
    }
}