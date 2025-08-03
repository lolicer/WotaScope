package pers.lolicer.wotascope.components.videoLayout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.constraintlayout.compose.ConstraintLayout
import pers.lolicer.wotascope.components.singleVideoBar.SingleVideoPanelItem
import pers.lolicer.wotascope.components.videoStatus.MediaPlayerListStatus
import pers.lolicer.wotascope.selectLayout
import pers.lolicer.wotascope.selectSize

@Composable
fun MainPanel(
    paths: MutableState<List<String>>
){
    ConstraintLayout(paths.selectLayout()!!){
        for(i in 0 until paths.value.size) {
            key(paths.value[i]){
                SingleVideoPanelItem(
                    paths.value[i],
                    Modifier.layoutId("panel${i + 1}").then(paths.selectSize()!!),
                    onRemove = { removedPath ->
                        // val removedPath = ExtensionUtils().convertFileUrlToPath(removedMrl)
                        //
                        println(removedPath.substringAfterLast('\\'))
                        println(paths.value.map{ path ->
                            path.substringAfterLast('\\')
                        })

                        paths.value = paths.value.toMutableList().apply{
                            removeIf { path -> path == removedPath }
                        }
                        // MediaPlayerListStatus.mutableMap.value = MediaPlayerListStatus.mutableMap.value.filter{ elem ->
                        //     elem.key.media().info().mrl() != removedMrl
                        // }.toMutableMap()
                        println("${paths.value.size} ${MediaPlayerListStatus.mutableMap.value.size}")

                        println(paths.value.map{ path ->
                            path.substringAfterLast('\\')
                        })
                    }
                )
            }
        }
    }
}