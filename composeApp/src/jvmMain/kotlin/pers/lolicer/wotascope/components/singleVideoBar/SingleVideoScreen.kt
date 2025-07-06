package pers.lolicer.wotascope.components.singleVideoBar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.onClick
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SingleVideoScreen(
    mediaPlayerComponent: EmbeddedMediaPlayerComponent,
    mediaPlayer: EmbeddedMediaPlayer,
    url: String,
    isSelect: MutableState<Boolean>
) {

    val factory = remember { { mediaPlayerComponent } }

    val screenWidth = with(LocalDensity.current) {
        LocalWindowInfo.current.containerSize.width.toDp()
    }
    val screenHeight = with(LocalDensity.current) {
        LocalWindowInfo.current.containerSize.height.toDp()
    }


    Box(
        Modifier
            .fillMaxWidth()
            .height(screenHeight- 4.dp)
            .onClick{
                isSelect.value = !isSelect.value
            }
    ){
        SwingPanel(
            factory = factory,
            background = Color.Transparent,
            modifier = Modifier.fillMaxSize(),
            update = { swingComponent ->
            }
        )

        LaunchedEffect(Unit){
            mediaPlayer.media().startPaused(url)
        }
    }

    DisposableEffect(mediaPlayer){
        val listener = object : MediaPlayerEventAdapter() {
            override fun mediaPlayerReady(mediaPlayer: MediaPlayer?) {
            }
        }
        mediaPlayer.events().addMediaPlayerEventListener(listener)
        onDispose { mediaPlayer.events().removeMediaPlayerEventListener(listener) }
    }

    DisposableEffect(Unit) {
        onDispose(mediaPlayer::release)
    }
}

/*
LaunchedEffect(Unit){
    if(!isVideoLoaded.value){
        coroutineScope.launch(Dispatchers.Unconfined){
            mediaPlayer.media().startPaused(url)
            mediaPlayer.controls().repeat = true

            while(!mediaPlayer.status().isPlayable){
                println("waiting...")
                delay(10)
            }
            isVideoLoaded.value = true

            val oldVolume = mediaPlayer.audio().volume()
            mediaPlayer.audio().setVolume(0)
            mediaPlayer.controls().play()
            // delay(100)
            mediaPlayer.controls().setPause(true)
            mediaPlayer.audio().setVolume(oldVolume)
        }
    }
}
*/
