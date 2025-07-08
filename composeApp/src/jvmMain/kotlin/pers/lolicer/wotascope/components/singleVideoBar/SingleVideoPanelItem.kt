package pers.lolicer.wotascope.components.singleVideoBar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pers.lolicer.wotascope.components.selectStatusMap.SelectStatusMap
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SingleVideoPanelItem(
    path: String,
    onMediaPlayer: (EmbeddedMediaPlayer) -> Unit,
    onSelectedChanged: (Boolean) -> Unit,
    constraint: Modifier
){
    val mediaPlayerComponent = remember { EmbeddedMediaPlayerComponent() }
    val mediaPlayer = remember { mediaPlayerComponent.mediaPlayer() }

    // 重组会导致 onMediaPlayer(mediaPlayer) 多次调用
    val returnMediaPlayer = remember { mutableStateOf(false) }
    if(!returnMediaPlayer.value){
        onMediaPlayer(mediaPlayer)
        returnMediaPlayer.value = true
    }

    val isSelected = remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .background(Color(30, 31, 34))
            .padding(1.dp, 0.dp, 1.dp, 4.dp)
            .border(
                if(isSelected.value) 2.dp else (-1).dp,
                Color(46, 193, 221),
                shape = RoundedCornerShape(2)
            )
            // .shadow(
            //     24.dp,
            //     ambientColor = Color.White,
            //     spotColor = Color.White
            // )
            .then(constraint)
    ){
        Column (
            modifier = Modifier
                .background(Color(30, 31, 34))
                .padding(4.dp, 4.dp, 4.dp, 6.dp)
        ){
            SingleVideoScreen(mediaPlayerComponent, mediaPlayer, path, isSelected)
            SingleVideoController(mediaPlayer)
        }

        LaunchedEffect(isSelected.value){
            onSelectedChanged(isSelected.value)

            if(!isSelected.value){
                mediaPlayer.controls().setPause(true)
            }

            if(SelectStatusMap.mutableMap.containsKey(mediaPlayer)){
                SelectStatusMap.mutableMap[mediaPlayer] = isSelected.value
            }
            else{
                throw Exception("SelectCommandMap ERROR")
            }
        }
    }
}
