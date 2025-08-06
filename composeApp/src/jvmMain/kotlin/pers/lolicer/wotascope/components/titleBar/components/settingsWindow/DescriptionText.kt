package pers.lolicer.wotascope.components.titleBar.components.settingsWindow

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DescriptionText(
    showDescription: MutableState<Boolean>,
    text: String
){
    AnimatedVisibility(
        visible = showDescription.value,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut()
    ) {
        Text(
            modifier = Modifier
                .padding(start = 28.dp, end = 28.dp, bottom = 12.dp),
            text = "    $text",
            color = Color.Gray,
            fontSize = 13.sp
        )
    }
}