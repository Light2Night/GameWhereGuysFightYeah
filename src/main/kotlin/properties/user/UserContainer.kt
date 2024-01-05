package properties.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File

class UserContainer {
    var user: User by mutableStateOf(User())
    private val json = Json {
        prettyPrint = true
        encodeDefaults = true
        ignoreUnknownKeys = true
    }
    private val file = File("data/user/user.json")

    fun load() {
        user = try {
            if (file.exists()) {
                json.decodeFromString(file.readText())
            } else {
                User()
            }
        } catch (e: Exception) {
            User()
        }
    }

    fun save() {
        File("data/user").mkdirs()
        file.writeText(json.encodeToString(user))
    }
}
