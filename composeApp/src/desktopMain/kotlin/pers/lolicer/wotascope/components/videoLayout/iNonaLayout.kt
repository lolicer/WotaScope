package pers.lolicer.wotascope.components.videoLayout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import pers.lolicer.wotascope.components.singleVideoBar.SingleVideoPanelItem
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer

@Composable
fun NonaLayout(
    paths: List<String>,
    onMediaPlayerList: (List<EmbeddedMediaPlayer>) -> Unit
){
    if(paths.size != 9) throw Exception("程序错误。")

    val refs = createRefs()
    val panel1 = ConstrainedLayoutReference(refs.component1())
    val panel2 = ConstrainedLayoutReference(refs.component2())
    val panel3 = ConstrainedLayoutReference(refs.component3())
    val panel4 = ConstrainedLayoutReference(refs.component4())
    val panel5 = ConstrainedLayoutReference(refs.component5())
    val panel6 = ConstrainedLayoutReference(refs.component6())
    val panel7 = ConstrainedLayoutReference(refs.component7())
    val panel8 = ConstrainedLayoutReference(refs.component8())
    val panel9 = ConstrainedLayoutReference(refs.component9())

    ConstraintLayout {
        createHorizontalChain(panel1, panel2, panel3, chainStyle = ChainStyle.Spread)
        createHorizontalChain(panel4, panel5, panel6, chainStyle = ChainStyle.Spread)
        createHorizontalChain(panel7, panel8, panel9, chainStyle = ChainStyle.Packed)

        val panel1Constraint = Modifier.constrainAs(panel1){
            // start.linkTo(parent.start)
            // end.linkTo(panel2.start)
            top.linkTo(parent.top)
            bottom.linkTo(panel4.top)
        }
        val panel2Constraint = Modifier.constrainAs(panel2){
            // start.linkTo(panel1.end)
            // end.linkTo(panel3.start)
            top.linkTo(panel1.top)
            bottom.linkTo(panel1.bottom)
        }
        val panel3Constraint = Modifier.constrainAs(panel3){
            // start.linkTo(panel2.end)
            // end.linkTo(parent.end)
            top.linkTo(panel1.top)
            bottom.linkTo(panel1.bottom)
        }
        val panel4Constraint = Modifier.constrainAs(panel4){
            // start.linkTo(parent.start)
            // end.linkTo(panel5.start)
            top.linkTo(panel2.bottom)
            bottom.linkTo(panel7.top)
        }
        val panel5Constraint = Modifier.constrainAs(panel5){
            // start.linkTo(panel4.end)
            // end.linkTo(panel6.start)
            top.linkTo(panel4.top)
            bottom.linkTo(panel4.bottom)
        }
        val panel6Constraint = Modifier.constrainAs(panel6){
            // start.linkTo(panel5.end)
            // end.linkTo(parent.end)
            top.linkTo(panel4.top)
            bottom.linkTo(panel4.bottom)
        }
        val panel7Constraint = Modifier.constrainAs(panel7){
            // start.linkTo(parent.start)
            // end.linkTo(panel8.start)
            top.linkTo(panel4.bottom)
            bottom.linkTo(parent.bottom)
        }
        val panel8Constraint = Modifier.constrainAs(panel8){
            // start.linkTo(panel7.end)
            // end.linkTo(panel9.start)
            top.linkTo(panel7.top)
            bottom.linkTo(panel7.bottom)
        }
        val panel9Constraint = Modifier.constrainAs(panel9){
            // start.linkTo(panel8.end)
            // end.linkTo(parent.end)
            top.linkTo(panel7.top)
            bottom.linkTo(panel7.bottom)
        }
        val constraintList = listOf(panel1Constraint, panel2Constraint, panel3Constraint, panel4Constraint, panel5Constraint, panel6Constraint, panel7Constraint, panel8Constraint, panel9Constraint)

        val mediaPlayerList = mutableListOf<EmbeddedMediaPlayer>()
        for(i in 0 until paths.size){
            SingleVideoPanelItem(
                paths[i],
                {mediaPlayer ->
                    mediaPlayerList.add(mediaPlayer)
                },
                {},
                constraintList[i].then(Modifier.fillMaxSize(1/3f))
            )
        }
        onMediaPlayerList(mediaPlayerList)
    }
}