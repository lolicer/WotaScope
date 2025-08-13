package pers.lolicer.wotascope.components.bottomController

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pers.lolicer.wotascope.components.bottomController.components.FastForwardButton
import pers.lolicer.wotascope.components.bottomController.components.PauseButton
import pers.lolicer.wotascope.components.bottomController.components.RewindButton
import pers.lolicer.wotascope.components.bottomController.components.SkipBackButton
import pers.lolicer.wotascope.components.bottomController.components.SkipForwardButton
import pers.lolicer.wotascope.components.bottomController.components.SpeedButton
import pers.lolicer.wotascope.components.bottomController.components.Volume

@Composable
fun BottomController(
    controllerHeight: Dp
){
    Row(
        modifier = Modifier
            .height(controllerHeight)
            .fillMaxWidth()
            .background(Color(43, 45, 48)),
        verticalAlignment = Alignment.CenterVertically
    ){
        Spacer(Modifier.width((controllerHeight.value * 0.5).dp))
        Volume(Modifier.width(controllerHeight * 3), /* mediaPlayerList */)
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Center
        ){
            SkipBackButton(Modifier.size(controllerHeight), /* mediaPlayerList */)
            RewindButton(Modifier.size(controllerHeight), /* mediaPlayerList */)
            PauseButton(Modifier.size(controllerHeight), /* mediaPlayerList ,*/)
            FastForwardButton(Modifier.size(controllerHeight), /* mediaPlayerList */)
            SkipForwardButton(Modifier.size(controllerHeight), /* mediaPlayerList */)
        }

        Spacer(Modifier.width(controllerHeight * 2))
        SpeedButton(Modifier.size(controllerHeight))
        Spacer(Modifier.width((controllerHeight.value * 0.5).dp))
    }
}