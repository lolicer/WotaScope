package pers.lolicer.wotascope.components.videoStatus

import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer

data object AudioStatus{
    val mutableMap: MutableMap<EmbeddedMediaPlayer, Int> = mutableMapOf()
    var globalVolume: Int = 100
}