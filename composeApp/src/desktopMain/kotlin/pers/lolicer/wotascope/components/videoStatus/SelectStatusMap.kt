package pers.lolicer.wotascope.components.videoStatus

import uk.co.caprica.vlcj.player.base.MediaPlayer

data object SelectStatusMap{
    val mutableMap: MutableMap<MediaPlayer, Boolean> = mutableMapOf()
}