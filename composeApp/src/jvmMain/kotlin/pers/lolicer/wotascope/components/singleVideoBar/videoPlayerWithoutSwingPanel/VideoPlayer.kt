package pers.lolicer.wotascope.components.singleVideoBar.videoPlayerWithoutSwingPanel

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import uk.co.caprica.vlcj.factory.MediaPlayerFactory
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.BufferFormatCallback
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.format.RV32BufferFormat
import java.nio.ByteBuffer
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.onClick
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeImageBitmap
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.ColorAlphaType
import org.jetbrains.skia.ImageInfo
import pers.lolicer.wotascope.components.videoStatus.SelectStatusMap
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer
import uk.co.caprica.vlcj.player.embedded.videosurface.CallbackVideoSurface
import uk.co.caprica.vlcj.player.embedded.videosurface.VideoSurfaceAdapters
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.BufferFormat
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.RenderCallback

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    mrl: String,
    mediaPlayer: MutableState<EmbeddedMediaPlayer?>,
    isSelected: MutableState<Boolean>
) {
    var imageBitmap by remember(mrl) { mutableStateOf<ImageBitmap?>(null) }
    var isPlayerReady by remember(mrl) { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .then(modifier)
            .fillMaxSize()
            .onClick{
                isSelected.value = !isSelected.value
            },
        contentAlignment = Alignment.Center
    ){
        if (isPlayerReady) {
            imageBitmap?.let {
                Image(
                    bitmap = it,
                    contentDescription = "Video",
                    modifier = Modifier.fillMaxSize()
                )
            } ?: run {
                Box(modifier = Modifier.fillMaxSize().background(Color.Gray))
            }
        } else {
            Box(modifier = Modifier.fillMaxSize().background(Color.Gray))
        }
    }

    mediaPlayer.value = remember(mrl) {
        var byteArray: ByteArray? = null
        var info: ImageInfo? = null
        val factory = MediaPlayerFactory()
        val embeddedMediaPlayer = factory.mediaPlayers().newEmbeddedMediaPlayer()
        val callbackVideoSurface = CallbackVideoSurface(
            object : BufferFormatCallback {
                override fun getBufferFormat(sourceWidth: Int, sourceHeight: Int): BufferFormat {
                    info = ImageInfo.makeN32(sourceWidth, sourceHeight, ColorAlphaType.OPAQUE)
                    return RV32BufferFormat(sourceWidth, sourceHeight)
                }

                override fun allocatedBuffers(buffers: Array<out ByteBuffer>) {
                    byteArray = ByteArray(buffers[0].limit())
                }
            },
            object : RenderCallback {
                var pos: Float = -1f

                override fun display(
                    mediaPlayer: MediaPlayer,
                    nativeBuffers: Array<out ByteBuffer>,
                    bufferFormat: BufferFormat?
                ) {
                    if (!mediaPlayer.status().isPlaying && pos == mediaPlayer.status().position()) {
                        return
                    }
                    pos = mediaPlayer.status().position()

                    val byteBuffer = nativeBuffers[0]
                    byteBuffer.get(byteArray)
                    byteBuffer.rewind()

                    val bmp = Bitmap()
                    bmp.allocPixels(info!!)
                    bmp.installPixels(byteArray)
                    imageBitmap = bmp.asComposeImageBitmap()
                }
            },
            true,
            VideoSurfaceAdapters.getVideoSurfaceAdapter()
        )
        embeddedMediaPlayer.videoSurface().set(callbackVideoSurface)
        embeddedMediaPlayer
    }

    LaunchedEffect(Unit){
        mediaPlayer.value!!.media().startPaused(mrl)
        isPlayerReady = true
        SelectStatusMap.mutableMap.putIfAbsent(mediaPlayer.value!!, true)
    }

    DisposableEffect(mediaPlayer) {
        val listener = object : MediaPlayerEventAdapter() {
            override fun mediaPlayerReady(mediaPlayer: MediaPlayer) {
            }
        }
        mediaPlayer.value!!.events().addMediaPlayerEventListener(listener)
        onDispose {
            mediaPlayer.value!!.events().removeMediaPlayerEventListener(listener)
            mediaPlayer.value!!.release()
        }
    }
}