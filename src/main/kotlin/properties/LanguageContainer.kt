package properties

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File

class LanguageContainer {
    val language: MutableState<Language?> = mutableStateOf(null)
    private val json = Json { prettyPrint = true }

    fun load(lang: String = "en") {
        val file = File("data/language/${lang}.json")
        if (!file.exists()) return

        language.value = json.decodeFromString(file.readText())
    }
}
