package pers.lolicer.wotascope.components.bottomController

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.onClick
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import pers.lolicer.wotascope.components.videoStatus.FinishStatusMap
import pers.lolicer.wotascope.components.videoStatus.SelectStatusMap
import pers.lolicer.wotascope.components.videoStatus.isAllFinished
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer
import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.media_pause
import wotascope.composeapp.generated.resources.media_play
import kotlin.collections.forEach


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
@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun PauseButton(
    modifier: Modifier,
    mediaPlayerList: List<EmbeddedMediaPlayer>,
    isAnyVideoPlaying: MutableState<Boolean>
){
    val scope = rememberCoroutineScope()
    // 这段代码在每次界面重组时运行，防止“添加视频引发的页面重组”导致的isAnyVideoPlaying未更新为false的问题。
    // 后续想办法优化一下，每次重组都运行不太好，应该改掉。
    var res = false
    for(mediaPlayer in mediaPlayerList){
        if(SelectStatusMap.mutableMap[mediaPlayer] == true && mediaPlayer.status().isPlaying){
            println("isAnyVideoPlaying")
            res = true
            break
        }
    }
    isAnyVideoPlaying.value = res


    var active by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .background(color = if(!active) Color.Transparent else Color(56, 58, 61)),
        contentAlignment = Alignment.Center
    ){
        Icon(
            modifier = Modifier
                .pointerHoverIcon(PointerIcon.Hand)
                .onPointerEvent(PointerEventType.Enter) { active = true }
                .onPointerEvent(PointerEventType.Exit) { active = false }
                .size(32.dp)
                .onClick{
                    if(mediaPlayerList.isNotEmpty()){
                        if(mediaPlayerList.isAnyPlaying()){
                            println("isAnyVideoPlaying")
                            for(mediaPlayer in mediaPlayerList){
                                if(SelectStatusMap.mutableMap[mediaPlayer] == true){
                                    // if(mediaPlayer.status().isPlaying) println("$mediaPlayer isPlaying")
                                    mediaPlayer.controls().setPause(true)
                                }
                            }
                        }
                        else{
                            if(FinishStatusMap.isAllFinished()){
                                println("isAllVideoFinished")
                                for(mediaPlayer in mediaPlayerList){
                                    if(SelectStatusMap.mutableMap[mediaPlayer] == true){
                                        mediaPlayer.controls().play()
                                        mediaPlayer.controls().play()
                                        FinishStatusMap.mutableMap[mediaPlayer] = false
                                    }
                                }
                            }
                            else{
                                println("None of isAnyVideoPlaying/isAllVideoFinished")
                                for(mediaPlayer in mediaPlayerList){
                                    if(SelectStatusMap.mutableMap[mediaPlayer] == true && !mediaPlayer.status().isPlaying && !FinishStatusMap.mutableMap[mediaPlayer]!!){
                                        mediaPlayer.controls().play()
                                    }
                                }
                            }
                        }
                    }
                    // 下面的监听好像可以控制状态了，这行先注释掉
                    // isAnyVideoPlaying.value = !isAnyVideoPlaying.value
                },
            painter = painterResource(if(isAnyVideoPlaying.value) Res.drawable.media_pause else Res.drawable.media_play),
            contentDescription = if(isAnyVideoPlaying.value) "暂停" else "播放",
            tint = Color.White
        )
    }

    for(mediaPlayer in mediaPlayerList){
        DisposableEffect(mediaPlayer){
            val listener = object : MediaPlayerEventAdapter() {
                override fun playing(mediaPlayer: MediaPlayer?) {
                    isAnyVideoPlaying.value = true
                }

                override fun paused(mediaPlayer: MediaPlayer?) {
                    var res = false
                    mediaPlayerList.forEach { mediaPlayer ->
                        if(mediaPlayer.status().isPlaying){
                            res = true
                        }
                    }
                    isAnyVideoPlaying.value = res
                }
            }
            mediaPlayer.events().addMediaPlayerEventListener(listener)
            onDispose { mediaPlayer.events().removeMediaPlayerEventListener(listener) }
        }
    }
}
