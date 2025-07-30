package pers.lolicer.wotascope.components.utils

import java.io.BufferedReader
import java.io.InputStreamReader

class ExecUtils {
    /**
     * 执行二进制命令。
     *
     * @param command 命令
     * @param autoPrint 是否自动打印执行信息
     * @return Pair格式：
     * ```
     *  -first：返回码
     *  -second：执行信息
     * ```
     */
    fun execute(
        command: Array<String>,
        autoPrint: Boolean = true
    ): Pair<Int, List<String>>{
        return try {
            val process = ProcessBuilder(*command)
                .redirectErrorStream(true) // 合并错误流到标准输出
                .start()

            // 读取输出流
            val outputLines = mutableListOf<String>()
            BufferedReader(InputStreamReader(process.inputStream)).use {reader ->
                reader.forEachLine { line ->
                    outputLines.add(line)
                    if(autoPrint) println(line)
                }
            }

            val exitCode = process.waitFor()
            exitCode to outputLines

        } catch (e: Exception) {
            e.printStackTrace()
            -1 to listOf("Error executing command: ${e.message}")
        }
    }

    /**
     * 快速检测第一秒是否存在非关键帧。（若视频小于一秒则检测整个视频）
     *
     * @param path 视频文件路径
     * @param autoPrint 是否自动打印执行信息
     * @return true表示第一秒均为关键帧，false表示存在非关键帧或检测失败
     */
    fun hasAllKeyFrames(
        path: String,
        autoPrint: Boolean = true
    ): Boolean{
        val cmd = arrayOf(
            "ffprobe",
            "-v", "error",
            "-select_streams", "v:0",
            "-show_entries", "frame=key_frame",
            "-of", "csv=p=0",
            "-read_intervals", "%+1",  // 只读取第一秒
            path
        )
        val (exitCode, output) = execute(cmd, autoPrint = autoPrint)
        return exitCode == 0 &&
                output.none { it == "0" } &&
                output.isNotEmpty()
    }
    /**
     * 将视频转换为全关键帧。
     *
     * @param path 原视频路径
     * @param targetDir 目标文件夹，格式：C:\\Users\\admin\\Downloads
     * @param autoPrint 是否自动打印执行信息
     *
     * @return Pair格式：
     * ```
     *  -first：Pair<Int, List<String>>
     *      -first：返回码
     *      -second：执行信息
     *  -second：String
     *      新视频的路径
     * ```
     */
    fun convertVideo(
        path: String,
        targetDir: String,
        autoPrint: Boolean = true
    ): Pair<Pair<Int, List<String>>, String> {
        val newPath = targetDir + "\\" + System.currentTimeMillis() + '_' + path.substringAfterLast("\\")
        val cmd = arrayOf(
            "ffmpeg",
            "-i", path,
            "-c:v", "libx264",
            "-g", "1",
            "-stats",
            newPath
        )
        return Pair(execute(cmd, autoPrint = autoPrint), newPath)
    }
}