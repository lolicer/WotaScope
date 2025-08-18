package pers.lolicer.wotascope.components.singleVideoPanel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import pers.lolicer.wotascope.components.BiliSlider
import pers.lolicer.wotascope.status.isSelected
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer

@Composable
fun ProgressBar(
    mediaPlayer: EmbeddedMediaPlayer
){
    val progressValue = remember{ mutableStateOf(mediaPlayer.status().position()) }
    val isDragging = remember { mutableStateOf(false) }

    BiliSlider(
        enabled = mediaPlayer.isSelected,
        value = progressValue.value,
        onValueChange = {
            progressValue.value = it
            isDragging.value = true
        },
        onValueChangeFinished = {
            val position = progressValue.value.coerceAtMost(1f)
            mediaPlayer.controls().setPosition(position)

            isDragging.value = false
        }
    )

    DisposableEffect(mediaPlayer){
        val listener = object : MediaPlayerEventAdapter() {
            override fun positionChanged(mediaPlayer: MediaPlayer, newPosition: Float) {
                if(!isDragging.value){
                    progressValue.value = newPosition
                }
            }
        }
        mediaPlayer.events().addMediaPlayerEventListener(listener)
        onDispose { mediaPlayer.events().removeMediaPlayerEventListener(listener) }
    }
}