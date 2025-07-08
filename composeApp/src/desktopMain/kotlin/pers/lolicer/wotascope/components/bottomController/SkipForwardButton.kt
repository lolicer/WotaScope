package pers.lolicer.wotascope.components.bottomController

import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.painterResource
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer
import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.media_skip_forward_10


@Composable
fun SkipForwardButton(
    mediaPlayer: EmbeddedMediaPlayer
){
    Icon(painter = painterResource(Res.drawable.media_skip_forward_10), contentDescription = "快进十秒")
}