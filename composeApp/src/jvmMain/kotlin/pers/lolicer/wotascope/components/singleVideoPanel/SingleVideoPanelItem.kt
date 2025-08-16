package pers.lolicer.wotascope.components.singleVideoPanel

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import pers.lolicer.wotascope.components.singleVideoPanel.floatingMenu.FloatingMenu
import pers.lolicer.wotascope.status.isSelected
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SingleVideoPanelItem(
    mediaPlayer: EmbeddedMediaPlayer,
    constraint: Modifier,
){
    var active by remember { mutableStateOf(false) }
    // val isSelected = remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .background(Color(30, 31, 34))
            .padding(1.dp, 0.dp, 1.dp, 4.dp)
            .border(
                if(mediaPlayer.isSelected) 2.dp else (-1).dp,
                Color(46, 193, 221),
                shape = RoundedCornerShape(2)
            )
            .onPointerEvent(PointerEventType.Enter){ active = true }
            .onPointerEvent(PointerEventType.Move){ active = true }
            .onPointerEvent(PointerEventType.Exit){ active = false }
            .then(constraint)
    ){
        Column (
            modifier = Modifier
                .background(Color(30, 31, 34))
                .padding(4.dp, 4.dp, 4.dp, 8.dp)
        ){
            var screenSize by remember { mutableStateOf(IntSize.Zero) }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .onSizeChanged{ screenSize = it }
            ){
                FloatingMenu(
                    modifier = Modifier.zIndex(2f),
                    mediaPlayer = mediaPlayer,
                    isHovered = active,
                    screenSize = screenSize
                )
                VideoDisplay(
                    modifier = Modifier.zIndex(1f),
                    mediaPlayer = mediaPlayer
                )
            }
            ProgressBar(mediaPlayer = mediaPlayer)
        }
    }

    LaunchedEffect(mediaPlayer.isSelected){
        if(!mediaPlayer.isSelected){
            mediaPlayer.controls().setPause(true)
        }
    }
}