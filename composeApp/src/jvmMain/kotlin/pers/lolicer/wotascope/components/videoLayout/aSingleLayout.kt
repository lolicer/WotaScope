package pers.lolicer.wotascope.components.videoLayout

import androidx.constraintlayout.compose.ConstraintSet

fun singleLayout(): ConstraintSet{
    // val refs = createRefs()
    // val panel1 = ConstrainedLayoutReference(refs.component1())
    return ConstraintSet{
        val panel1 = createRefFor("panel1")
        constrain(panel1){
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        }
    }
}

// @Composable
// fun SingleLayout(
//     paths: List<String>,
//     // onMediaPlayerList: (List<EmbeddedMediaPlayer>) -> Unit
// ){
//     if(paths.size != 1) throw Exception("程序错误。")
//
//     val refs = createRefs()
//     val panel1 = ConstrainedLayoutReference(refs.component1())
//
//     ConstraintLayout {
//         val panel1Constraint = Modifier.constrainAs(panel1){
//             start.linkTo(parent.start)
//             end.linkTo(parent.end)
//             top.linkTo(parent.top)
//             bottom.linkTo(parent.bottom)
//         }
//         val constraintList = listOf(panel1Constraint)
//
//         // val mediaPlayerList = mutableListOf<EmbeddedMediaPlayer>()
//         for(i in 0 until paths.size){
//             SingleVideoPanelItem(
//                 paths[i],
//                 // {mediaPlayer ->
//                 //     mediaPlayerList.add(mediaPlayer)
//                 // },
//                 // {},
//                 constraintList[i],
//                 {}
//             )
//         }
//         // LaunchedEffect(mediaPlayerList.size){
//         //     if(mediaPlayerList.size == 1){
//         //         onMediaPlayerList(mediaPlayerList)
//         //     }
//         // }
//     }
// }