package pers.lolicer.wotascope.components.singleVideoPanel.floatingMenu.selected

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
import pers.lolicer.wotascope.status.isSelected
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer
import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.menu_checkbox_checked
import wotascope.composeapp.generated.resources.menu_checkbox_unchecked

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Selected(
    mediaPlayer: EmbeddedMediaPlayer,
    height: Dp
){
    Icon(
        modifier = Modifier
            .size(height)
            .pointerHoverIcon(PointerIcon.Hand)
            .onClick{
                mediaPlayer.isSelected = !mediaPlayer.isSelected
            },
        painter = painterResource(if(mediaPlayer.isSelected) Res.drawable.menu_checkbox_checked else Res.drawable.menu_checkbox_unchecked),
        contentDescription = "选中",
        tint = Color.DarkGray
    )
}