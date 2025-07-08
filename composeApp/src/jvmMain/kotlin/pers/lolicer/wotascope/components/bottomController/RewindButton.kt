package pers.lolicer.wotascope.components.bottomController

import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.painterResource
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer
import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.media_rewind


@Composable
fun RewindButton(
    mediaPlayer: EmbeddedMediaPlayer
){
    Icon(painter = painterResource(Res.drawable.media_rewind), contentDescription = "后退一帧，暂未实现")
}