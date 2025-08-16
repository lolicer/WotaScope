package pers.lolicer.wotascope.components.singleVideoPanel

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.onDrag
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.onClick
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asComposeImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.IntOffset
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.ColorAlphaType
import org.jetbrains.skia.ImageInfo
import pers.lolicer.wotascope.status.isMirrored
import pers.lolicer.wotascope.status.offset
import pers.lolicer.wotascope.status.scale
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer
import uk.co.caprica.vlcj.player.embedded.videosurface.CallbackVideoSurface
import uk.co.caprica.vlcj.player.embedded.videosurface.VideoSurfaceAdapters
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.BufferFormat
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.BufferFormatCallback
import uk.co.caprica.vlcj.player.embedded.videosurface.callback.format.RV32BufferFormat
import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.wotascope_icon
import java.nio.ByteBuffer

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun VideoDisplay(
    modifier: Modifier = Modifier,
    mediaPlayer: EmbeddedMediaPlayer,
    isHovered: Boolean
) {
    var videoFrame by remember { mutableStateOf<ImageBitmap?>(null) }

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        attachVideoSurface(mediaPlayer) { bitmap ->
            videoFrame = bitmap
        }
    }

    Box(
        modifier = Modifier
            .then(modifier)
            .fillMaxSize()
            .clipToBounds(),
        contentAlignment = Alignment.Center
    ) {
        if (videoFrame == null) {
            Image(
                painter = painterResource(Res.drawable.wotascope_icon),
                contentDescription = "Video",
                modifier = Modifier.fillMaxSize()
            )
        }
        else {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .focusable()
                    .focusRequester(focusRequester)
                    .graphicsLayer(
                        scaleX = if(mediaPlayer.isMirrored) (mediaPlayer.scale * -1) else mediaPlayer.scale,
                        scaleY = mediaPlayer.scale
                    )
                    .offset{
                        val x = mediaPlayer.offset.x.toInt()
                        val y = mediaPlayer.offset.y.toInt()

                        IntOffset(x, y)
                    }
                    .onDrag{
                        mediaPlayer.offset += it
                    }
                    .onClick{
                        focusRequester.requestFocus()
                        println("focus")
                    }
                    .onPointerEvent(PointerEventType.Scroll){
                        if(isHovered){
                            val zoomDelta = it.changes.first().scrollDelta.y
                            mediaPlayer.scale = (mediaPlayer.scale * (1f - zoomDelta * 0.1f)).coerceIn(0.5f, 3f) // 限制缩放范围
                        }
                    },
                bitmap = videoFrame!!,
                contentDescription = "Video"
            )
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }
}

private fun attachVideoSurface(
    mediaPlayer: EmbeddedMediaPlayer,
    onFrame: (ImageBitmap) -> Unit
) {
    var frameBuffer: ByteArray? = null
    var frameInfo: ImageInfo? = null

    val videoSurface = CallbackVideoSurface(
        object : BufferFormatCallback {
            override fun getBufferFormat(width: Int, height: Int): BufferFormat {
                frameInfo = ImageInfo.makeN32(width, height, ColorAlphaType.PREMUL)
                return RV32BufferFormat(width, height)
            }

            override fun allocatedBuffers(buffers: Array<out ByteBuffer>) {
                frameBuffer = ByteArray(buffers[0].capacity())
            }
        },
        { player, nativeBuffers, bufferFormat ->
            val buffer = nativeBuffers[0]
            frameBuffer?.let { bytes ->
                buffer.get(bytes)
                buffer.rewind()

                val skiaBitmap = Bitmap()
                skiaBitmap.allocPixels(frameInfo!!)
                skiaBitmap.installPixels(bytes)

                onFrame(skiaBitmap.asComposeImageBitmap())
            }
        },
        true,
        VideoSurfaceAdapters.getVideoSurfaceAdapter()
    )

    mediaPlayer.videoSurface().set(videoSurface)
    // mediaPlayer.controls().play()
    println("set videoSurface")
}
