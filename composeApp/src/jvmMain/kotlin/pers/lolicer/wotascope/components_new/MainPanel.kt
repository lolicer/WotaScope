package pers.lolicer.wotascope.components_new

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.constraintlayout.compose.ConstraintLayout
import pers.lolicer.wotascope.components_new.layoutSet.LayoutSet
import pers.lolicer.wotascope.components_new.singleVideoPanel.SingleVideoPanelItem
import pers.lolicer.wotascope.components_new.status.MediaPlayerListStatus
import pers.lolicer.wotascope.components_new.status.selectSize

@Composable
fun MainPanel(){
    val videoNumber = remember { mutableStateOf(MediaPlayerListStatus.list.value.size) }
    if(videoNumber.value != 0) {
        ConstraintLayout(
            LayoutSet.getLayoutSet(videoNumber.value)!!
        ) {
            for(i in 0 until MediaPlayerListStatus.list.value.size) {
                key(MediaPlayerListStatus.list.value[i]) {
                    SingleVideoPanelItem(
                        mediaPlayer = MediaPlayerListStatus.list.value[i].first,
                        constraint = Modifier.layoutId("panel${i + 1}")
                            .then(MediaPlayerListStatus.selectSize()!!)
                    )
                }
            }
        }
    }

    LaunchedEffect(MediaPlayerListStatus.list.value){
        videoNumber.value = MediaPlayerListStatus.list.value.size
    }
}