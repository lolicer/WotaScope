package pers.lolicer.wotascope

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import com.sun.jna.NativeLibrary
import pers.lolicer.wotascope.components.bottomController.BottomController
import pers.lolicer.wotascope.components.titleBar.TitleBar
import pers.lolicer.wotascope.utils.FileUtils
import java.io.File

@Composable
fun App(
    windowState: WindowState,
    windowScope: FrameWindowScope
) {
    var isMaximized by remember { mutableStateOf(windowState.placement == WindowPlacement.Maximized) }

    MaterialTheme {
        val titleHeight = 30.dp
        val controllerHeight = 50.dp

        Column(
            modifier = if(!isMaximized) {
                Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        width = 1.dp,
                        color = Color(67, 68, 69),
                        shape = RoundedCornerShape(8.dp)
                    )
            } else Modifier
        ){
            windowScope.TitleBar(titleHeight, windowState, { isMaximized = !isMaximized })

            Box(
                Modifier
                    .fillMaxWidth()
                    .background(Color(30, 31, 34))
                    .height(windowState.size.height - titleHeight - controllerHeight)
            ){
                MainPanel()
            }

            BottomController(controllerHeight/* , mediaPlayerList.value */)
        }
    }
    // println(File(System.getProperty("compose.application.resources.dir")).resolve("ffmpeg").absolutePath)
    NativeLibrary.addSearchPath("libvlc", File(System.getProperty("compose.application.resources.dir")).resolve("VLC").absolutePath)

    FileUtils().createTempDirIfNotExists()
}