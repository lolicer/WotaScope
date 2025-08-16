package pers.lolicer.wotascope.components.singleVideoPanel.floatingMenu.menu.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.onClick
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.Dp
import org.jetbrains.compose.resources.painterResource
import pers.lolicer.wotascope.status.offset
import pers.lolicer.wotascope.status.scale
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer
import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.menu_reset

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VideoResetItem(
    mediaPlayer: EmbeddedMediaPlayer,
    height: Dp
){
    var rotationAngle by remember { mutableStateOf(0f) }
    val rotation by animateFloatAsState(
        targetValue = rotationAngle,
        animationSpec = spring(
            dampingRatio = 0.4f, // 调整弹性系数 (0.0~1.0)
            stiffness = 200f     // 调整刚度
        ),
        finishedListener = {
            if(rotationAngle <= -36000) rotationAngle = 0f
        }
    )

    Row(
        modifier = Modifier.height(height).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "复位",
            color = Color.White
        )

        Icon(
            modifier = Modifier
                .size(height)
                .pointerHoverIcon(PointerIcon.Hand)
                .onClick{
                    rotationAngle -= 360f
                    mediaPlayer.scale = 1f
                    mediaPlayer.offset = Offset(0f, 0f)
                }
                .graphicsLayer{
                    rotationZ = rotation
                },
            painter = painterResource(Res.drawable.menu_reset),
            contentDescription = "复位",
            tint = Color(188, 190, 196)
        )
    }
}