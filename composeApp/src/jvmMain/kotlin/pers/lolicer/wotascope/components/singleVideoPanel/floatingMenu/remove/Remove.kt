package pers.lolicer.wotascope.components.singleVideoPanel.floatingMenu.remove

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.onClick
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.Dp
import org.jetbrains.compose.resources.painterResource
import pers.lolicer.wotascope.status.MediaPlayerListStatus
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer
import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.media_close

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Remove(
    mediaPlayer: EmbeddedMediaPlayer,
    height: Dp
){
    Icon(
        modifier = Modifier
            .size(height)
            .pointerHoverIcon(PointerIcon.Hand)
            .onClick{
                MediaPlayerListStatus.list.value = MediaPlayerListStatus.list.value.filterNot { it.first == mediaPlayer }
            },
        painter = painterResource(Res.drawable.media_close),
        contentDescription = "关闭视频",
        tint = Color.Red
    )
}