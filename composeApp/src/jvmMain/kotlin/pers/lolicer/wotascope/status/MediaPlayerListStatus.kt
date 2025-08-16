package pers.lolicer.wotascope.status

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer

object MediaPlayerListStatus {
    val list: MutableState<List<Pair<EmbeddedMediaPlayer, Status>>> = mutableStateOf(listOf())

    private var _globalVolumeProp: Float = 1f
    var globalVolumeProp: Float
        get() = _globalVolumeProp
        set(value) {
            _globalVolumeProp = value.coerceIn(0f..1f)
        }

    var speed: Float = 1f
}
/**
 * 视频的状态。
 *
 * @param isSelected 视频是否在被选择
 * @param isFinished 视频是否已结束
 * @param volume 视频音量，与全局音量相乘得到输出音量
 */
class Status(
    val isSelected: MutableState<Boolean> = mutableStateOf(true),
    var isFinished: Boolean,
    var volume: Int,
    val isMirrored: MutableState<Boolean> = mutableStateOf(false)
)

/**
 * 视频的选择状态。
 */
var EmbeddedMediaPlayer.isSelected: Boolean
    get() = MediaPlayerListStatus.list.value
        .first { it.first == this }
        .second.isSelected.value
    set(value){
        MediaPlayerListStatus.list.value
            .first { it.first == this }
            .second.isSelected.value = value
    }

/**
 * 视频的完成状态。
 */
var EmbeddedMediaPlayer.isFinished: Boolean
    get() = MediaPlayerListStatus.list.value
        .first { it.first == this }
        .second.isFinished
    set(value){
        MediaPlayerListStatus.list.value
            .first { it.first == this }
            .second.isFinished = value
    }

/**
 * 视频的音量，与全局音量相乘得到输出音量。
 */
var EmbeddedMediaPlayer.volume: Int
    get() = MediaPlayerListStatus.list.value
        .first { it.first == this }
        .second.volume
    set(value){
        MediaPlayerListStatus.list.value
            .first { it.first == this }
            .second.volume = value
    }

/**
 * 视频的镜像状态。
 */
var EmbeddedMediaPlayer.isMirrored: Boolean
    get() = MediaPlayerListStatus.list.value
        .first { it.first == this }
        .second.isMirrored.value
    set(value){
        MediaPlayerListStatus.list.value
            .first { it.first == this }
            .second.isMirrored.value = value
    }

/**
 * 根据视频数量，返回每个视频应占据的宽高。
 */
fun MediaPlayerListStatus.selectSize(): Modifier?{
    return when(this.list.value.size){
        0 -> null
        1 -> Modifier
        2, 3, 4 -> Modifier.fillMaxSize(0.5f)
        5, 6, 7, 8, 9 -> Modifier.fillMaxSize(1/3f)
        else -> { throw Exception("程序错误：超出9个视频") }
    }
}

/**
 * 是否有被选择的视频正在播放。
 */
fun MediaPlayerListStatus.isAnyPlaying(): Boolean{
    var res = false
    for(elem in this.list.value){
        if(elem.first.isSelected && elem.first.status().isPlaying){
            res = true
            break
        }
    }
    return res
}

/**
 * 是否所有被选择的视频都已结束。
 */
fun MediaPlayerListStatus.isAllFinished(): Boolean{
    var res = true
    for(elem in this.list.value){
        if(elem.first.isSelected && !elem.first.isFinished){
            res = false
            break
        }
    }
    return res
}

/**
 * 是否所有被选择的视频都已静音。
 */
fun MediaPlayerListStatus.isAllMute(): Boolean{
    this.list.value.forEach { elem ->
        if(!elem.first.audio().isMute){
            return false
        }
    }
    return true
}