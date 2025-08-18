package pers.lolicer.wotascope.components.singleVideoPanel.floatingMenu.menu.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pers.lolicer.wotascope.components.BiliSlider
import pers.lolicer.wotascope.status.MediaPlayerListStatus
import pers.lolicer.wotascope.status.isFinished
import pers.lolicer.wotascope.status.volume
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer

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

            BiliSlider(
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
                },
                onValueChangeFinished = {}
            )
        }
    }
}