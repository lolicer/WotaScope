package pers.lolicer.wotascope.components.singleVideoPanel

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import pers.lolicer.wotascope.components.singleVideoPanel.components.VolumeItem
import pers.lolicer.wotascope.status.MediaPlayerListStatus
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer
import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.media_close
import wotascope.composeapp.generated.resources.media_menu

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun FloatingMenu(
    modifier: Modifier,
    mediaPlayer: EmbeddedMediaPlayer,
    isHovered: Boolean,
    screenSize: IntSize
){
    var expandedMenu by remember { mutableStateOf(false) }

    AnimatedVisibility(
        modifier = modifier.offset(x = screenSize.width.dp - (screenSize.width / 40 * 3).dp, y = 10.dp),
        visible = isHovered || expandedMenu,
        enter = slideInHorizontally { it } + fadeIn(),
        exit = slideOutHorizontally { it } + fadeOut()
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(16))
                .background(color = Color.LightGray)
                .height((screenSize.width / 40 + 6).dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Spacer(modifier = Modifier.width(3.dp).fillMaxHeight())

            Box(){
                val rotationAngle by animateFloatAsState(
                    targetValue = if (expandedMenu) 90f else 0f,
                    animationSpec = tween(durationMillis = 500)
                )

                // 菜单
                Icon(
                    modifier = Modifier
                        .size((screenSize.width / 40).dp)
                        .pointerHoverIcon(PointerIcon.Hand)
                        .rotate(rotationAngle)
                        .onClick{
                            expandedMenu = !expandedMenu
                        },
                    painter = painterResource(Res.drawable.media_menu),
                    contentDescription = "菜单",
                    tint = Color.DarkGray,
                )

                DropdownMenu(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    containerColor = Color(43, 45, 48),
                    expanded = expandedMenu,
                    onDismissRequest = { expandedMenu = false }
                ){
                    VolumeItem(
                        mediaPlayer = mediaPlayer,
                        height = (screenSize.width / 40).dp
                    )
                }
            }

            Spacer(modifier = Modifier.width(4.dp).fillMaxHeight())

            // 关闭视频
            Icon(
                modifier = Modifier
                    .size((screenSize.width / 40).dp)
                    .pointerHoverIcon(PointerIcon.Hand)
                    .onClick{
                        MediaPlayerListStatus.list.value = MediaPlayerListStatus.list.value.filterNot { it.first == mediaPlayer }
                    },
                painter = painterResource(Res.drawable.media_close),
                contentDescription = "关闭视频",
                tint = Color.Red
            )

            Spacer(modifier = Modifier.width(3.dp).fillMaxHeight())
        }
    }
}