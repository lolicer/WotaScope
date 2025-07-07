package pers.lolicer.wotascope.components.bottomController

import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer

data class MediaState(
    val mediaPlayer: EmbeddedMediaPlayer,
    var isPlaying: Boolean,
    var isFinished: Boolean,
    var isSelected: Boolean
)