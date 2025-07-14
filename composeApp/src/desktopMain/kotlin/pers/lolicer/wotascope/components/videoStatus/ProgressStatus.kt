package pers.lolicer.wotascope.components.videoStatus

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

object ProgressStatus {
    val value: MutableState<Boolean> = mutableStateOf(false)
    // var value = false
}