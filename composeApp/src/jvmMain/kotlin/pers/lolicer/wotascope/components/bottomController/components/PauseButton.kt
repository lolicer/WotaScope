package pers.lolicer.wotascope.components.bottomController.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.onClick
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import org.jetbrains.compose.resources.painterResource
import pers.lolicer.wotascope.status.isAllFinished
import pers.lolicer.wotascope.status.isAnyPlaying
import pers.lolicer.wotascope.status.MediaPlayerListStatus
import pers.lolicer.wotascope.status.isFinished
import pers.lolicer.wotascope.status.isSelected
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer
import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.media_pause
import wotascope.composeapp.generated.resources.media_play
import java.net.URLDecoder
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
    // mediaPlayerList: List<EmbeddedMediaPlayer>,
    // isAnyVideoPlaying: MutableState<Boolean>
){
    val isAnyVideoPlaying = remember { mutableStateOf(false) }

    // 这段代码在每次界面重组时运行，防止“添加视频引发的页面重组”导致的isAnyVideoPlaying未更新为false的问题。
    // 后续想办法优化一下，每次重组都运行不太好，应该改掉。
    var res = false
    // for(elem in MediaPlayerListStatus.mutableMap.value){
    //     val mediaPlayer = elem.key
    //     val isSelected = MediaPlayerListStatus.mutableMap.value[mediaPlayer]?.isSelected
    //     println(MediaPlayerListStatus.mutableMap.value.keys)
    //     if(isSelected == true && mediaPlayer.status().isPlaying){
    //         println("isAnyVideoPlaying")
    //         res = true
    //         break
    //     }
    // }
    for(elem in MediaPlayerListStatus.list.value){
        val mediaPlayer = elem.first
        val isSelected = mediaPlayer.isSelected
        if(isSelected && mediaPlayer.status().isPlaying){
            res  =true
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
                    if(MediaPlayerListStatus.list.value.isNotEmpty()){
                        if(MediaPlayerListStatus.isAnyPlaying()) {
                            println("isAnyVideoPlaying")
                            for(elem in MediaPlayerListStatus.list.value) {
                                val mediaPlayer = elem.first
                                val isSelected = mediaPlayer.isSelected
                                if(isSelected) {
                                    mediaPlayer.controls().setPause(true)
                                }
                            }
                        } else {
                            if(MediaPlayerListStatus.isAllFinished()) {
                                println("isAllVideoFinished")
                                for(elem in MediaPlayerListStatus.list.value) {
                                    val mediaPlayer = elem.first
                                    val isSelected = mediaPlayer.isSelected
                                    if(isSelected) {
                                        mediaPlayer.controls().play()
                                        mediaPlayer.isFinished = false
                                    }
                                }
                            } else {
                                println("None of isAnyVideoPlaying/isAllVideoFinished")
                                for(elem in MediaPlayerListStatus.list.value) {
                                    val mediaPlayer = elem.first
                                    val isSelected = mediaPlayer.isSelected
                                    val isFinished = mediaPlayer.isFinished

                                    if(isSelected && !mediaPlayer.status().isPlaying && !isFinished) {
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

        MediaPlayerListStatus.list.value.forEach{ elem ->
            val mediaPlayer = elem.first
            // println("tian_jia_JianTing")
            DisposableEffect(mediaPlayer){
                val listener = object : MediaPlayerEventAdapter() {
                    override fun playing(mediaPlayer: MediaPlayer?) {
                        isAnyVideoPlaying.value = true
                    }

                    override fun paused(mediaPlayer: MediaPlayer?) {
                        var res = false
                        MediaPlayerListStatus.list.value.forEach {elem ->
                            if(elem.first.status().isPlaying) {
                                res = true
                            }
                        }
                        isAnyVideoPlaying.value = res
                    }

                    override fun finished(mediaPlayer: MediaPlayer) {
                        val embeddedMediaPlayer = mediaPlayer as EmbeddedMediaPlayer
                        embeddedMediaPlayer.isFinished = true

                        val name = mediaPlayer.media().info().mrl().substringAfterLast("/")
                        println("${URLDecoder.decode(name, "UTF-8")} 结束了。")

                        if(MediaPlayerListStatus.isAllFinished()){
                            isAnyVideoPlaying.value = false
                        }
                    }
                }
                if(mediaPlayer.media().isValid) {
                    mediaPlayer.events().addMediaPlayerEventListener(listener)
                }
                onDispose { mediaPlayer.events().removeMediaPlayerEventListener(listener) }
            }
        }
    }

    // for(elem in MediaPlayerListStatus.mutableMap.value){
    //     val mediaPlayer = elem.key
    //     DisposableEffect(mediaPlayer) {
    //         val listener = object: MediaPlayerEventAdapter() {
    //         }
    //         mediaPlayer.events().addMediaPlayerEventListener(listener)
    //         onDispose {mediaPlayer.events().removeMediaPlayerEventListener(listener)}
    //     }
    // }
}
