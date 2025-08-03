package pers.lolicer.wotascope.utils

import java.io.File
import java.nio.file.Files

class FileUtils {
    // 在安装位置创建 temp_videos 文件夹
    fun createTempDirIfNotExists(){
        val tmpDir = File(System.getProperty("user.dir"), "temp_videos")
        if(!tmpDir.exists()){
            Files.createDirectories(tmpDir.toPath())
            val readmeFile = File(tmpDir, "readme.txt")
            readmeFile.writeText(
                "仙贝，Ciallo～(∠・ω< )⌒★\n" +
                "此文件夹用于存放程序运行中产生的编码后视频文件，程序关闭时会清空，不建议手动放入其它文件。\n" +
                "このフォルダは、プログラムの実行中に生成されたエンコードされたビデオファイルを格納するために使用されます。プログラムが閉じると空になり、他のファイルを手動で入れることは推奨されません。\n" +
                "This folder is used to store encoded video files generated during program execution. It will be cleared when the program is closed, and it is not recommended to manually insert other files.\n" +
                "跐攵閒夾痈チ洊瓬珵垿運荇φ浐泩dè編碼诟礻見頻攵閒，禾呈垿關閅嵵噲氵青啌，ト踺議掱動邡込娸鉈呅閒。"
            )
            println("Create dir: ${tmpDir.absolutePath}")
        }
    }

    // 清理 temp_video 文件夹内容
    fun clearTempDirIfExists(){
        val tmpDir = File(System.getProperty("user.dir"), "temp_videos")
        if(tmpDir.exists() && tmpDir.isDirectory){
            println(tmpDir.listFiles().size)
            tmpDir.listFiles().forEach { file ->
                println(file.name)
                if(file.name != "readme.txt"){
                    file.delete()
                }
            }
        }
    }
}