package pers.lolicer.wotascope.settings

import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.contains
import com.russhwolf.settings.set
import java.io.File
import java.util.prefs.Preferences


object SettingsManager {
    val settings: Settings = getDesktopSettings().apply{
        initDefaultValues()
    }

    private fun getDesktopSettings(): PreferencesSettings {
        val preferences = Preferences.userRoot()
        return PreferencesSettings(preferences)
    }

    private fun Settings.initDefaultValues(){
        // 关于：IS_FIRST_LAUNCH
        // 若无此项设置，只能保证初次安装本应用的机器，默认目录会被定位到正确位置
        // 添加此项设置，会通过安装目录下有没有temp_videos文件夹来判断此次启动是不是当前版本的初次启动，保证了用户安装新版本且安装到了其他位置时的默认路径的正确性

        if(!contains(SettingsKeys.IS_FIRST_LAUNCH)) {
            this[SettingsKeys.IS_FIRST_LAUNCH] = true
        }
        if(!File(System.getProperty("user.dir"), "temp_videos").exists()){
            this[SettingsKeys.IS_FIRST_LAUNCH] = true
        }

        if(!contains(SettingsKeys.PRE_ENCODING)) {
            this[SettingsKeys.PRE_ENCODING] = true
        }
        if (!contains(SettingsKeys.ENCODED_VIDEO_DIR) || this.getBooleanOrNull(SettingsKeys.IS_FIRST_LAUNCH)!!) {
            this[SettingsKeys.ENCODED_VIDEO_DIR] = File(System.getProperty("user.dir"), "temp_videos").path
            this[SettingsKeys.IS_FIRST_LAUNCH] = false
        }
        if(!contains(SettingsKeys.MAIN_WINDOW_WIDTH)) {
            this[SettingsKeys.MAIN_WINDOW_WIDTH] = 800
        }
        if(!contains(SettingsKeys.MAIN_WINDOW_HEIGHT)) {
            this[SettingsKeys.MAIN_WINDOW_HEIGHT] = 600
        }
    }
}
