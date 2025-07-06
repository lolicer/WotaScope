package pers.lolicer.wotascope.components.bottomController

import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer

data object ControlledMediaPlayers{
    val value = mutableSetOf<EmbeddedMediaPlayer>()

    override fun toString(): String {
        return value.toString()
    }
}
