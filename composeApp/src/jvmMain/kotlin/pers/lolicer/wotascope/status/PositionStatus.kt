package pers.lolicer.wotascope.status

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * 用于处理点击底部控制栏无法触发 `EmbeddedMediaPlayer` 对于time/position监听，导致无法及时更新视频进度条的问题。
 */
object PositionStatus{
    private var _shouldUpdateProgressBar by mutableStateOf(false)

    val shouldUpdateProgressBar: Boolean get() = _shouldUpdateProgressBar

    fun setProgressBarUpdateEnabled(newStatus: Boolean){
        _shouldUpdateProgressBar = newStatus
    }
}