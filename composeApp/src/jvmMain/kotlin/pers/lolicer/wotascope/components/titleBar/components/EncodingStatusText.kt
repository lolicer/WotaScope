package pers.lolicer.wotascope.components.titleBar.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pers.lolicer.wotascope.status.EncodingStatus

@Composable
fun EncodingStatusText(
    height: Dp,
    encodingStatus: MutableState<EncodingStatus?>,
    showCompletion: Boolean
){
    Column(){
        ShoujoIsPrayingText(height, encodingStatus.value)
        PrayerCompleted(height, showCompletion)
    }
}

@Composable
fun ShoujoIsPrayingText(
    height: Dp,
    encodingStatus: EncodingStatus?
){
    val totalDuration = 2000 // 2秒总周期
    val waveDuration = 1500  // 波浪动画部分时长
    val pauseDuration = totalDuration - waveDuration // 暂停时间

    // 每个字符的动画持续时间
    val charDuration = waveDuration / (8+1) // 每个字符完成上下运动的时间

    // 为每个字符创建动画状态
    val animations = List(8) { index ->
        val delay = index * (charDuration / 2) // 每个字符间隔200ms开始
        val infiniteTransition = rememberInfiniteTransition()

        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = waveDuration
                    0f at delay using LinearEasing
                    0.5f at (delay + charDuration / 2) using LinearEasing // 上到顶点
                    1f at (delay + charDuration) using LinearEasing      // 下回原位
                },
                repeatMode = RepeatMode.Restart,
                initialStartOffset = StartOffset(pauseDuration) // 每轮结束后暂停
            )
        )
    }

    AnimatedVisibility(
        modifier = Modifier.height(height),
        visible = (encodingStatus == EncodingStatus.ENCODING),
        enter = slideInVertically{ -it },
        exit = slideOutVertically{ -it }
    ){
        val text = "少女祈祷中..."
        Row(
            modifier = Modifier.height(height),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            text.forEachIndexed { index, char ->
                val offsetProgress by animations[index]

                // 计算Y轴偏移量（0% -> 30% -> 0%）
                val offset = when {
                    offsetProgress < 0.5 -> offsetProgress * 0.4f // 向上移动 (0% -> 20%)
                    else -> (1 - offsetProgress) * 0.4f           // 向下移动 (20% -> 0%)
                }

                Text(
                    text = char.toString(),
                    style = TextStyle(fontSize = 16.sp),
                    color = Color.White,
                    modifier = Modifier
                        .padding(start = 2.dp, end = 2.dp, bottom = 2.dp)
                        .graphicsLayer {
                            translationY = -offset * 50 // 20% 偏移量
                        }
                )
            }
        }
    }
}

@Composable
fun PrayerCompleted(
    height: Dp,
    showCompletion: Boolean
){
    AnimatedVisibility(
        modifier = Modifier.height(height),
        visible = showCompletion,
        enter = slideInVertically{ it },
        exit = slideOutVertically{ it }
    ){
        Row(
            modifier = Modifier.height(height),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ){
            Text(
                text = "编码完成~",
                style = TextStyle(fontSize = 16.sp),
                color = Color.White,
                modifier = Modifier
                    .padding(start = 2.dp, end = 2.dp, bottom = 2.dp)
            )
        }
    }
}

// 直接用Compose原生动画了。Lottie原生不支持中文字体，转换为视频再转为lottie有锯齿
//
// Lottie似乎暂时无法应用在Compose Multiplatform上，故使用此作为Lottie加载器。链接如下：
// https://github.com/alexzhirkevich/compottie
// implementation("io.github.alexzhirkevich:compottie:2.0.0-rc04")
// implementation("io.github.alexzhirkevich:compottie-dot:2.0.0-rc04")
//
// val composition = rememberLottieComposition {
//     LottieCompositionSpec.JsonString(
//         Res.readBytes("drawable/shoujo_is_praying.json").decodeToString()
//     )
// }
// val composition = rememberLottieComposition {
//     LottieCompositionSpec.Companion.DotLottie(
//         archive = Res.readBytes("drawable/shoujo_is_praying.lottie")
//     )
// }
// Image(
//     modifier = Modifier.height(height).width(height * 64 / 15).border(1.dp, Color.Blue),
//     painter = rememberLottiePainter(
//         composition = composition.value,
//         iterations = Compottie.IterateForever
//     ),
//     contentDescription = "Lottie Animation"
// )