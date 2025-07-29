package pers.lolicer.wotascope.components.titleBar.settingsWindow

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.onClick
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.russhwolf.settings.set
import org.jetbrains.compose.resources.painterResource
import pers.lolicer.wotascope.components.utils.ExtensionUtils
import pers.lolicer.wotascope.settings.SettingsKeys
import pers.lolicer.wotascope.settings.SettingsManager.settings
import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.settings_folder
import wotascope.composeapp.generated.resources.settings_toggle_off
import wotascope.composeapp.generated.resources.settings_toggle_on
import kotlin.collections.plus

@Composable
fun MainSettingPanel(
    height: Dp
){
    val settingItemHeight = 50.dp

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .height(height)
            .fillMaxWidth()
            .background(Color(43, 45, 48))
            .verticalScroll(scrollState)
    ){
        PreEncodingSetting(settingItemHeight)

        Divider(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            color = Color(188, 190, 196)
        )

        EncodedVideoDirSetting(settingItemHeight)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PreEncodingSetting(
    height: Dp
){
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .padding(start = 28.dp, end = 28.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = "自动编码为全关键帧",
            color = Color.White,
            fontSize = 14.sp
        )

        val preEncoding = remember {mutableStateOf(settings.getBooleanOrNull(SettingsKeys.PRE_ENCODING)!!)}
        Icon(
            modifier = Modifier
                .size(28.dp)
                .pointerHoverIcon(PointerIcon.Hand)
                .onClick{
                    settings[SettingsKeys.PRE_ENCODING] = !preEncoding.value
                    preEncoding.value = !preEncoding.value
                    println(settings.getBooleanOrNull(SettingsKeys.PRE_ENCODING))

                    focusManager.clearFocus() // 用于处理EncodedVideoDirSetting()中点击BasicTextField之外的任意部分使之失去焦点的逻辑（遥遥领先JetBrains系列IDE！（不是））
                },
            painter = painterResource(
                if(preEncoding.value)
                    Res.drawable.settings_toggle_on
                else
                    Res.drawable.settings_toggle_off
            ),
            tint = if(preEncoding.value) Color(84, 138, 247) else Color(128, 128, 128),
            contentDescription = if(preEncoding.value) "点击以关闭" else "点击以开启"
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EncodedVideoDirSetting(
    height: Dp
){
    val encodedVideoDir = remember {mutableStateOf(settings.getStringOrNull(SettingsKeys.ENCODED_VIDEO_DIR)!!)}
    val isFocused = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .padding(start = 28.dp, end = 28.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            modifier = Modifier.weight(0.35f),
            text = "临时视频路径",
            color = Color.White,
            fontSize = 14.sp
        )

        BasicTextField(
            modifier = Modifier
                .height(height)
                .weight(0.65f)
                .padding(vertical = 8.dp)
                .onFocusChanged{
                    isFocused.value = it.isFocused
                },
            value = encodedVideoDir.value,
            onValueChange = {
                println("TextField Value Changed.")
                encodedVideoDir.value = it
            },
            singleLine = true,
            readOnly = true,
            textStyle = TextStyle(
                color = (
                    if(isFocused.value)
                        Color.White
                    else
                        Color(188, 190, 196)
                ),
                fontSize = 14.sp
            ),
            cursorBrush = SolidColor(Color.White),
            decorationBox = {innerTextField ->
                Row(
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = (
                                if(isFocused.value)
                                    Color(53, 116, 240)
                                else
                                    Color(78, 81, 87)
                            ),
                            shape = RoundedCornerShape(4.dp)
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Box(Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                    ) {
                        innerTextField()
                    }
                    Icon(
                        modifier = Modifier
                            .size(20.dp + (8*2).dp)
                            .padding(horizontal = 8.dp)
                            .pointerHoverIcon(PointerIcon.Hand)
                            .onClick{
                                val selectDir = ExtensionUtils().selectDir()
                                if(selectDir != null){
                                    encodedVideoDir.value = selectDir
                                    settings[SettingsKeys.ENCODED_VIDEO_DIR] = selectDir
                                }
                            },
                        painter = painterResource(Res.drawable.settings_folder),
                        contentDescription = "选择编码视频临时存放路径",
                        tint = Color(188, 190, 196)
                    )
                }
            }
        )
    }
}