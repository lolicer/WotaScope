package pers.lolicer.wotascope.components.bottomController

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
import pers.lolicer.wotascope.components.videoStatus.FinishStatusMap
import pers.lolicer.wotascope.components.videoStatus.ProgressStatus
import pers.lolicer.wotascope.components.videoStatus.SelectStatusMap
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer
import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.media_skip_forward_10


@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun SkipForwardButton(
    modifier: Modifier,
    mediaPlayerList: List<EmbeddedMediaPlayer>
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
                    ProgressStatus.value.value = !ProgressStatus.value.value
                    SelectStatusMap.mutableMap.forEach { elem ->
                        if(elem.value){
                            if(FinishStatusMap.mutableMap[elem.key] == false){
                                elem.key.controls().skipTime(1000 * 10)
                            }
                        }
                    }
                },
            painter = painterResource(Res.drawable.media_skip_forward_10),
            contentDescription = "快进十秒",
            tint = Color.White
        )
    }
}