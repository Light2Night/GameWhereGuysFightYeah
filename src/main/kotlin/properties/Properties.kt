package properties

import properties.language.Language
import properties.language.LanguageContainer
import properties.settings.Settings
import properties.settings.SettingsContainer
import properties.style.Style
import properties.style.StyleContainer
import properties.user.User
import properties.user.UserContainer
import java.lang.Exception

object Properties {
    const val version = "0.0.0 alpha"

    private val userContainer: UserContainer = UserContainer()
    fun user(): User = userContainer.user
    fun loadUser() { userContainer.load() }
    fun saveUser() { userContainer.save() }

    private val languageContainer: LanguageContainer = LanguageContainer()
    fun language(): Language = languageContainer.language.value ?: throw Exception("Language not found")
    fun loadLanguage(lang: String = "en") { languageContainer.load(lang) }

    private val settingsContainer: SettingsContainer = SettingsContainer()
    fun settings(): Settings = settingsContainer.settings
    fun loadSettings() { settingsContainer.load() }
    fun saveSettings() { settingsContainer.save() }

    private val styleContainer: StyleContainer = StyleContainer()
    fun style(): Style = styleContainer.style
    fun loadStyle() { styleContainer.load() }
    fun saveStyle() { styleContainer.save() }
}
