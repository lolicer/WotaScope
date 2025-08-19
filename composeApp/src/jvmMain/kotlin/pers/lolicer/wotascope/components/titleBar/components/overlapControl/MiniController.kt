package pers.lolicer.wotascope.components.titleBar.components.overlapControl

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.onDrag
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import pers.lolicer.wotascope.components.BiliSlider
import pers.lolicer.wotascope.components.DescriptionText
import pers.lolicer.wotascope.status.MediaPlayerListStatus
import pers.lolicer.wotascope.status.alpha
import pers.lolicer.wotascope.status.offset
import pers.lolicer.wotascope.status.scale
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer
import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.menu_circle
import wotascope.composeapp.generated.resources.menu_square
import wotascope.composeapp.generated.resources.settings_notice

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MiniController(
    height: Dp,
    width: Dp
){
    var showDescription by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.width(width)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "调整",
                color = Color.LightGray,
                fontSize = 14.sp
            )
            Icon(
                modifier = Modifier
                    .size(height / 2)
                    .padding(start = 4.dp)
                    .pointerHoverIcon(PointerIcon.Hand)
                    .onClick{
                        showDescription = !showDescription
                    },
                painter = painterResource(Res.drawable.settings_notice),
                contentDescription = "说明",
                tint = Color.Gray
            )
        }

        DescriptionText(
            showDescription = showDescription,
            text = "拖动或滚动左侧圆形区域以移动或缩放视频；\n双击左侧圆形区域以重置视频位置；\n点击或拖动右侧滑块以调整视频透明度。",
            modifier = Modifier.padding(start = 4.dp, end = 0.dp, bottom = 12.dp),
            isParagraph = false
        )

        val list = MediaPlayerListStatus.list.value
        MiniControllerItem(list[0].first, height)
        MiniControllerItem(list[1].first, height)
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun MiniControllerItem(
    mediaPlayer: EmbeddedMediaPlayer,
    height: Dp
){
    val scope = rememberCoroutineScope()

    var alpha by remember { mutableStateOf(mediaPlayer.alpha) } // 视频透明度

    var joystickOffset by remember { mutableStateOf(Animatable(Offset(0f, 0f), Offset.VectorConverter)) }
    val radius = height.value * 5/8 // 摇杆可移动的半径

    var isDragging by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.height(height).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ){
            Icon(
                modifier = Modifier.size(height * 5/8).zIndex(0f),
                painter = painterResource(Res.drawable.menu_square),
                contentDescription = "",
                tint = Color.LightGray
            )
            Icon(
                modifier = Modifier
                    .size(height * 3/8)
                    .zIndex(1f)
                    .pointerHoverIcon(PointerIcon.Hand)
                    .offset(
                        joystickOffset.value.x.coerceIn(-radius, radius).dp,
                        joystickOffset.value.y.coerceIn(-radius, radius).dp
                    )
                    .onDrag(
                        onDragStart = {
                            isDragging = true
                        },
                        onDrag = {
                            joystickOffset = Animatable(joystickOffset.value + it, Offset.VectorConverter)
                            // mediaPlayer.offset += it
                        },
                        onDragEnd = {
                            isDragging = false
                            scope.launch {
                                joystickOffset.animateTo(
                                    targetValue = Offset(0f, 0f),
                                    animationSpec = tween(durationMillis = 250) // 300ms 动画
                                )
                            }
                        }
                    )
                    .combinedClickable(
                        onClick = {},
                        onDoubleClick = {
                            mediaPlayer.offset = Offset(0f, 0f)
                        }
                    )
                    .onPointerEvent(PointerEventType.Scroll){
                        val zoomDelta = it.changes.first().scrollDelta.y
                        mediaPlayer.scale = (mediaPlayer.scale * (1f - zoomDelta * 0.1f))
                    },
                painter = painterResource(Res.drawable.menu_circle),
                contentDescription = "",
                tint = Color.LightGray
            )
        }
        BiliSlider(
            modifier = Modifier.padding(start = height/2),
            value = alpha,
            onValueChange = {
                alpha = it
                mediaPlayer.alpha = alpha
            },
            onValueChangeFinished = {}
        )

        LaunchedEffect(isDragging){
            if(isDragging){
                while(true){
                    withFrameNanos{
                        val x = joystickOffset.value.x / 10
                        val y = joystickOffset.value.y / 10
                        mediaPlayer.offset += Offset(x, y)
                    }
                }
            }
        }
    }
}