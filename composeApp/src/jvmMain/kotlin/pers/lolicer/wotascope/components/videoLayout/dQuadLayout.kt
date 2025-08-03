package pers.lolicer.wotascope.components.videoLayout

import androidx.constraintlayout.compose.ConstraintSet

fun quadLayout(): ConstraintSet{
    return ConstraintSet{
        val (panel1, panel2, panel3, panel4) = createRefsFor("panel1", "panel2", "panel3", "panel4")

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
            bottom.linkTo(panel4.top)
        }
        constrain(panel3){
            start.linkTo(parent.start)
            end.linkTo(panel4.start)
            top.linkTo(panel1.bottom)
            bottom.linkTo(parent.bottom)
        }
        constrain(panel4){
            start.linkTo(panel3.end)
            end.linkTo(parent.end)
            top.linkTo(panel2.bottom)
            bottom.linkTo(parent.bottom)
        }
    }
}

// @Composable
// fun QuadLayout(
//     paths: List<String>,
//     // onMediaPlayerList: (List<EmbeddedMediaPlayer>) -> Unit
// ){
//     if(paths.size != 4) throw Exception("程序错误。")
//
//     val refs = createRefs()
//     val panel1 = ConstrainedLayoutReference(refs.component1())
//     val panel2 = ConstrainedLayoutReference(refs.component2())
//     val panel3 = ConstrainedLayoutReference(refs.component3())
//     val panel4 = ConstrainedLayoutReference(refs.component4())
//
//     ConstraintLayout {
//         val panel1Constraint = Modifier.constrainAs(panel1){
//             start.linkTo(parent.start)
//             end.linkTo(panel2.start)
//             top.linkTo(parent.top)
//             bottom.linkTo(panel3.top)
//         }
//         val panel2Constraint = Modifier.constrainAs(panel2){
//             start.linkTo(panel1.end)
//             end.linkTo(parent.end)
//             top.linkTo(parent.top)
//             bottom.linkTo(panel4.top)
//         }
//         val panel3Constraint = Modifier.constrainAs(panel3){
//             start.linkTo(parent.start)
//             end.linkTo(panel4.start)
//             top.linkTo(panel1.bottom)
//             bottom.linkTo(parent.bottom)
//         }
//         val panel4Constraint = Modifier.constrainAs(panel4){
//             start.linkTo(panel3.end)
//             end.linkTo(parent.end)
//             top.linkTo(panel2.bottom)
//             bottom.linkTo(parent.bottom)
//         }
//         val constraintList = listOf(panel1Constraint, panel2Constraint, panel3Constraint, panel4Constraint)
//
//         // val mediaPlayerList = mutableListOf<EmbeddedMediaPlayer>()
//         for(i in 0 until paths.size){
//             SingleVideoPanelItem(
//                 paths[i],
//                 // {mediaPlayer ->
//                 //     mediaPlayerList.add(mediaPlayer)
//                 // },
//                 // {},
//                 constraintList[i].then(Modifier.fillMaxSize(0.5f)),
//                 {}
//             )
//         }
//         // LaunchedEffect(mediaPlayerList.size){
//         //     if(mediaPlayerList.size == 4){
//         //         onMediaPlayerList(mediaPlayerList)
//         //     }
//         // }
//     }
// }