package pers.lolicer.wotascope.components.singleVideoBar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import uk.co.caprica.vlcj.player.base.MediaPlayer

@Composable
fun VerticalSlider(
    modifier: Modifier = Modifier,
    value: Float,
    onValueChange: (Float) -> Unit,
    sliderHeight: Dp,
    trackWidth: Dp
) {

    Box(
        modifier = modifier
            .width(sliderHeight / 10f)
            .pointerHoverIcon(PointerIcon.Hand)
            .pointerInput(sliderHeight, trackWidth) {
                detectDragGestures { change, _ ->
                    val newValue = 1f - (change.position.y / size.height).coerceIn(0f, 1f)
                    println("newValue = $newValue")
                    onValueChange(newValue)
                }
            }
    ) {
        // 轨道背景
        Box(
            modifier = Modifier
                .width(trackWidth)
                .height(sliderHeight)
                .align(Alignment.Center)
                .background(Color.LightGray)
        )

        // 进度条
        Box(
            modifier = Modifier
                .width(trackWidth)
                .height(sliderHeight * value)
                .align(Alignment.BottomCenter)
                .background(Color(0, 191, 255))
        )

        // 自定义Thumb
        Box(
            modifier = Modifier
                .size(sliderHeight / 10f)
                .offset(y = sliderHeight * (1f - value) - sliderHeight / 10f / 2f)
                .align(Alignment.TopCenter)
                .background(Color.White, CircleShape)
                .border(2.dp, Color.Blue, CircleShape)
        )
    }
}