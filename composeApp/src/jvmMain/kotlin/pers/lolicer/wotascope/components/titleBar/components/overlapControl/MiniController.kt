package pers.lolicer.wotascope.components.titleBar.components.overlapControl

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import org.jetbrains.compose.resources.painterResource
import pers.lolicer.wotascope.components.BiliSlider
import pers.lolicer.wotascope.components.DescriptionText
import pers.lolicer.wotascope.status.MediaPlayerListStatus
import pers.lolicer.wotascope.status.alpha
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer
import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.menu_circle
import wotascope.composeapp.generated.resources.menu_circle_outline
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
            text = "拖动或滚动左侧圆形区域以移动或缩放视频；\n点击或拖动右侧滑块以调整视频透明度。",
            modifier = Modifier.padding(start = 4.dp, end = 0.dp, bottom = 12.dp),
            isParagraph = false
        )

        val list = MediaPlayerListStatus.list.value
        MiniControllerItem(list[0].first, height)
        MiniControllerItem(list[1].first, height)
    }
}

@Composable
fun MiniControllerItem(
    mediaPlayer: EmbeddedMediaPlayer,
    height: Dp
){
    var alpha by remember { mutableStateOf(mediaPlayer.alpha) }

    Row(
        modifier = Modifier.height(height).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ){
            Icon(
                modifier = Modifier.size(height * 1/2).zIndex(0f),
                painter = painterResource(Res.drawable.menu_circle_outline),
                contentDescription = "",
                tint = Color.LightGray
            )
            Icon(
                modifier = Modifier.size(height * 3/8).zIndex(1f),
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
    }
}