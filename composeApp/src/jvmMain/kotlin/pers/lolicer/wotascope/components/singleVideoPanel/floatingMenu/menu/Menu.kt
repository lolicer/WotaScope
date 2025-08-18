package pers.lolicer.wotascope.components.singleVideoPanel.floatingMenu.menu

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Popup
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
    var active by remember { mutableStateOf(false) }
    var anchorBounds by remember { mutableStateOf<Rect?>(null) } // 在按钮位置设置锚点

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
                .onPointerEvent(PointerEventType.Enter) { active = true }
                .onPointerEvent(PointerEventType.Exit) { active = false }
                .rotate(rotationAngle)
                .onClick{
                    expandedMenu.value = !expandedMenu.value
                }
                .onGloballyPositioned { coordinates ->
                    val position = coordinates.localToWindow(Offset.Zero)
                    val size = coordinates.size.toSize()
                    anchorBounds = Rect(position, size)
                },
            painter = painterResource(Res.drawable.media_menu),
            contentDescription = "菜单",
            tint = Color.DarkGray,
        )

        if(anchorBounds != null) {
            Popup(
                onDismissRequest = {
                    if(!active) expandedMenu.value = false
                },
                offset = IntOffset(-(height.value.toInt() * 2), anchorBounds!!.height.toInt())
            ){
                AnimatedVisibility(
                    visible = expandedMenu.value && anchorBounds != null,
                    enter = expandVertically(),
                    exit = shrinkVertically{ -(it + 10) }
                ){
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(color = Color(43, 45, 48))
                            .width(height * 4 + 8.dp * 2)
                            .border(
                                width = 1.dp,
                                color = Color(67, 68, 69),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
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
        }
    }
}