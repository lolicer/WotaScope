package pers.lolicer.wotascope.components

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
    showDescription: Boolean,
    text: String,
    modifier: Modifier = Modifier.padding(start = 28.dp, end = 28.dp, bottom = 12.dp),
    isParagraph: Boolean = true
){
    AnimatedVisibility(
        visible = showDescription,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut()
    ) {
        Text(
            modifier = modifier,
            text = if(isParagraph)"    " else "" + text,
            color = Color.Gray,
            fontSize = 13.sp
        )
    }
}