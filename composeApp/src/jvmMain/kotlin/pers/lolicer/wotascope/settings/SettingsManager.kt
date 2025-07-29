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
    }

    // private fun putIntValue(key: String, value: Int){
    //     settings.putInt(key, value)
    // }
    // private fun putBooleanValue(key: String, value: Boolean){
    //     settings.putBoolean(key, value)
    // }
    // private fun putStringValue(key: String, value: String){
    //     settings.putString(key, value)
    // }
    //
    // fun putValue(key: String, value: Int) = putIntValue(key, value)
    // fun putValue(key: String, value: Boolean) = putBooleanValue(key, value)
    // fun putValue(key: String, value: String) = putStringValue(key, value)
    //
    //
    // private fun setIntValue(key: String, value: Int){
    //     if(settings.hasKey(key)) settings[key] = value
    //     else throw IllegalArgumentException("There is no key named $key")
    // }
    // private fun setBooleanValue(key: String, value: Boolean){
    //     if(settings.hasKey(key)) settings[key] = value
    //     else throw IllegalArgumentException("There is no key named $key")
    // }
    // private fun setStringValue(key: String, value: String){
    //     if(settings.hasKey(key)) settings[key] = value
    //     else throw IllegalArgumentException("There is no key named $key")
    // }
    //
    // fun setValue(key: String, value: Int) = setIntValue(key, value)
    // fun setValue(key: String, value: Boolean) = setBooleanValue(key, value)
    // fun setValue(key: String, value: String) = setStringValue(key, value)
    //
    // fun getIntValue(key: String, defaultValue: Int = 0): Int {
    //     return settings[key, defaultValue]
    // }
    //
    // fun getBooleanValue(key: String, defaultValue: Boolean = false): Boolean {
    //     return settings[key, defaultValue]
    // }
    //
    // fun getStringValue(key: String, defaultValue: String = ""): String {
    //     return settings[key, defaultValue]
    // }
}
