package pers.lolicer.wotascope.status

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object OverlapStatus {
    private var _overlapStatus by mutableStateOf(OverlapState.NOT_OVERLAP)
    val status: OverlapState get() = _overlapStatus

    fun updateStatus(newStatus: OverlapState) {
        _overlapStatus = newStatus
    }
}

enum class OverlapState {
    OVERLAP,
    NOT_OVERLAP
}