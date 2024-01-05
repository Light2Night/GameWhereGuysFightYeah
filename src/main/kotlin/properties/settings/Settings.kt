package properties.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.serialization.*

@Serializable(with = SettingsSerializer::class)
class Settings(
    language: String = "en",
) {
    var language: String by mutableStateOf(language)
}