package pers.lolicer.wotascope.components.selectStatusMap

import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer

data object SelectStatusMap{
    val mutableMap: MutableMap<EmbeddedMediaPlayer, Boolean> = mutableMapOf()
}