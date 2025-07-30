package pers.lolicer.wotascope.components.singleVideoBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer
import kotlin.math.abs

import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.media_pause
import wotascope.composeapp.generated.resources.media_play
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.painterResource
import pers.lolicer.wotascope.components.utils.ExtensionUtils
import pers.lolicer.wotascope.components.videoStatus.ProgressStatus

@Composable
fun SingleVideoController(
    mediaPlayer: EmbeddedMediaPlayer
){
    ProgressBar(mediaPlayer)
}

@OptIn(ExperimentalMaterial3Api::class, InternalResourceApi::class)
@Composable
fun ProgressBar(
    mediaPlayer: EmbeddedMediaPlayer
){
    val progress = remember { mutableStateOf(mediaPlayer.status().position()) }
    val isDragging = remember { mutableStateOf(false) }

    Slider(
        modifier = Modifier
            .height(4.dp)
            .fillMaxWidth()
            .pointerHoverIcon(PointerIcon.Hand),
        thumb = { Box{} },
        track = {
            Row(
                modifier = Modifier
                    .clip(shape = RectangleShape)
                    .height(4.dp)
            ){
                Box(modifier = Modifier.fillMaxWidth(progress.value).height(4.dp).background(color = Color(	0, 191, 255)))
                Box(modifier = Modifier.fillMaxWidth().height(4.dp).background(color = Color.LightGray))
            }
        },
        value = progress.value,
        onValueChange = {
            progress.value = it
            isDragging.value = true
        },
        onValueChangeFinished = {
            if(!(abs(1f - progress.value) <= 1e-7)) // 没有拖到结尾
                mediaPlayer.controls().setPosition(progress.value)
            else // 拖到结尾，因为vlcj读不到 position=1f 或 time=length ，所以这样处理一下
                mediaPlayer.controls().setTime(mediaPlayer.status().length()-1)
            isDragging.value = false
        }
    )

    // 监听视频进度
    DisposableEffect(mediaPlayer){
        val listener = object : MediaPlayerEventAdapter() {
            override fun positionChanged(mediaPlayer: MediaPlayer, newPosition: Float) {
                if(!isDragging.value){
                    progress.value = newPosition
                }
            }

            override fun finished(mediaPlayer: MediaPlayer) {
                progress.value = 1f
            }
        }
        mediaPlayer.events().addMediaPlayerEventListener(listener)
        onDispose { mediaPlayer.events().removeMediaPlayerEventListener(listener) }
    }

    LaunchedEffect(Unit){
        snapshotFlow {ProgressStatus.value.value}
            .collect {
                progress.value = mediaPlayer.status().position()
            }
    }
}

@Composable
fun PauseButton(
    mediaPlayer: EmbeddedMediaPlayer
) {
    val isPlaying = remember { mutableStateOf(mediaPlayer.status().isPlaying) }

    Button(
        onClick = { ExtensionUtils().changePauseState(mediaPlayer) },
        modifier = Modifier.size(32.dp),
        colors = ButtonColors(
            containerColor = Color.Transparent,
            contentColor = Color.White,
            disabledContainerColor = Color.LightGray,
            disabledContentColor = Color.White
        )
    ){
        if(isPlaying.value){
            Image( painterResource(Res.drawable.media_pause),
                contentDescription = "Pause",
                modifier = Modifier.size(28.dp)
            )
        }
        else{
            Image(painterResource(Res.drawable.media_play),
                contentDescription = "Play",
                modifier = Modifier.size(28.dp)
            )
        }

        // 监听视频停止与播放以改变按钮样式
        DisposableEffect(mediaPlayer){
            val listener = object : MediaPlayerEventAdapter() {
                override fun playing(mediaPlayer: MediaPlayer?) {
                    isPlaying.value = true
                }
                override fun paused(mediaPlayer: MediaPlayer?) {
                    isPlaying.value = false
                }
            }
            mediaPlayer.events().addMediaPlayerEventListener(listener)
            onDispose { mediaPlayer.events().removeMediaPlayerEventListener(listener) }
        }
    }
}