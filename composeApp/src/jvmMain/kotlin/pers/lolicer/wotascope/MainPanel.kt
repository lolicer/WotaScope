package pers.lolicer.wotascope

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import pers.lolicer.wotascope.components.layoutSet.LayoutSet
import pers.lolicer.wotascope.components.singleVideoPanel.SingleVideoPanelItem
import pers.lolicer.wotascope.status.MediaPlayerListStatus
import pers.lolicer.wotascope.status.OverlapState
import pers.lolicer.wotascope.status.OverlapStatus
import pers.lolicer.wotascope.status.selectSizeModifier

@Composable
fun MainPanel(){
    var videoNumber by remember { mutableStateOf(MediaPlayerListStatus.list.value.size) }

    val overlapped = OverlapStatus.status
    val overlappedConstraintSet = remember(overlapped){
        ConstraintSet {
            val (panel1, panel2) = createRefsFor("panel1", "panel2")

            constrain(panel1) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
            constrain(panel2) {
                start.linkTo(panel1.start)
                end.linkTo(panel1.end)
                top.linkTo(panel1.top)
                bottom.linkTo(panel1.bottom)
            }
        }
    }

    if(videoNumber != 0) {

        ConstraintLayout(
            if(videoNumber == 2 && overlapped == OverlapState.OVERLAP)
                overlappedConstraintSet
            else
                LayoutSet.getLayoutSet(videoNumber)!!
        ) {
            for(i in 0 until MediaPlayerListStatus.list.value.size) {
                key(MediaPlayerListStatus.list.value[i]) {
                    SingleVideoPanelItem(
                        mediaPlayer = MediaPlayerListStatus.list.value[i].first,
                        constraint = Modifier.layoutId("panel${i + 1}")
                            .then(MediaPlayerListStatus.selectSizeModifier()!!)
                    )
                }
            }
        }
    }

    LaunchedEffect(MediaPlayerListStatus.list.value){
        videoNumber = MediaPlayerListStatus.list.value.size
    }
}