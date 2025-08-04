package pers.lolicer.wotascope.components.titleBar.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import wotascope.composeapp.generated.resources.Res
import wotascope.composeapp.generated.resources.wotascope_icon

@Composable
fun TitleIcon(
    titleHeight: Dp
){
    Box(
        contentAlignment = Alignment.Companion.Center,
        modifier = Modifier.Companion.size(titleHeight)
    ) {
        Icon(
            modifier = Modifier.Companion.size(24.dp),
            painter = painterResource(Res.drawable.wotascope_icon),
            contentDescription = "图标",
            tint = Color.Companion.White
        )
    }

}