package pers.lolicer.wotascope.components.videoLayout

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import pers.lolicer.wotascope.components.singleVideoBar.SingleVideoPanelItem
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer

@Composable
fun SingleLayout(
    paths: List<String>,
    onMediaPlayerList: (List<EmbeddedMediaPlayer>) -> Unit
){
    if(paths.size != 1) throw Exception("程序错误。")

    val refs = createRefs()
    val panel1 = ConstrainedLayoutReference(refs.component1())

    ConstraintLayout {
        val panel1Constraint = Modifier.constrainAs(panel1){
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        }
        val constraintList = listOf(panel1Constraint)

        val mediaPlayerList = mutableListOf<EmbeddedMediaPlayer>()
        for(i in 0 until paths.size){
            SingleVideoPanelItem(
                paths[i],
                {mediaPlayer ->
                    mediaPlayerList.add(mediaPlayer)
                },
                {},
                constraintList[i]
            )
        }
        onMediaPlayerList(mediaPlayerList)
    }
}