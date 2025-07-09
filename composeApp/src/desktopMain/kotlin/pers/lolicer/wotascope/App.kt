package pers.lolicer.wotascope

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
//import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.WindowState
import pers.lolicer.wotascope.components.bottomController.BottomController
import pers.lolicer.wotascope.components.videoStatus.SelectStatusMap
import pers.lolicer.wotascope.components.titleBar.TitleBar
import pers.lolicer.wotascope.components.videoLayout.DualLayout
import pers.lolicer.wotascope.components.videoLayout.HeptalLayout
import pers.lolicer.wotascope.components.videoLayout.HexaLayout
import pers.lolicer.wotascope.components.videoLayout.NonaLayout
import pers.lolicer.wotascope.components.videoLayout.OctaLayout
import pers.lolicer.wotascope.components.videoLayout.PentaLayout
import pers.lolicer.wotascope.components.videoLayout.QuadLayout
import pers.lolicer.wotascope.components.videoLayout.SingleLayout
import pers.lolicer.wotascope.components.videoLayout.TripleLayout
import uk.co.caprica.vlcj.player.base.MediaPlayer
import kotlin.collections.emptyList

@Composable
@Preview
fun App(
    windowState: WindowState,
    windowScope: FrameWindowScope
) {

    val paths = remember { mutableStateOf<List<String>>(emptyList()) }
    val mediaPlayerList = remember { mutableStateOf<List<MediaPlayer>>(emptyList()) }

    MaterialTheme {
        val titleHeight = 30.dp
        val controllerHeight = 50.dp

        Column(
            Modifier.background(Color.White)
        ){
            windowScope.TitleBar(titleHeight, windowState, paths)

            Box(
                Modifier
                    .fillMaxWidth()
                    .background(Color(30, 31, 34))
                    .height(windowState.size.height - titleHeight - controllerHeight)
            ){
                when(paths.value.size){
                    0 -> { /* 取消选择 */ }
                    1 -> { SingleLayout(
                            paths = paths.value,
                            onMediaPlayerList = {
                                mediaPlayerList.value = it
                                println("mediaPlayerList.value = ${mediaPlayerList.value}")
                                it.forEach{ mediaPlayer ->
                                    SelectStatusMap.mutableMap.putIfAbsent(mediaPlayer, true)
                                }
                            }
                    )}
                    2 -> { DualLayout(
                            paths = paths.value,
                            onMediaPlayerList = {
                                mediaPlayerList.value = it
                                it.forEach{ mediaPlayer ->
                                    SelectStatusMap.mutableMap.putIfAbsent(mediaPlayer, true)
                                }
                            }
                    )}
                    3 -> { TripleLayout(
                        paths.value,
                        {
                            mediaPlayerList.value = it
                            it.forEach{ mediaPlayer ->
                                SelectStatusMap.mutableMap.putIfAbsent(mediaPlayer, true)
                            }
                        }
                    )}
                    4 -> { QuadLayout(
                        paths.value,
                        {
                            mediaPlayerList.value = it
                            it.forEach{ mediaPlayer ->
                                SelectStatusMap.mutableMap.putIfAbsent(mediaPlayer, true)
                            }
                        }
                    )}
                    5 -> { PentaLayout(
                        paths.value,
                        {
                            mediaPlayerList.value = it
                            it.forEach{ mediaPlayer ->
                                SelectStatusMap.mutableMap.putIfAbsent(mediaPlayer, true)
                            }
                        }
                    )}
                    6 -> { HexaLayout(
                        paths.value,
                        {
                            mediaPlayerList.value = it
                            it.forEach{ mediaPlayer ->
                                SelectStatusMap.mutableMap.putIfAbsent(mediaPlayer, true)
                            }
                        }
                    )}
                    7 -> { HeptalLayout(
                        paths.value,
                        {
                            mediaPlayerList.value = it
                            it.forEach{ mediaPlayer ->
                                SelectStatusMap.mutableMap.putIfAbsent(mediaPlayer, true)
                            }
                        }
                    )}
                    8 -> { OctaLayout(
                        paths.value,
                        {
                            mediaPlayerList.value = it
                            it.forEach{ mediaPlayer ->
                                SelectStatusMap.mutableMap.putIfAbsent(mediaPlayer, true)
                            }
                        }
                    )}
                    9 -> { NonaLayout(
                        paths.value,
                        {
                            mediaPlayerList.value = it
                            it.forEach{ mediaPlayer ->
                                SelectStatusMap.mutableMap.putIfAbsent(mediaPlayer, true)
                            }
                        }
                    )}

                    else -> { /* 已经塞不下了 */ }
                }
            }

            BottomController(controllerHeight, mediaPlayerList.value)
        }
    }
}