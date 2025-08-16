package pers.lolicer.wotascope.components.singleVideoPanel.floatingMenu.menu.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.onClick
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.Dp
import org.jetbrains.compose.resources.painterResource
import pers.lolicer.wotascope.status.isMirrored
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer
import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.menu_checkbox_checked
import wotascope.composeapp.generated.resources.menu_checkbox_unchecked

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MirrorItem(
    mediaPlayer: EmbeddedMediaPlayer,
    height: Dp
){
    Row(
        modifier = Modifier.height(height).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = "镜像",
            color = Color.White
        )

        Icon(
            modifier = Modifier
                .size(height)
                .pointerHoverIcon(PointerIcon.Hand)
                .onClick{
                    mediaPlayer.isMirrored = !mediaPlayer.isMirrored
                },
            painter = painterResource(if(mediaPlayer.isMirrored) Res.drawable.menu_checkbox_checked else Res.drawable.menu_checkbox_unchecked),
            contentDescription = if(mediaPlayer.isMirrored) "取消镜像" else "镜像",
            tint = Color(188, 190, 196)
        )
    }
}