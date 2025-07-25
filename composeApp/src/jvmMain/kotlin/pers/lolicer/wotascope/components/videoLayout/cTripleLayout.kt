package pers.lolicer.wotascope.components.videoLayout

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import pers.lolicer.wotascope.components.singleVideoBar.SingleVideoPanelItem
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer

@Composable
fun TripleLayout(
    paths: List<String>,
    // onMediaPlayerList: (List<EmbeddedMediaPlayer>) -> Unit
){
    if(paths.size != 3) throw Exception("程序错误。")

    val refs = createRefs()
    val panel1 = ConstrainedLayoutReference(refs.component1())
    val panel2 = ConstrainedLayoutReference(refs.component2())
    val panel3 = ConstrainedLayoutReference(refs.component3())

    ConstraintLayout {
        val panel1Constraint = Modifier.constrainAs(panel1){
            start.linkTo(parent.start)
            end.linkTo(panel2.start)
            top.linkTo(parent.top)
            bottom.linkTo(panel3.top)
        }
        val panel2Constraint = Modifier.constrainAs(panel2){
            start.linkTo(panel1.end)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(panel3.top)
        }
        val panel3Constraint = Modifier.constrainAs(panel3){
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(panel1.bottom)
            bottom.linkTo(parent.bottom)
        }
        val constraintList = listOf(panel1Constraint, panel2Constraint, panel3Constraint)

        // val mediaPlayerList = mutableListOf<EmbeddedMediaPlayer>()
        for(i in 0 until paths.size){
            SingleVideoPanelItem(
                paths[i],
                // {mediaPlayer ->
                //     mediaPlayerList.add(mediaPlayer)
                // },
                // {},
                constraintList[i].then(Modifier.fillMaxSize(0.5f)),
                {}
            )
        }
        // LaunchedEffect(mediaPlayerList.size){
        //     if(mediaPlayerList.size == 3){
        //         onMediaPlayerList(mediaPlayerList)
        //     }
        // }
    }
}

fun tripleLayout(): ConstraintSet{
    return ConstraintSet{
        val (panel1, panel2, panel3) = createRefsFor("panel1", "panel2", "panel3")

        constrain(panel1){
            start.linkTo(parent.start)
            end.linkTo(panel2.start)
            top.linkTo(parent.top)
            bottom.linkTo(panel3.top)
        }
        constrain(panel2){
            start.linkTo(panel1.end)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(panel3.top)
        }
        constrain(panel3){
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(panel1.bottom)
            bottom.linkTo(parent.bottom)
        }
    }
}