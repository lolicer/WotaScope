package pers.lolicer.wotascope.components.singleVideoBar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SingleVideoPanelItem(
    url: String,
    onMediaPlayer: (EmbeddedMediaPlayer) -> Unit,
    onSelected: (Boolean) -> Unit
){
    val mediaPlayerComponent = remember { EmbeddedMediaPlayerComponent() }
    val mediaPlayer = remember { mediaPlayerComponent.mediaPlayer() }
    onMediaPlayer(mediaPlayer)

    val isSelected = remember { mutableStateOf(true) }

    Column (
        modifier = Modifier
            .padding(4.dp)
            .background(Color(30, 31, 34))
            .border(
                width = if(isSelected.value) 1.dp else 0.dp,
                color = Color(46, 193, 221),
                shape = RectangleShape,
            )
    ){
        SingleVideoScreen(mediaPlayerComponent, mediaPlayer, url, isSelected)
        SingleVideoController(mediaPlayer)
    }

    LaunchedEffect(isSelected){
        onSelected(isSelected.value)
    }
}
