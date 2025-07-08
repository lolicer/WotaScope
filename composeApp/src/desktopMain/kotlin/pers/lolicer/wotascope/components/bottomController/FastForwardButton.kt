package pers.lolicer.wotascope.components.bottomController

import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.painterResource
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer
import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.media_fastforward


@Composable
fun FastForwardButton(
    mediaPlayer: EmbeddedMediaPlayer
){
    Icon(painter = painterResource(Res.drawable.media_fastforward), contentDescription = "前进一帧")
}