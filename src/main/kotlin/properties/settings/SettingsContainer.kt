package properties.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File

class SettingsContainer {
    var settings: Settings by mutableStateOf(Settings())
    private val json = Json {
        prettyPrint = true
        encodeDefaults = true
        ignoreUnknownKeys = true
    }
    private val file = File("data/settings.json")

    fun load() {
        settings = if (file.exists()) {
            try {
                Json.decodeFromString<Settings>(file.readText())

            } catch (e: Exception) { Settings() }
        } else { Settings() }
    }

    fun save() {
        File("data").mkdirs()
        file.writeText(json.encodeToString(settings))
    }
}
