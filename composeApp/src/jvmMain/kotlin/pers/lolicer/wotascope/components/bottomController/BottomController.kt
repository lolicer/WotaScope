@file:OptIn(ExperimentalFoundationApi::class)

package pers.lolicer.wotascope.components.bottomController

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.onClick
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.media_fastforward
import wotascope.composeapp.generated.resources.media_pause
import wotascope.composeapp.generated.resources.media_play
import wotascope.composeapp.generated.resources.media_rewind
import wotascope.composeapp.generated.resources.media_skip_back_5
import wotascope.composeapp.generated.resources.media_skip_forward_10
import org.jetbrains.compose.resources.painterResource
import pers.lolicer.wotascope.components.selectStatusMap.SelectStatusMap
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer
import wotascope.composeapp.generated.resources.volume_0
import wotascope.composeapp.generated.resources.volume_1
import wotascope.composeapp.generated.resources.volume_2

@Composable
fun BottomController(
    controllerHeight: Dp,
    mediaPlayerList: List<EmbeddedMediaPlayer>
){
    Row(
        modifier = Modifier
            .height(controllerHeight)
            .fillMaxWidth()
            .background(Color(43, 45, 48)),
        verticalAlignment = Alignment.CenterVertically
    ){
        Spacer(Modifier.weight(0.5f))
        Volume(Modifier.weight(1f), mediaPlayerList)
        Spacer(Modifier.weight(2f))
        Row(
            modifier = Modifier.weight(3f),
            horizontalArrangement = Arrangement.Center
        ){
            PauseButton(Modifier, mediaPlayerList)
        }
        Spacer(Modifier.weight(3.5f))
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
    modifier: Modifier,
    mediaPlayerList: List<EmbeddedMediaPlayer>
){
    val finishedStatusMap = mutableMapOf<EmbeddedMediaPlayer, MutableState<Boolean>>().apply{
        println("PauseButton Recombined.")
        mediaPlayerList.forEach{ mediaPlayer ->
            // 现在每次点击SingleVideoPanel都会让list里面多一条点击的mediaPlayer，没找着为什么，有空再找找。 2025.7.7 17:27
            // 知道了，因为SingleVideoPanelItem重组导致onMediaPlayer被多次调用，导致Layout中的mediaPlayerList.add(mediaPlayer)被多次调用，已经解决 2025.7.7 17:56
            this.putIfAbsent(mediaPlayer, remember{mutableStateOf(false)})
        }
    }
    // println("mapSize: ${finishedStatusMap.values}")
    val isAnyVideoPlaying = remember { mutableStateOf(false) }

    // 这段代码在每次界面重组时运行，防止“添加视频引发的页面重组”导致的isAnyVideoPlaying未更新为false的问题。
    // 后续想办法优化一下，每次重组都运行不太好，应该改掉。
    var res = false
    for(mediaPlayer in mediaPlayerList){
        if(SelectStatusMap.mutableMap[mediaPlayer] == true && mediaPlayer.status().isPlaying){
            println("isAnyVideoPlaying")
            res = true
            break
        }else{
            println("noVideoPlaying")
        }
    }
    isAnyVideoPlaying.value = res
    // println("Check isAnyVideoPlaying")

    /*
    // val mediaStates = remember(mediaPlayerList) {
    //     mediaPlayerList.map { mediaPlayer ->
    //         MediaState(
    //             mediaPlayer = mediaPlayer,
    //             isPlaying = false,
    //             isFinished = false,
    //             isSelected = true
    //         )
    //     }
    // }
    // val isAnyVideoPlaying = remember { mutableStateOf(false) }
*/

    Icon(
        modifier = Modifier
            .then(modifier)
            .pointerHoverIcon(PointerIcon.Hand)
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
                        if(finishedStatusMap.isAllFinished()){
                            println("isAllVideoFinished")
                            for(mediaPlayer in mediaPlayerList){
                                if(SelectStatusMap.mutableMap[mediaPlayer] == true){
                                    mediaPlayer.controls().play()
                                    mediaPlayer.controls().play()
                                    finishedStatusMap[mediaPlayer]!!.value = false
                                }
                            }
                        }
                        else{
                            println("None of isAnyVideoPlaying/isAllVideoFinished")
                            for(mediaPlayer in mediaPlayerList){
                                if(SelectStatusMap.mutableMap[mediaPlayer] == true && !mediaPlayer.status().isPlaying && !finishedStatusMap[mediaPlayer]!!.value){
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

                override fun finished(mediaPlayer: MediaPlayer) {
                    finishedStatusMap[mediaPlayer]!!.value = true
                    println("$mediaPlayer JieShu Le")
                    if(finishedStatusMap.isAllFinished()){
                        isAnyVideoPlaying.value = false
                    }
                }
            }
            mediaPlayer.events().addMediaPlayerEventListener(listener)
            onDispose { mediaPlayer.events().removeMediaPlayerEventListener(listener) }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Volume(
    modifier: Modifier,
    mediaPlayerList: List<EmbeddedMediaPlayer>
){
    val volumeSize = remember { mutableStateOf(1f) }

    for(mediaPlayer in mediaPlayerList){
        print(mediaPlayer.audio().volume().toString() + ' ')
    }

    Row(
        modifier = Modifier.then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            modifier = Modifier.onClick{
                if(volumeSize.value > 0 + 1e-7){
                    for(mediaPlayer in mediaPlayerList){
                        mediaPlayer.audio().setVolume(0)
                    }
                    volumeSize.value = 0f
                }
            },
            painter =
                painterResource(resource =
                    if(volumeSize.value <= 0 + 1e-7)
                        Res.drawable.volume_0
                    else
                        if(volumeSize.value <= 0.66)
                            Res.drawable.volume_1
                        else
                            Res.drawable.volume_2
                ),
            contentDescription = "音量开关",
            tint = Color.White
        )

        Slider(
            modifier = Modifier.height(4.dp),
            value = volumeSize.value,
            onValueChange = {
                volumeSize.value = it
                for(mediaPlayer in mediaPlayerList){
                    mediaPlayer.audio().setVolume((volumeSize.value * 100).toInt())
                }
            },
            thumb = {Box{}},
            track = {
                Row(
                    modifier = Modifier
                        .clip(shape = RectangleShape)
                        .height(4.dp)
                ){
                    Box(modifier = Modifier.fillMaxWidth(volumeSize.value).height(4.dp).background(color = Color(	0, 191, 255)))
                    Box(modifier = Modifier.fillMaxWidth().height(4.dp).background(color = Color(212, 212, 212)))
                }
            }
        )
    }
}

fun List<EmbeddedMediaPlayer>.isAnyPlaying(): Boolean{
    for(mediaPlayer in this){
        if(SelectStatusMap.mutableMap[mediaPlayer] == true && mediaPlayer.status().isPlaying){
            return true
        }
    }
    return false
}

fun MutableMap<EmbeddedMediaPlayer, MutableState<Boolean>>.isAllFinished(): Boolean{
    for(finishStatus in this){
        if(SelectStatusMap.mutableMap[finishStatus.key] == true && !finishStatus.value.value){
            return false
        }
    }

    println("Dou JieShu Le")
    return true

    // AI说写下面一行就行了，看不懂，先不这么写
    // return values.all{it.value}
}

// /**
//  * 检查是否有视频在播放。
//  *
//  * 有视频在播放则返回`true`，否则返回`false`。
//  */
// fun List<MediaState>.isAnyVideoPlaying(): Boolean{
//     for(mediaState in this){
//         if(mediaState.isPlaying){
//            return true
//         }
//     }
//     return false
// }
//
// /**
//  * 检查所有视频是否结束。
//  *
//  * 有视频未结束则返回`false`，否则返回`true`。
//  */
// fun List<MediaState>.isAllVideoFinished(): Boolean{
//     for(mediaState in this){
//         if(!mediaState.isFinished){
//             return false
//         }
//     }
//     return true
// }