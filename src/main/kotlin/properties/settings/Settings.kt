package properties.settings

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
data class Settings(
    var language: String = "en",
    var attack_buttons: Int = 0,
) {
    companion object {
        fun createFromJson(jsonObject: JsonObject): Settings? {
            return try {
                val map = jsonObject.toMap()
                val newSettings = Settings()
                map["language"]?.jsonPrimitive?.contentOrNull?.let { newSettings.language = it }
                map["attack_buttons"]?.jsonPrimitive?.intOrNull?.let { newSettings.attack_buttons = it }

                newSettings
            } catch (e: Exception) {
                null
            }
        }
    }
}