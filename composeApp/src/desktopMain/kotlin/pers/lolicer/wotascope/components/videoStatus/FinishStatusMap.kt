package pers.lolicer.wotascope.components.videoStatus

import androidx.compose.runtime.MutableState
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer
import kotlin.collections.iterator

object FinishStatusMap {
    val mutableMap: MutableMap<EmbeddedMediaPlayer, Boolean> = mutableMapOf()
}

fun FinishStatusMap.isAllFinished(): Boolean{
    this.mutableMap.forEach { finishStatus ->
        if(SelectStatusMap.mutableMap[finishStatus.key] == true && !finishStatus.value){
            return false
        }
    }

    println("Dou JieShu Le")
    return true
}

// fun MutableMap<EmbeddedMediaPlayer, MutableState<Boolean>>.isAllFinished(): Boolean{
//     for(finishStatus in this){
//         if(SelectStatusMap.mutableMap[finishStatus.key] == true && !finishStatus.value.value){
//             return false
//         }
//     }
//
//     println("Dou JieShu Le")
//     return true
// }