package pers.lolicer.wotascope.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BiliSlider(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    value: Float,
    onValueChange: (it: Float) -> Unit,
    onValueChangeFinished: () -> Unit
){
    Slider(
        modifier = Modifier
            .height(4.dp)
            .fillMaxWidth()
            .pointerHoverIcon(PointerIcon.Hand)
            .then(modifier),
        enabled = enabled,
        thumb = { Box{} },
        track = {
            Row(
                modifier = Modifier
                    .clip(shape = RectangleShape)
                    .height(4.dp)
            ){
                Box(modifier = Modifier.fillMaxWidth(value).height(4.dp).background(color = Color(	0, 191, 255)))
                Box(modifier = Modifier.fillMaxWidth().height(4.dp).background(color = Color.LightGray))
            }
        },
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        onValueChangeFinished = {
            onValueChangeFinished()
        }
    )
}