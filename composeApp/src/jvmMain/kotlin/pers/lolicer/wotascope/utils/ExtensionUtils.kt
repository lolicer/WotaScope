package pers.lolicer.wotascope.utils

import java.awt.FileDialog
import java.awt.Frame
import java.io.File
import java.net.URI
import javax.swing.JFileChooser
import javax.swing.filechooser.FileSystemView

class ExtensionUtils {
    // 从资源管理器选择文件
    fun selectFile(): List<String>? {

        val dialog = FileDialog(Frame(), "请选择最多9个视频文件 | Ciallo～(∠・ω< )⌒★", FileDialog.LOAD)
        dialog.isMultipleMode = true
        dialog.setFile("*.mp4;*.avi;*.mkv;*.flv;*.mov;*.wmv")
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