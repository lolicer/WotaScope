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
        if(!contains(SettingsKeys.PRE_ENCODING)) {
            this[SettingsKeys.PRE_ENCODING] = true
        }
        if (!contains(SettingsKeys.ENCODED_VIDEO_DIR)) {
            this[SettingsKeys.ENCODED_VIDEO_DIR] = File(System.getProperty("user.dir"), "temp_videos").path
        }
        if(!contains(SettingsKeys.MAIN_WINDOW_WIDTH)) {
            this[SettingsKeys.MAIN_WINDOW_WIDTH] = 800
        }
        if(!contains(SettingsKeys.MAIN_WINDOW_HEIGHT)) {
            this[SettingsKeys.MAIN_WINDOW_HEIGHT] = 600
        }
    }
}
