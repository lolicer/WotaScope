package pers.lolicer.wotascope.components.singleVideoPanel

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import org.jetbrains.compose.resources.painterResource
import pers.lolicer.wotascope.status.MediaPlayerListStatus
import pers.lolicer.wotascope.status.volume
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer
import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.media_close

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun FloatingMenu(
    modifier: Modifier,
    mediaPlayer: EmbeddedMediaPlayer,
    isHovered: Boolean,
    screenSize: IntSize
){
    val columnSize = IntSize((screenSize.width * 1/25), (screenSize.height * 3/10))
    // 左下 音量滑块
    val volume = remember{ mutableStateOf(mediaPlayer.audio().volume() ) }
    AnimatedVisibility(
        modifier = modifier
            .offset(y = (screenSize.height * 6/10).dp, x = 10.dp)
            .clip(RoundedCornerShape(20))
            .alpha(0.85f),
        visible = isHovered,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .height(columnSize.height.dp)
                .width(columnSize.width.dp)
                .background(Color.DarkGray)
                .padding(vertical = 10.dp)
        ){
            VerticalSlider(
                modifier = Modifier.fillMaxSize(),
                value = volume.value / 100f,
                onValueChange = { newProgress ->
                    if(mediaPlayer.status().isPlayable){
                        volume.value = (newProgress * 100f).toInt()
                        mediaPlayer.volume = volume.value
                        mediaPlayer.audio().setVolume((volume.value * MediaPlayerListStatus.globalVolumeProp).toInt())
                    }
                },
                sliderHeight = (columnSize.height - 20).dp,
                trackWidth = 4.dp
            )
        }
    }

    // 右上关闭选项
    AnimatedVisibility(
        // modifier = modifier.offset(x = (screenSize.width * 19/20 - 10).dp, y = 10.dp),
        modifier = modifier.offset(x = (screenSize.width - 50 - 10).dp, y = 10.dp),
        visible = isHovered,
        enter = slideInHorizontally { it } + fadeIn(),
        exit = slideOutHorizontally { it } + fadeOut()
    ) {
        Box(
            modifier = Modifier
                // .size((screenSize.width * 1/20).dp)
                // .background(Color(0xFF4CAF50))
                .clip(CircleShape)
                .pointerHoverIcon(PointerIcon.Hand)
                .onClick{
                    MediaPlayerListStatus.list.value = MediaPlayerListStatus.list.value.filterNot { it.first == mediaPlayer }
                    // mediaPlayer.release()
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(45.dp)
                    .zIndex(1f),
                painter = painterResource(Res.drawable.media_close),
                contentDescription = "移除此视频",
                tint = Color.Red
            )
            Spacer(modifier = Modifier
                .background(color = Color.White)
                .clip(CircleShape)
                .size(25.dp)
                .zIndex(0f)
                .border(width = 1.dp, color = Color.Blue)
            )
        }
    }

    LaunchedEffect(mediaPlayer.audio().volume()){
        val newVolume = mediaPlayer.audio().volume()
        volume.value = newVolume.coerceIn(0..100)
    }
}