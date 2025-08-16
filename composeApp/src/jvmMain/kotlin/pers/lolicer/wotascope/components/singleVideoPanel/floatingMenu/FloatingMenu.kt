package pers.lolicer.wotascope.components.singleVideoPanel.floatingMenu

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import pers.lolicer.wotascope.components.singleVideoPanel.floatingMenu.menu.Menu
import pers.lolicer.wotascope.components.singleVideoPanel.floatingMenu.remove.Remove
import pers.lolicer.wotascope.components.singleVideoPanel.floatingMenu.selected.Selected
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FloatingMenu(
    modifier: Modifier,
    mediaPlayer: EmbeddedMediaPlayer,
    isHovered: Boolean,
    screenSize: IntSize
){
    val expandedMenu = remember { mutableStateOf(false) }

    AnimatedVisibility(
        modifier = modifier.offset(x = screenSize.width.dp - (screenSize.width / 40 * 4).dp, y = 10.dp),
        visible = isHovered || expandedMenu.value,
        enter = slideInHorizontally { it } + fadeIn(),
        exit = slideOutHorizontally { it } + fadeOut()
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(16))
                .background(color = Color.LightGray)
                .height((screenSize.width / 40 + 6).dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            val height = (screenSize.width / 40).dp
            Spacer(modifier = Modifier.width(3.dp).fillMaxHeight())

            Menu(mediaPlayer, expandedMenu, height)

            Spacer(modifier = Modifier.width(4.dp).fillMaxHeight())

            Selected(mediaPlayer, height)

            Spacer(modifier = Modifier.width(4.dp).fillMaxHeight())

            Remove(mediaPlayer, height)

            Spacer(modifier = Modifier.width(3.dp).fillMaxHeight())
        }
    }
}