@file:OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)

package pers.lolicer.wotascope.components.titleBar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowScope
import androidx.compose.ui.window.WindowState
import com.russhwolf.settings.set
import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.title_escape
import wotascope.composeapp.generated.resources.title_maximize
import wotascope.composeapp.generated.resources.title_minimize
import wotascope.composeapp.generated.resources.title_restore
import wotascope.composeapp.generated.resources.yjtp
import org.jetbrains.compose.resources.painterResource
import pers.lolicer.wotascope.components.titleBar.settingsWindow.SettingsWindow
import pers.lolicer.wotascope.components.utils.ExtensionUtils
import pers.lolicer.wotascope.settings.SettingsKeys
import pers.lolicer.wotascope.settings.SettingsManager.settings
import java.awt.GraphicsEnvironment
import kotlin.collections.plus
import kotlin.system.exitProcess

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WindowScope.TitleBar(
    titleHeight: Dp,
    windowState: WindowState,
    paths: MutableState<List<String>>
) = WindowDraggableArea{
    val iconSize = 24.dp

    Row(
        Modifier.height(titleHeight).fillMaxWidth().background(Color(43, 45, 48))
    ){
        Icon(titleHeight)
        Spacer(modifier = Modifier.width(8.dp))
        AddButton(titleHeight, paths)
        Spacer(modifier = Modifier.width(8.dp))
        SettingsButton(titleHeight)

        Spacer(modifier = Modifier.weight(1f))

        MinimizeButton(titleHeight,iconSize , windowState)
        MaximizeButton(titleHeight,iconSize , windowState)
        EscapeButton(titleHeight, iconSize)
    }
}

@Composable
fun Icon(
    titleHeight: Dp
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(titleHeight)
    ){
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(Res.drawable.yjtp),
            contentDescription = "图标",
            tint = Color.Unspecified
        )
    }

}

@Composable
fun AddButton(
    titleHeight: Dp,
    paths: MutableState<List<String>>
){
    var active by remember { mutableStateOf(false) }
    var isPressed by remember { mutableStateOf(false) }

    Text(
        "添加",
        color = Color.White,
        fontSize = 14.sp,
        modifier = Modifier
            .offset(y = 4.dp)
            .height(titleHeight - 8.dp)
            .pointerHoverIcon(PointerIcon.Hand)
            .onPointerEvent(PointerEventType.Enter) { active = true }
            .onPointerEvent(PointerEventType.Exit) { active = false }
            .pointerInput(Unit){
                awaitPointerEventScope {
                    while(true){
                        val event = awaitPointerEvent()
                        when (event.type) {
                            PointerEventType.Press -> isPressed = true    // 鼠标按下
                            PointerEventType.Release -> isPressed = false // 鼠标抬起
                            else -> {}
                        }
                    }
                }
            }
            .onClick{
                val selectFile = ExtensionUtils().selectFile()
                if(selectFile != null) {
                    if(paths.value.size + selectFile.size <= 9)
                        paths.value = paths.value + selectFile
                }
            }
            .drawBehind {
                val textHeight = size.height
                val textWidth = size.width
                val strokeWidth = 1f

                if(active && !isPressed){
                    drawLine(
                        color = Color.White,
                        start = Offset(0f, textHeight - 2),
                        end = Offset(textWidth, textHeight - 2),
                        strokeWidth = strokeWidth
                    )
                }
            }
    )
}

@Composable
fun SettingsButton(
    titleHeight: Dp
){
    var active by remember { mutableStateOf(false) }
    var isPressed by remember { mutableStateOf(false) }

    val showSettingsWindow = remember { mutableStateOf(false) }

    Text(
        "设置",
        color = Color.White,
        fontSize = 14.sp,
        modifier = Modifier
            .offset(y = 4.dp)
            .height(titleHeight - 8.dp)
            .pointerHoverIcon(PointerIcon.Hand)
            .onPointerEvent(PointerEventType.Enter) { active = true }
            .onPointerEvent(PointerEventType.Exit) { active = false }
            .pointerInput(Unit){
                awaitPointerEventScope {
                    while(true){
                        val event = awaitPointerEvent()
                        when (event.type) {
                            PointerEventType.Press -> isPressed = true    // 鼠标按下
                            PointerEventType.Release -> isPressed = false // 鼠标抬起
                            else -> {}
                        }
                    }
                }
            }
            .onClick{
                showSettingsWindow.value = true
            }
            .drawBehind {
                val textHeight = size.height
                val textWidth = size.width
                val strokeWidth = 1f

                if(active && !isPressed){
                    drawLine(
                        color = Color.White,
                        start = Offset(0f, textHeight - 2),
                        end = Offset(textWidth, textHeight - 2),
                        strokeWidth = strokeWidth
                    )
                }
            }
    )

    if(showSettingsWindow.value){
        SettingsWindow(showSettingsWindow)
    }
}

