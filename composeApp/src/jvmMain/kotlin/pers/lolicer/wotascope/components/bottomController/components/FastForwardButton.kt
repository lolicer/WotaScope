package pers.lolicer.wotascope.components.bottomController.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.onClick
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerHoverIcon
import org.jetbrains.compose.resources.painterResource
import pers.lolicer.wotascope.status.MediaPlayerListStatus
import pers.lolicer.wotascope.status.PositionStatus
import pers.lolicer.wotascope.status.isFinished
import pers.lolicer.wotascope.status.isSelected
import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.media_fastforward


@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun FastForwardButton(
    modifier: Modifier
){
    var active by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .background(color = if(!active) Color.Transparent else Color(56, 58, 61)),
        contentAlignment = Alignment.Center
    ){
        Icon(
            modifier = Modifier
                .pointerHoverIcon(PointerIcon.Hand)
                .onPointerEvent(PointerEventType.Enter) { active = true }
                .onPointerEvent(PointerEventType.Exit) { active = false }
                .onClick{
                    MediaPlayerListStatus.list.value.forEach { elem ->
                        if(elem.first.isSelected && !elem.first.isFinished){
                            elem.first.controls().skipTime(1000/30)
                            elem.first.controls().setPause(true)
                        }
                    }

                    PositionStatus.setProgressBarUpdateEnabled(!PositionStatus.shouldUpdateProgressBar)
                },
            painter = painterResource(Res.drawable.media_fastforward),
            contentDescription = "前进一帧",
            tint = Color.White
        )
    }
}