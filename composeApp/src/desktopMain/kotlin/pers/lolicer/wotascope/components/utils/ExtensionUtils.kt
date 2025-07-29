package pers.lolicer.wotascope.components.utils

import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer
import java.awt.FileDialog
import java.awt.Frame
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.URI
import javax.swing.JFileChooser
import javax.swing.filechooser.FileSystemView

class ExtensionUtils {
    // 控制暂停和开始
    fun changePauseState(mediaPlayer: EmbeddedMediaPlayer){
        if(mediaPlayer.status().isPlaying && mediaPlayer.status().canPause()){ // 正在播放
            mediaPlayer.controls().pause()
        }
        else{
            mediaPlayer.controls().play()
            mediaPlayer.controls().play()
        }
    }

    // 从资源管理器选择文件
    fun selectFile(): List<String>? {

        val dialog =
            FileDialog(Frame(), "请选择最多9个视频文件 | Ciallo～(∠・ω< )⌒★", FileDialog.LOAD)
        dialog.isMultipleMode = true
        dialog.setFile("*.mp4;*.wav;*.avi;*.mkv;*.flv;*.mov")
        dialog.isVisible = true

        val paths = mutableListOf<String>()
        for(file in dialog.files)
            paths.add(file.path)

        return paths.toList()
    }

    // 从资源管理器选择文件夹
    fun selectDir(): String?{
        val chooser = JFileChooser(FileSystemView.getFileSystemView()).apply {
            dialogTitle = "请选择文件夹 | Ciallo～(∠・ω< )⌒★"
            fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
            isAcceptAllFileFilterUsed = false
        }

        return when (chooser.showOpenDialog(Frame())) {
            JFileChooser.APPROVE_OPTION -> chooser.selectedFile?.absolutePath
            else -> null
        }
    }

    // 转换路径：将从mediaplayer.media.info.mrl获取的路径转为paths中存储的路径
    fun convertFileUrlToPath(url: String): String {
        return File(URI.create(url)).path
    }
}