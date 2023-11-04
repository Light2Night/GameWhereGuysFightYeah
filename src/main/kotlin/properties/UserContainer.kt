package properties

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File

class UserContainer {
    lateinit var user: User
    private val json = Json {
        prettyPrint = true
        encodeDefaults = true
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
