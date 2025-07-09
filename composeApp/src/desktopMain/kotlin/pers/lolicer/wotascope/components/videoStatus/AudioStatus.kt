package pers.lolicer.wotascope.components.videoStatus

import uk.co.caprica.vlcj.player.base.MediaPlayer

data object AudioStatus{
    val mutableMap: MutableMap<MediaPlayer, Int> = mutableMapOf()
    var globalVolume: Int = 100
}