@Composable
fun MinimizeButton(
    titleHeight: Dp,
    iconSize: Dp,
    windowState: WindowState
){
    var active by remember { mutableStateOf(false) }
    var isPressed by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.size(titleHeight),
        contentAlignment = Alignment.Center
    ){
        Icon(
            modifier = Modifier
                .size(if(!active || isPressed) (iconSize - 6.dp) else iconSize)
                .pointerHoverIcon(PointerIcon.Hand)
                .onClick{ windowState.isMinimized = true }
                .onPointerEvent(PointerEventType.Enter) { active = true }
                .onPointerEvent(PointerEventType.Exit) { active = false }
                .pointerInput(Unit){
                    awaitPointerEventScope {
                        while(true){
                            val event = awaitPointerEvent()
                            when (event.type) {
                                PointerEventType.Press -> isPressed = true    // 鼠标按下
                                PointerEventType.Release -> isPressed = false // 鼠标抬起
                                else -> {}
                            }
                        }
                    }
                }
                .background(color = if(!active || isPressed) Color.Transparent else Color(56, 58, 61)),
            painter = painterResource(Res.drawable.title_minimize),
            contentDescription = "最小化",
            tint = Color.White
        )
    }
}

@Composable
fun MaximizeButton(
    titleHeight: Dp,
    iconSize: Dp,
    windowState: WindowState
){
    var active by remember { mutableStateOf(false) }
    var isPressed by remember { mutableStateOf(false) }

    val screenWidth = with(LocalDensity.current) {
        GraphicsEnvironment.getLocalGraphicsEnvironment().maximumWindowBounds.width.toDp()
    }
    val screenHeight = with(LocalDensity.current) {
        GraphicsEnvironment.getLocalGraphicsEnvironment().maximumWindowBounds.height.toDp()
    }

    val previousPosition = remember { mutableStateOf(windowState.position) }
    val previousWidth = remember { mutableStateOf(windowState.size.width) }
    val previousHeight = remember { mutableStateOf(windowState.size.height) }
    val isMaximized = remember { mutableStateOf(false) }

    LaunchedEffect(windowState.position){
        if(!isMaximized.value)
            previousPosition.value = windowState.position
    }
    LaunchedEffect(windowState.size){
        val width = windowState.size.width
        val height = windowState.size.height
        if(width == screenWidth && height == screenHeight){
            isMaximized.value = true
        }
        else{
            previousWidth.value = width
            previousHeight.value = height

            settings[SettingsKeys.MAIN_WINDOW_WIDTH] = width.value.toInt()
            settings[SettingsKeys.MAIN_WINDOW_HEIGHT] = height.value.toInt()
        }
    }

    Box(
        modifier = Modifier
            .size(titleHeight)
            .pointerHoverIcon(PointerIcon.Hand),
        contentAlignment = Alignment.Center
    ){
        Icon(
            modifier = Modifier
                .size(if(!active || isPressed) (iconSize - 6.dp) else iconSize)
                .onClick{
                    if(!isMaximized.value){
                        windowState.size = DpSize(screenWidth, screenHeight)
                        windowState.position = WindowPosition(0.dp, 0.dp)
                    }
                    else{
                        windowState.size = DpSize(previousWidth.value, previousHeight.value)
                        windowState.position = previousPosition.value
                    }

                    isMaximized.value = !isMaximized.value
                }
                .onPointerEvent(PointerEventType.Enter) { active = true }
                .onPointerEvent(PointerEventType.Exit) { active = false }
                .pointerInput(Unit){
                    awaitPointerEventScope {
                        while(true){
                            val event = awaitPointerEvent()
                            when (event.type) {
                                PointerEventType.Press -> isPressed = true    // 鼠标按下
                                PointerEventType.Release -> isPressed = false // 鼠标抬起
                                else -> {}
                            }
                        }
                    }
                }
                .background(color = if(!active || isPressed) Color.Transparent else Color(56, 58, 61)),
            painter = if(!isMaximized.value) painterResource(Res.drawable.title_maximize) else painterResource(Res.drawable.title_restore),
            contentDescription = if(!isMaximized.value) "最大化" else "还原窗口大小",
            tint = Color.White
        )
    }

}

@Composable
fun EscapeButton(
    titleHeight: Dp,
    iconSize: Dp
){
    var active by remember { mutableStateOf(false) }
    var isPressed by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.size(titleHeight),
        contentAlignment = Alignment.Center
    ){
        Icon(
            modifier = Modifier
                .size(if(!active || isPressed) (iconSize - 6.dp) else iconSize)
                .pointerHoverIcon(PointerIcon.Hand)
                .onClick{ exitProcess(0) }
                .onPointerEvent(PointerEventType.Enter) { active = true }
                .onPointerEvent(PointerEventType.Exit) { active = false }
                .pointerInput(Unit){
                    awaitPointerEventScope {
                        while(true){
                            val event = awaitPointerEvent()
                            when (event.type) {
                                PointerEventType.Press -> isPressed = true    // 鼠标按下
                                PointerEventType.Release -> isPressed = false // 鼠标抬起
                                else -> {}
                            }
                        }
                    }
                }
                .background(color = if(!active || isPressed) Color.Transparent else Color(56, 58, 61)),
            painter = painterResource(Res.drawable.title_escape),
            contentDescription = "退出程序",
            tint = Color.White
        )
    }
}