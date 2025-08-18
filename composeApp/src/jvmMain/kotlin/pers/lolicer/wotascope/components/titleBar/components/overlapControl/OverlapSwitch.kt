package pers.lolicer.wotascope.components.titleBar.components.overlapControl

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import pers.lolicer.wotascope.components.DescriptionText
import pers.lolicer.wotascope.status.MediaPlayerListStatus
import pers.lolicer.wotascope.status.OverlapState
import pers.lolicer.wotascope.status.OverlapStatus
import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.settings_notice
import wotascope.composeapp.generated.resources.settings_toggle_off
import wotascope.composeapp.generated.resources.settings_toggle_on

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OverlapSwitch(
    height: Dp,
    width: Dp
){
    var overlapped by remember { mutableStateOf(OverlapStatus.status == OverlapState.OVERLAP) }

    var showDescription by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.width(width)
    ){
        Row(
            modifier = Modifier
                .height(height)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row{
                Text(
                    text = "重叠",
                    color = Color.LightGray,
                    fontSize = 14.sp
                )

                Icon(
                    modifier = Modifier
                        .size(height / 2)
                        .padding(start = 4.dp)
                        .pointerHoverIcon(PointerIcon.Hand)
                        .onClick{
                            showDescription = !showDescription
                        },
                    painter = painterResource(Res.drawable.settings_notice),
                    contentDescription = "说明",
                    tint = Color.Gray
                )
            }

            Icon(
                modifier = Modifier
                    .size(height)
                    .pointerHoverIcon(PointerIcon.Hand)
                    .onClick{
                        if(MediaPlayerListStatus.list.value.size == 2){
                            if(OverlapStatus.status == OverlapState.OVERLAP){
                                OverlapStatus.updateStatus(OverlapState.NOT_OVERLAP)
                            }
                            else if(OverlapStatus.status == OverlapState.NOT_OVERLAP){
                                OverlapStatus.updateStatus(OverlapState.OVERLAP)
                            }
                            overlapped = !overlapped
                        }
                    },
                painter = painterResource(if(overlapped) Res.drawable.settings_toggle_on else Res.drawable.settings_toggle_off),
                contentDescription = if(overlapped) "重叠中" else "未重叠",
                tint = if(overlapped) Color(84, 138, 247) else Color(128, 128, 128),

                )
        }
        DescriptionText(
            showDescription = showDescription,
            text = "此选项仅在两视频时有效。",
            modifier = Modifier.padding(start = 4.dp, end = 0.dp, bottom = 12.dp),
            isParagraph = false
        )
    }
}