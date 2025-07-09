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
import pers.lolicer.wotascope.components.singleVideoBar.videoPlayerWithoutSwingPanel.VideoPlayer
import pers.lolicer.wotascope.components.videoStatus.SelectStatusMap
import uk.co.caprica.vlcj.player.base.MediaPlayer

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SingleVideoPanelItem(
    path: String,
    onMediaPlayer: (MediaPlayer) -> Unit,
    onSelectedChanged: (Boolean) -> Unit,
    constraint: Modifier
){
    // val mediaPlayerComponent = remember { EmbeddedMediaPlayerComponent() }
    val mediaPlayer = remember { mutableStateOf<MediaPlayer?>(null) }

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
                .padding(4.dp, 4.dp, 4.dp, 8.dp)
        ){
            // SingleVideoScreen(mediaPlayerComponent, mediaPlayer, path, isSelected)
            VideoPlayer(
                modifier = Modifier,
                mrl = path,
                onVideoReady = { mp ->
                    mediaPlayer.value = mp
                },
                isSelected = isSelected
            )
            if(mediaPlayer.value != null){
                SingleVideoController(mediaPlayer.value!!)
            }
        }

        LaunchedEffect(isSelected.value){
            if(mediaPlayer.value != null){
                onSelectedChanged(isSelected.value)

                if(!isSelected.value){
                    mediaPlayer.value!!.controls().setPause(true)
                }

                if(SelectStatusMap.mutableMap.containsKey(mediaPlayer.value)){
                    SelectStatusMap.mutableMap[mediaPlayer.value!!] = isSelected.value
                }
                else{
                    println("SelectStatusMap.mutableMap:${SelectStatusMap.mutableMap}")
                    println("But mediaPlayer.value: ${mediaPlayer.value}")
                    throw Exception("SelectCommandMap ERROR")
                }
            }
        }
        LaunchedEffect(mediaPlayer.value){
            if(mediaPlayer.value != null){
                // println("mediaPlayer.value = ${mediaPlayer.value}")
                onMediaPlayer(mediaPlayer.value!!)
            }
        }
    }
}
