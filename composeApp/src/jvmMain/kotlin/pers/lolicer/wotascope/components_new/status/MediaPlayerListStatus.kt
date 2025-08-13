package pers.lolicer.wotascope.components_new.status

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
}

class Status(
    var isSelected: Boolean,
    var isFinished: Boolean,
    var volume: Int
)

// fun EmbeddedMediaPlayer.isSelected(): Boolean{
//     return MediaPlayerListStatus.list.value
//         .first { it.first == this }
//         .second.isSelected
// }
//
// fun EmbeddedMediaPlayer.isFinished(): Boolean{
//     return MediaPlayerListStatus.list.value
//         .first { it.first == this }
//         .second.isFinished
// }

var EmbeddedMediaPlayer.isSelected: Boolean
    get() = MediaPlayerListStatus.list.value
        .first { it.first == this }
        .second.isSelected
    set(value){
        MediaPlayerListStatus.list.value
            .first { it.first == this }
            .second.isSelected = value
    }

var EmbeddedMediaPlayer.isFinished: Boolean
    get() = MediaPlayerListStatus.list.value
        .first { it.first == this }
        .second.isFinished
    set(value){
        MediaPlayerListStatus.list.value
            .first { it.first == this }
            .second.isFinished = value
    }

var EmbeddedMediaPlayer.volume: Int
    get() = MediaPlayerListStatus.list.value
        .first { it.first == this }
        .second.volume
    set(value){
        MediaPlayerListStatus.list.value
            .first { it.first == this }
            .second.volume = value
    }

fun MediaPlayerListStatus.selectSize(): Modifier?{
    return when(this.list.value.size){
        0 -> null
        1 -> Modifier
        2, 3, 4 -> Modifier.fillMaxSize(0.5f)
        5, 6, 7, 8, 9 -> Modifier.fillMaxSize(1/3f)
        else -> { throw Exception("程序错误：超出9个视频") }
    }
}

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

fun MediaPlayerListStatus.isAllMute(): Boolean{
    this.list.value.forEach { elem ->
        if(!elem.first.audio().isMute){
            return false
        }
    }
    return true
}