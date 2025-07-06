@file:OptIn(ExperimentalFoundationApi::class)

package pers.lolicer.wotascope.components.bottomController

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.onClick
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.Dp
import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.media_fastforward
import wotascope.composeapp.generated.resources.media_pause
import wotascope.composeapp.generated.resources.media_play
import wotascope.composeapp.generated.resources.media_rewind
import wotascope.composeapp.generated.resources.media_skip_back_5
import wotascope.composeapp.generated.resources.media_skip_forward_10
import org.jetbrains.compose.resources.painterResource
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer

@Composable
fun BottomController(
    controllerHeight: Dp,
    mediaPlayerList: List<EmbeddedMediaPlayer>
){
    Column(
        modifier = Modifier
            .height(controllerHeight)
            .fillMaxWidth()
            .background(Color(43, 45, 48)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        PauseButton(mediaPlayerList)
    }
}

/**
 * 暂停/播放按钮。
 *
 * 点击逻辑如下：
 * ```
 * 有视频在播放：
 * 	结束的视频：do nothing
 * 	播放的视频：暂停
 * 	暂停的视频：do nothing
 * 无视频在播放：
 * 	全结束：
 * 		结束的视频：从头播放
 * 		播放的视频：无
 * 		暂停的视频：无
 * 	未全结束：
 * 		结束的视频：do nothing
 * 		播放的视频：无
 * 		暂停的视频：播放
 * ```
 *
 * 按钮变化逻辑如下：
 * ```
 * 已知：
 * 有视频在播放：`暂停`按钮
 * 无视频在播放：`播放`按钮
 * 且
 * 初始：`播放`按钮
 * 视频全结束：`播放`按钮
 *
 * 故：
 * 点击时变化
 * 监听结束视频个数，全结束则显示`播放`按钮
 * ```
 */
@Composable
fun PauseButton(
    mediaPlayerList: List<EmbeddedMediaPlayer>
){
    val mediaStates = remember(mediaPlayerList) {
        mediaPlayerList.map { mediaPlayer ->
            MediaState(
                mediaPlayer = mediaPlayer,
                isPlaying = false,
                isFinished = false
            )
        }
    }
    val isAnyVideoPlaying = remember { mutableStateOf(false) }

    Icon(
        modifier = Modifier
            .pointerHoverIcon(PointerIcon.Hand)
            .onClick{
                if(mediaPlayerList.isNotEmpty()){
                    if(mediaStates.isAnyVideoPlaying()){
                        println("isAnyVideoPlaying")
                        for(mediaState in mediaStates){
                            if(mediaState.isPlaying)
                                println("mediaState.isPlaying")
                            mediaState.mediaPlayer.controls().setPause(true)
                        }
                    }
                    else{
                        if(mediaStates.isAllVideoFinished()){
                            println("isAllVideoFinished")
                            for(mediaState in mediaStates){
                                mediaState.mediaPlayer.controls().play()
                                mediaState.mediaPlayer.controls().play()
                                mediaState.isFinished = false
                            }
                        }
                        else{
                            println("None of isAnyVideoPlaying/isAllVideoFinished")
                            for(mediaState in mediaStates){
                                if(!mediaState.isPlaying && !mediaState.isFinished){
                                    mediaState.mediaPlayer.controls().play()
                                }
                            }
                        }
                    }
                }

                isAnyVideoPlaying.value = !isAnyVideoPlaying.value
            },
        painter = painterResource(if(isAnyVideoPlaying.value) Res.drawable.media_pause else Res.drawable.media_play),
        contentDescription = if(isAnyVideoPlaying.value) "暂停" else "播放",
        tint = Color.White
    )

    for(mediaState in mediaStates){
        DisposableEffect(mediaState.mediaPlayer){
            val listener = object : MediaPlayerEventAdapter() {
                override fun paused(mediaPlayer: MediaPlayer) {
                    mediaState.isPlaying = false
                }
                override fun playing(mediaPlayer: MediaPlayer) {
                    mediaState.isPlaying = true
                }

                override fun finished(mediaPlayer: MediaPlayer) {
                    // coroutineScope.launch(Dispatchers.Main){
                    //     mediaPlayer.controls().play()
                    //     delay(100)
                    //     mediaPlayer.controls().setPause(true)
                    // }
                    mediaState.isPlaying = false
                    mediaState.isFinished = true

                    if(mediaStates.isAllVideoFinished()){
                        isAnyVideoPlaying.value = false
                    }
                }
            }
            mediaState.mediaPlayer.events().addMediaPlayerEventListener(listener)
            onDispose { mediaState.mediaPlayer.events().removeMediaPlayerEventListener(listener) }
        }
    }

    // LaunchedEffect(finishedVideoNum.value){
    //     if(finishedVideoNum.value >= mediaPlayerList.size){
    //         isPlaying.value = false
    //     }
    // }
    // LaunchedEffect(pausedVideoNum.value){
    //     if(pausedVideoNum.value >= mediaPlayerList.size){
    //         isPlaying.value = false
    //     }
    // }
}

@Composable
fun RewindButton(
    mediaPlayer: EmbeddedMediaPlayer
){
    Icon(painter = painterResource(Res.drawable.media_rewind), contentDescription = "后退一帧，暂未实现")
}

@Composable
fun FastForwardButton(
    mediaPlayer: EmbeddedMediaPlayer
){
    Icon(painter = painterResource(Res.drawable.media_fastforward), contentDescription = "前进一帧")
}

@Composable
fun SkipBackButton(
    mediaPlayer: EmbeddedMediaPlayer
){
    Icon(painter = painterResource(Res.drawable.media_skip_back_5), contentDescription = "快退五秒")
}

@Composable
fun SkipForwardButton(
    mediaPlayer: EmbeddedMediaPlayer
){
    Icon(painter = painterResource(Res.drawable.media_skip_forward_10), contentDescription = "快进十秒")
}

/**
 * 检查是否有视频在播放。
 *
 * 有视频在播放则返回`true`，否则返回`false`。
 */
fun List<MediaState>.isAnyVideoPlaying(): Boolean{
    for(mediaState in this){
        if(mediaState.isPlaying){
           return true
        }
    }
    return false
}

/**
 * 检查所有视频是否结束。
 *
 * 有视频未结束则返回`false`，否则返回`true`。
 */
fun List<MediaState>.isAllVideoFinished(): Boolean{
    for(mediaState in this){
        if(!mediaState.isFinished){
            return false
        }
    }
    return true
}