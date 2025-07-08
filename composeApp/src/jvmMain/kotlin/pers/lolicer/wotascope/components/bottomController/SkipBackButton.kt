package pers.lolicer.wotascope.components.bottomController

import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.painterResource
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer
import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.media_skip_back_5


@Composable
fun SkipBackButton(
    mediaPlayer: EmbeddedMediaPlayer
){
    Icon(painter = painterResource(Res.drawable.media_skip_back_5), contentDescription = "快退五秒")
}