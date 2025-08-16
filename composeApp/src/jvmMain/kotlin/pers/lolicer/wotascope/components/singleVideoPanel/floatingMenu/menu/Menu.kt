package pers.lolicer.wotascope.components.singleVideoPanel.floatingMenu.menu

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.onClick
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import pers.lolicer.wotascope.components.singleVideoPanel.floatingMenu.menu.components.MirrorItem
import pers.lolicer.wotascope.components.singleVideoPanel.floatingMenu.menu.components.VideoResetItem
import pers.lolicer.wotascope.components.singleVideoPanel.floatingMenu.menu.components.VolumeItem
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer
import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.media_menu

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun Menu(
    mediaPlayer: EmbeddedMediaPlayer,
    expandedMenu: MutableState<Boolean>,
    height: Dp
){
    Box{
        val rotationAngle by animateFloatAsState(
            targetValue = if (expandedMenu.value) 90f else 0f,
            animationSpec = tween(durationMillis = 500)
        )

        // 菜单
        Icon(
            modifier = Modifier
                .size(height)
                .pointerHoverIcon(PointerIcon.Hand)
                .rotate(rotationAngle)
                .onClick{
                    expandedMenu.value = !expandedMenu.value
                },
            painter = painterResource(Res.drawable.media_menu),
            contentDescription = "菜单",
            tint = Color.DarkGray,
        )

        DropdownMenu(
            modifier = Modifier
                .padding(horizontal = 8.dp),
            containerColor = Color(43, 45, 48),
            expanded = expandedMenu.value,
            onDismissRequest = { expandedMenu.value = false }
        ){
            VolumeItem(
                mediaPlayer = mediaPlayer,
                height = height
            )

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 8.dp),
                color = Color(188, 190, 196)
            )

            MirrorItem(
                mediaPlayer = mediaPlayer,
                height = height
            )

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 8.dp),
                color = Color(188, 190, 196)
            )

            VideoResetItem(
                mediaPlayer = mediaPlayer,
                height = height
            )
        }
    }
}