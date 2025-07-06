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
fun HeptalLayout(
    paths: List<String>,
    onMediaPlayerList: (List<EmbeddedMediaPlayer>) -> Unit
){
    if(paths.size != 7) throw Exception("程序错误。")

    val refs = createRefs()
    val panel1 = ConstrainedLayoutReference(refs.component1())
    val panel2 = ConstrainedLayoutReference(refs.component2())
    val panel3 = ConstrainedLayoutReference(refs.component3())
    val panel4 = ConstrainedLayoutReference(refs.component4())
    val panel5 = ConstrainedLayoutReference(refs.component5())
    val panel6 = ConstrainedLayoutReference(refs.component6())
    val panel7 = ConstrainedLayoutReference(refs.component7())

    ConstraintLayout {
        createHorizontalChain(panel1, panel2, chainStyle = ChainStyle.Packed)
        createHorizontalChain(panel3, panel4, panel5, chainStyle = ChainStyle.Spread)
        createHorizontalChain(panel6, panel7, chainStyle = ChainStyle.Packed)

        val panel1Constraint = Modifier.constrainAs(panel1){
            // start.linkTo(parent.start)
            // end.linkTo(panel2.start)
            top.linkTo(parent.top)
            bottom.linkTo(panel3.top)
        }
        val panel2Constraint = Modifier.constrainAs(panel2){
            // start.linkTo(panel1.end)
            // end.linkTo(parent.end)
            top.linkTo(panel1.top)
            bottom.linkTo(panel1.bottom)
        }
        val panel3Constraint = Modifier.constrainAs(panel3){
            // start.linkTo(parent.start)
            // end.linkTo(panel4.start)
            top.linkTo(panel1.bottom)
            bottom.linkTo(panel6.top)
        }
        val panel4Constraint = Modifier.constrainAs(panel4){
            // start.linkTo(panel3.end)
            // end.linkTo(panel5.start)
            top.linkTo(panel3.top)
            bottom.linkTo(panel3.bottom)
        }
        val panel5Constraint = Modifier.constrainAs(panel5){
            // start.linkTo(panel4.end)
            // end.linkTo(parent.end)
            top.linkTo(panel3.top)
            bottom.linkTo(panel3.bottom)
        }
        val panel6Constraint = Modifier.constrainAs(panel6){
            // start.linkTo(parent.start)
            // end.linkTo(panel7.start)
            top.linkTo(panel3.bottom)
            bottom.linkTo(parent.bottom)
        }
        val panel7Constraint = Modifier.constrainAs(panel7){
            // start.linkTo(panel6.end)
            // end.linkTo(parent.end)
            top.linkTo(panel6.top)
            bottom.linkTo(panel6.bottom)
        }
        val constraintList = listOf(panel1Constraint, panel2Constraint, panel3Constraint, panel4Constraint, panel5Constraint, panel6Constraint, panel7Constraint)

        val mediaPlayerList = mutableListOf<EmbeddedMediaPlayer>()
        for(i in 0 until paths.size){
            Box(modifier = constraintList[i].then(Modifier.fillMaxSize(1/3f))){
                SingleVideoPanelItem(
                    paths[i],
                    {mediaPlayer ->
                        mediaPlayerList.add(mediaPlayer)
                    },
                    {}
                )
            }
        }
        onMediaPlayerList(mediaPlayerList)
    }
}