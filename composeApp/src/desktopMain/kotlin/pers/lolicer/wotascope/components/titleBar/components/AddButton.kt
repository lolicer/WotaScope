package pers.lolicer.wotascope.components.titleBar.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.onClick
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pers.lolicer.wotascope.components.utils.ExecUtils
import pers.lolicer.wotascope.components.utils.ExtensionUtils
import pers.lolicer.wotascope.settings.SettingsKeys
import pers.lolicer.wotascope.settings.SettingsManager

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun AddButton(
    titleHeight: Dp,
    paths: MutableState<List<String>>,
    onEncodeStart: () -> Unit,
    onEncodeFinish: () -> Unit
){
    val scope = rememberCoroutineScope()

    var active by remember {mutableStateOf(false)}
    var isPressed by remember {mutableStateOf(false)}

    Text(
        "添加",
        color = Color.Companion.White,
        fontSize = 14.sp,
        modifier = Modifier.Companion
            .offset(y = 4.dp)
            .height(titleHeight - 8.dp)
            .pointerHoverIcon(PointerIcon.Companion.Hand)
            .onPointerEvent(PointerEventType.Companion.Enter) {active = true}
            .onPointerEvent(PointerEventType.Companion.Exit) {active = false}
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while(true) {
                        val event = awaitPointerEvent()
                        when(event.type) {
                            PointerEventType.Companion.Press -> isPressed = true    // 鼠标按下
                            PointerEventType.Companion.Release -> isPressed = false // 鼠标抬起
                            else -> {}
                        }
                    }
                }
            }
            .onClick {
                val selectFile = ExtensionUtils().selectFile()
                val newPaths = mutableListOf<String>()
                if(selectFile != null) {
                    onEncodeStart()

                    scope.launch(Dispatchers.IO) {
                        selectFile.forEach {
                            var path = it
                            val preEncoding =
                                SettingsManager.settings.getBooleanOrNull(SettingsKeys.PRE_ENCODING)!!
                            if(preEncoding) {
                                if(!ExecUtils().hasAllKeyFrames(path, false)) {
                                    path = ExecUtils().convertVideo(
                                        path = path,
                                        targetDir = SettingsManager.settings.getStringOrNull(
                                            SettingsKeys.ENCODED_VIDEO_DIR
                                        )!!,
                                        autoPrint = true
                                    ).second
                                }
                            }
                            newPaths.add(path)

                        }

                        if(paths.value.size + newPaths.size <= 9) {
                            paths.value = paths.value + newPaths
                        }
                        
                        onEncodeFinish()
                    }
                }
            }
            .background(color = if(active) Color(56, 58, 61) else Color.Companion.Transparent)
            .drawBehind {
                val textHeight = size.height
                val textWidth = size.width
                val strokeWidth = 1f

                if(active && !isPressed) {
                    drawLine(
                        color = Color.Companion.White,
                        start = Offset(0f, textHeight - 2),
                        end = Offset(textWidth, textHeight - 2),
                        strokeWidth = strokeWidth
                    )
                }
            }
    )
}