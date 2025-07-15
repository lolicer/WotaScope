package pers.lolicer.wotascope.components.videoStatus

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer

object RemoveVideoPlayerStatus {
    val value: MutableState<EmbeddedMediaPlayer?> = mutableStateOf(null)
}