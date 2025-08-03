package pers.lolicer.wotascope

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.constraintlayout.compose.ConstraintSet
import com.sun.jna.NativeLibrary
import pers.lolicer.wotascope.components.bottomController.BottomController
import pers.lolicer.wotascope.components.titleBar.TitleBar
import pers.lolicer.wotascope.components.videoLayout.MainPanel
import pers.lolicer.wotascope.components.videoLayout.dualLayout
import pers.lolicer.wotascope.components.videoLayout.heptalLayout
import pers.lolicer.wotascope.components.videoLayout.hexaLayout
import pers.lolicer.wotascope.components.videoLayout.nonaLayout
import pers.lolicer.wotascope.components.videoLayout.octaLayout
import pers.lolicer.wotascope.components.videoLayout.pentaLayout
import pers.lolicer.wotascope.components.videoLayout.quadLayout
import pers.lolicer.wotascope.components.videoLayout.singleLayout
import pers.lolicer.wotascope.components.videoLayout.tripleLayout
import java.io.File
import kotlin.collections.emptyList

@Composable
fun App(
    windowState: WindowState,
    windowScope: FrameWindowScope
) {
    val paths = remember { mutableStateOf<List<String>>(emptyList()) }
    // val mediaPlayerList = remember { mutableStateOf<List<EmbeddedMediaPlayer>>(emptyList()) }

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
            windowScope.TitleBar(titleHeight, windowState, paths, { isMaximized = (windowState.placement == WindowPlacement.Maximized) })

            Box(
                Modifier
                    .fillMaxWidth()
                    .background(Color(30, 31, 34))
                    .height(windowState.size.height - titleHeight - controllerHeight)
            ){
                /*
                // when(paths.value.size){
                //     0 -> { /* 取消选择 */ }
                //     1 -> { SingleLayout(
                //             paths = paths.value,
                //             // {
                //             //     mediaPlayerList.value = it
                //             //     println("mediaPlayerList.value = ${mediaPlayerList.value}")
                //             //     it.forEach{ mediaPlayer ->
                //             //         // SelectStatusMap.mutableMap.putIfAbsent(mediaPlayer, true)
                //             //         // AudioStatus.mutableMap.putIfAbsent(mediaPlayer, 100)
                //             //         // FinishStatusMap.mutableMap.putIfAbsent(mediaPlayer, false)
                //             //
                //             //         MediaPlayerListStatus.mutableMap.value.put(
                //             //             mediaPlayer,
                //             //             Status(isSelected = true, isFinished = false, volume = 100)
                //             //         )
                //             //     }
                //             // }
                //     )}
                //     2 -> { DualLayout(
                //             paths = paths.value,
                //             // {
                //             //     mediaPlayerList.value = it
                //             //     it.forEach{ mediaPlayer ->
                //             //         // SelectStatusMap.mutableMap.putIfAbsent(mediaPlayer, true)
                //             //         // AudioStatus.mutableMap.putIfAbsent(mediaPlayer, 100)
                //             //         // FinishStatusMap.mutableMap.putIfAbsent(mediaPlayer, false)
                //             //
                //             //         MediaPlayerListStatus.mutableMap.value.put(
                //             //             mediaPlayer,
                //             //             Status(isSelected = true, isFinished = false, volume = 100)
                //             //         )
                //             //     }
                //             // }
                //     )}
                //     3 -> { TripleLayout(
                //         paths.value,
                //         // {
                //         //     mediaPlayerList.value = it
                //         //     it.forEach{ mediaPlayer ->
                //         //         // SelectStatusMap.mutableMap.putIfAbsent(mediaPlayer, true)
                //         //         // AudioStatus.mutableMap.putIfAbsent(mediaPlayer, 100)
                //         //         // FinishStatusMap.mutableMap.putIfAbsent(mediaPlayer, false)
                //         //
                //         //         MediaPlayerListStatus.mutableMap.value.put(
                //         //             mediaPlayer,
                //         //             Status(isSelected = true, isFinished = false, volume = 100)
                //         //         )
                //         //     }
                //         // }
                //     )}
                //     4 -> { QuadLayout(
                //         paths.value,
                //         // {
                //         //     mediaPlayerList.value = it
                //         //     it.forEach{ mediaPlayer ->
                //         //         // SelectStatusMap.mutableMap.putIfAbsent(mediaPlayer, true)
                //         //         // AudioStatus.mutableMap.putIfAbsent(mediaPlayer, 100)
                //         //         // FinishStatusMap.mutableMap.putIfAbsent(mediaPlayer, false)
                //         //
                //         //         MediaPlayerListStatus.mutableMap.value.put(
                //         //             mediaPlayer,
                //         //             Status(isSelected = true, isFinished = false, volume = 100)
                //         //         )
                //         //     }
                //         // }
                //     )}
                //     5 -> { PentaLayout(
                //         paths.value,
                //         // {
                //         //     mediaPlayerList.value = it
                //         //     it.forEach{ mediaPlayer ->
                //         //         // SelectStatusMap.mutableMap.putIfAbsent(mediaPlayer, true)
                //         //         // AudioStatus.mutableMap.putIfAbsent(mediaPlayer, 100)
                //         //         // FinishStatusMap.mutableMap.putIfAbsent(mediaPlayer, false)
                //         //
                //         //         MediaPlayerListStatus.mutableMap.value.put(
                //         //             mediaPlayer,
                //         //             Status(isSelected = true, isFinished = false, volume = 100)
                //         //         )
                //         //     }
                //         // }
                //     )}
                //     6 -> { HexaLayout(
                //         paths.value,
                //         // {
                //         //     mediaPlayerList.value = it
                //         //     it.forEach{ mediaPlayer ->
                //         //         // SelectStatusMap.mutableMap.putIfAbsent(mediaPlayer, true)
                //         //         // AudioStatus.mutableMap.putIfAbsent(mediaPlayer, 100)
                //         //         // FinishStatusMap.mutableMap.putIfAbsent(mediaPlayer, false)
                //         //
                //         //         MediaPlayerListStatus.mutableMap.value.put(
                //         //             mediaPlayer,
                //         //             Status(isSelected = true, isFinished = false, volume = 100)
                //         //         )
                //         //     }
                //         // }
                //     )}
                //     7 -> { HeptalLayout(
                //         paths.value,
                //         // {
                //         //     mediaPlayerList.value = it
                //         //     it.forEach{ mediaPlayer ->
                //         //         // SelectStatusMap.mutableMap.putIfAbsent(mediaPlayer, true)
                //         //         // AudioStatus.mutableMap.putIfAbsent(mediaPlayer, 100)
                //         //         // FinishStatusMap.mutableMap.putIfAbsent(mediaPlayer, false)
                //         //
                //         //         MediaPlayerListStatus.mutableMap.value.put(
                //         //             mediaPlayer,
                //         //             Status(isSelected = true, isFinished = false, volume = 100)
                //         //         )
                //         //     }
                //         // }
                //     )}
                //     8 -> { OctaLayout(
                //         paths.value,
                //         // {
                //         //     mediaPlayerList.value = it
                //         //     it.forEach{ mediaPlayer ->
                //         //         // SelectStatusMap.mutableMap.putIfAbsent(mediaPlayer, true)
                //         //         // AudioStatus.mutableMap.putIfAbsent(mediaPlayer, 100)
                //         //         // FinishStatusMap.mutableMap.putIfAbsent(mediaPlayer, false)
                //         //
                //         //         MediaPlayerListStatus.mutableMap.value.put(
                //         //             mediaPlayer,
                //         //             Status(isSelected = true, isFinished = false, volume = 100)
                //         //         )
                //         //     }
                //         // }
                //     )}
                //     9 -> { NonaLayout(
                //         paths.value,
                //         // {
                //         //     mediaPlayerList.value = it
                //         //     it.forEach{ mediaPlayer ->
                //         //         // SelectStatusMap.mutableMap.putIfAbsent(mediaPlayer, true)
                //         //         // AudioStatus.mutableMap.putIfAbsent(mediaPlayer, 100)
                //         //         // FinishStatusMap.mutableMap.putIfAbsent(mediaPlayer, false)
                //         //
                //         //         MediaPlayerListStatus.mutableMap.value.put(
                //         //             mediaPlayer,
                //         //             Status(isSelected = true, isFinished = false, volume = 100)
                //         //         )
                //         //     }
                //         // }
                //     )}
                //
                //     else -> { /* 已经塞不下了 */ }
                // }
                */
                if(paths.value.isNotEmpty()){
                    MainPanel(paths = paths)
                }
            }

            BottomController(controllerHeight/* , mediaPlayerList.value */)

            // val ffmpegFile = File(System.getProperty("user.dir"), "temp_videos").path
            // println(ffmpegFile)
            // ExecUtils().convertVideo(
            //     path = "C:\\Users\\chang\\Downloads\\8saba.mp4",
            //     targetDir = ffmpegFile
            // )
        }
    }

    // val dllNames = listOf("libiconv-2", "libva", "libva_win32", "libwinpthread-1", "libx264-164", "zlib1")
    // dllNames.forEach{
    //     val dllPath = File("resources/ffmpeg/${it}.dll").absolutePath
    //     println(dllPath)
    //     System.load(dllPath)
    // }
    println(File(System.getProperty("compose.application.resources.dir")).resolve("ffmpeg").absolutePath)
    // System.setProperty("VLC_PLUGIN_PATH", File(System.getProperty("compose.application.resources.dir")).resolve("VLC").absolutePath + "\\plugins")
    // System.setProperty("jna.library.path", File(System.getProperty("compose.application.resources.dir")).resolve("VLC").absolutePath)
    NativeLibrary.addSearchPath("libvlc", File(System.getProperty("compose.application.resources.dir")).resolve("VLC").absolutePath)
    // System.load(File(System.getProperty("compose.application.resources.dir")).resolve("VLC").absolutePath + "\\libvlccore.dll")
    // System.load(File(System.getProperty("compose.application.resources.dir")).resolve("VLC").absolutePath + "\\libvlc.dll")
    // System.load(File(System.getProperty("compose.application.resources.dir")).resolve("VLC").absolutePath + "\\plugins\\misc")

    // println("jna.library.path = ${System.getProperty("jna.library.path")}")
    // println("VLC_PLUGIN_PATH = ${System.getProperty("VLC_PLUGIN_PATH")}")
}

fun MutableState<List<String>>.selectLayout(): ConstraintSet?{
    return when(this.value.size){
        0 -> null
        1 -> singleLayout()
        2 -> dualLayout()
        3 -> tripleLayout()
        4 -> quadLayout()
        5 -> pentaLayout()
        6 -> hexaLayout()
        7 -> heptalLayout()
        8 -> octaLayout()
        9 -> nonaLayout()
        else -> { throw Exception("程序错误：超出9个视频") }
    }
}

fun MutableState<List<String>>.selectSize(): Modifier?{
    return when(this.value.size){
        0 -> null
        1 -> Modifier
        2, 3, 4 -> Modifier.fillMaxSize(0.5f)
        5, 6, 7, 8, 9 -> Modifier.fillMaxSize(1/3f)
        else -> { throw Exception("程序错误：超出9个视频") }
    }
}