package pers.lolicer.wotascope.status

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
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
 * @param isSelected    视频是否在被选择
 * @param isFinished    视频是否已结束
 * @param volume        视频音量，与全局音量相乘得到输出音量
 * @param isMirrored    视频镜像状态
 * @param offset        视频位置（使用偏移量表示）
 * @param scale         视频缩放大小
 * @param alpha         视频透明度
 */
class Status(
    isSelected: Boolean = true,
    isFinished: Boolean = false,
    volume: Int = 100,
    isMirrored: Boolean = false,
    offset: Offset = Offset(0f, 0f),
    scale: Float = 1f,
    alpha: Float = 1f
) {
    // 内部使用 mutableStateOf 存储状态，保证响应式更新
    val isSelectedState = mutableStateOf(isSelected)
    var isSelected: Boolean by isSelectedState

    var isFinished: Boolean = isFinished

    var volume: Int = volume

    val isMirroredState = mutableStateOf(isMirrored)
    var isMirrored: Boolean by isMirroredState

    val offsetState = mutableStateOf(offset)
    var offset: Offset by offsetState

    val scaleState = mutableStateOf(scale)
    var scale: Float by scaleState

    val alphaState = mutableStateOf(alpha)
    var alpha: Float by alphaState
}

/**
 * 视频的选择状态。
 */
var EmbeddedMediaPlayer.isSelected: Boolean
    get() = MediaPlayerListStatus.list.value
        .first { it.first == this }
        .second.isSelected
    set(value){
        MediaPlayerListStatus.list.value
            .first { it.first == this }
            .second.isSelected = value
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
        .second.isMirrored
    set(value){
        MediaPlayerListStatus.list.value
            .first { it.first == this }
            .second.isMirrored = value
    }

/**
 * 视频位置。（用偏移量表示）
 */
var EmbeddedMediaPlayer.offset: Offset
    get() = MediaPlayerListStatus.list.value
        .first {it.first == this}
        .second.offset
    set(value){
        MediaPlayerListStatus.list.value
            .first{ it.first == this}
            .second.offset = value
    }

/**
 * 视频的缩放状态，限制在0.5f~3f。
 */
var EmbeddedMediaPlayer.scale: Float
    get() = MediaPlayerListStatus.list.value
        .first { it.first == this }
        .second.scale
    set(value){
        MediaPlayerListStatus.list.value
            .first { it.first == this }
            .second.scale = value.coerceIn(0.5f, 3f)
    }

/**
 * 视频透明度，限制在0f~1f，1f表示不透明。
 */
var EmbeddedMediaPlayer.alpha: Float
    get() = MediaPlayerListStatus.list.value
        .first { it.first == this }
        .second.alpha
    set(value){
        MediaPlayerListStatus.list.value
            .first { it.first == this }
            .second.alpha = value.coerceIn(0f, 1f)
    }

/**
 * 根据视频数量和是否重叠，返回每个视频应占据的宽高。
 */
fun MediaPlayerListStatus.selectSizeModifier(): Modifier?{
    return when(this.list.value.size){
        0 -> null
        1 -> Modifier
        2 -> if(OverlapStatus.status == OverlapState.OVERLAP) Modifier else Modifier.fillMaxSize(0.5f)
        3, 4 -> Modifier.fillMaxSize(0.5f)
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