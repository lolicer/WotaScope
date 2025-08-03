package pers.lolicer.wotascope.components.videoLayout

import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintSet

fun pentaLayout(): ConstraintSet{
    return ConstraintSet{
        val guideline = createGuidelineFromTop(1/6f)
        val (panel1, panel2, panel3, panel4, panel5) = createRefsFor("panel1", "panel2", "panel3", "panel4", "panel5")

        createHorizontalChain(panel1, panel2, panel3, chainStyle = ChainStyle.Spread)
        createHorizontalChain(panel4, panel5, chainStyle = ChainStyle.Packed)

        constrain(panel1){
            top.linkTo(guideline)
            bottom.linkTo(panel4.top)
        }
        constrain(panel2){
            top.linkTo(panel1.top)
            bottom.linkTo(panel1.bottom)
        }
        constrain(panel3){
            top.linkTo(panel1.top)
            bottom.linkTo(panel1.bottom)
        }
        constrain(panel4){
            top.linkTo(panel1.bottom)
            bottom.linkTo(parent.bottom)
        }
        constrain(panel5){
            top.linkTo(panel4.top)
            bottom.linkTo(panel4.bottom)
        }
    }
}

// @Composable
// fun PentaLayout(
//     paths: List<String>,
//     // onMediaPlayerList: (List<EmbeddedMediaPlayer>) -> Unit
// ){
//     if(paths.size != 5) throw Exception("程序错误。")
//
//     val refs = createRefs()
//     val panel1 = ConstrainedLayoutReference(refs.component1())
//     val panel2 = ConstrainedLayoutReference(refs.component2())
//     val panel3 = ConstrainedLayoutReference(refs.component3())
//     val panel4 = ConstrainedLayoutReference(refs.component4())
//     val panel5 = ConstrainedLayoutReference(refs.component5())
//
//     ConstraintLayout {
//         val guideline = createGuidelineFromTop(1/6f)
//
//         createHorizontalChain(panel1, panel2, panel3, chainStyle = ChainStyle.Spread)
//         createHorizontalChain(panel4, panel5, chainStyle = ChainStyle.Packed)
//
//         val panel1Constraint = Modifier.constrainAs(panel1){
//             // start.linkTo(parent.start)
//             // end.linkTo(panel2.start)
//             top.linkTo(guideline)
//             bottom.linkTo(panel4.top)
//         }
//         val panel2Constraint = Modifier.constrainAs(panel2){
//             // start.linkTo(panel1.end)
//             // end.linkTo(panel3.start)
//             top.linkTo(panel1.top)
//             bottom.linkTo(panel1.bottom)
//         }
//         val panel3Constraint = Modifier.constrainAs(panel3){
//             // start.linkTo(panel2.end)
//             // end.linkTo(parent.end)
//             top.linkTo(panel1.top)
//             bottom.linkTo(panel1.bottom)
//         }
//         val panel4Constraint = Modifier.constrainAs(panel4){
//             // start.linkTo(parent.start)
//             // end.linkTo(panel5.start)
//             top.linkTo(panel1.bottom)
//             bottom.linkTo(parent.bottom)
//         }
//         val panel5Constraint = Modifier.constrainAs(panel5){
//             // start.linkTo(panel4.end)
//             // end.linkTo(parent.end)
//             top.linkTo(panel4.top)
//             bottom.linkTo(panel4.bottom)
//         }
//         val constraintList = listOf(panel1Constraint, panel2Constraint, panel3Constraint, panel4Constraint, panel5Constraint)
//
//         // val mediaPlayerList = mutableListOf<EmbeddedMediaPlayer>()
//         for(i in 0 until paths.size){
//             SingleVideoPanelItem(
//                 paths[i],
//                 // {mediaPlayer ->
//                 //     mediaPlayerList.add(mediaPlayer)
//                 // },
//                 // {},
//                 constraintList[i].then(Modifier.fillMaxSize(1/3f)),
//                 {}
//             )
//         }
//         // LaunchedEffect(mediaPlayerList.size){
//         //     if(mediaPlayerList.size == 5){
//         //         onMediaPlayerList(mediaPlayerList)
//         //     }
//         // }
//     }
// }