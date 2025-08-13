package pers.lolicer.wotascope.components.singleVideoPanel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import pers.lolicer.wotascope.status.isSelected
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressBar(
    mediaPlayer: EmbeddedMediaPlayer
){
    val progressValue = remember{ mutableStateOf(mediaPlayer.status().position()) }
    val isDragging = remember { mutableStateOf(false) }

    Slider(
        modifier = Modifier
            .height(4.dp)
            .fillMaxWidth()
            .pointerHoverIcon(PointerIcon.Hand),
        enabled = mediaPlayer.isSelected,
        thumb = { Box{} },
        track = {
            Row(
                modifier = Modifier
                    .clip(shape = RectangleShape)
                    .height(4.dp)
            ){
                Box(modifier = Modifier.fillMaxWidth(progressValue.value).height(4.dp).background(color = Color(	0, 191, 255)))
                Box(modifier = Modifier.fillMaxWidth().height(4.dp).background(color = Color.LightGray))
            }
        },
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