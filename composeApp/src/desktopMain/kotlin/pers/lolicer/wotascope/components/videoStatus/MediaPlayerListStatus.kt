package pers.lolicer.wotascope.components.videoStatus

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import pers.lolicer.wotascope.components.videoStatus.MediaPlayerListStatus.mutableMap
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer

object MediaPlayerListStatus {
    val mutableMap: MutableState<MutableMap<EmbeddedMediaPlayer, Status>> = mutableStateOf(mutableMapOf())

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

fun MediaPlayerListStatus.isAllFinished(): Boolean{
    this.mutableMap.value.forEach { elem ->
        val isSelected = MediaPlayerListStatus.mutableMap.value[elem.key]?.isSelected
        val isFinished = MediaPlayerListStatus.mutableMap.value[elem.key]?.isFinished
        if(isSelected == true && isFinished == false){
            return false
        }
    }
    println("Dou JieShu Le")
    return true
}

fun MediaPlayerListStatus.isAnyPlaying(): Boolean{
    this.mutableMap.value.forEach { elem ->
        if(elem.value.isSelected && elem.key.status().isPlaying){
            return true
        }
    }
    return false
}

fun MediaPlayerListStatus.isAllMute(): Boolean{
    this.mutableMap.value.forEach { elem ->
        if(!elem.key.audio().isMute){
            return false
        }
    }
    return true
}