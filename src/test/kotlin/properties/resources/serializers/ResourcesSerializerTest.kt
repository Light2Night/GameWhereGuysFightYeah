package properties.resources.serializers

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import properties.resources.Cost
import properties.resources.Reward
import properties.resources.UserResources
import properties.user.UserContainer

class ResourcesSerializerTest {
    @Test
    fun `test cost serializer`() {
        val json = """{"coins":3,"crystals":4,"exp":5}"""
        val data = Cost(3, 4, 5)

        assertEquals(json, Json.encodeToString(data))
    }

    @Test
    fun `test reward serializer`() {
        val json = """{"coins":3,"crystals":4,"exp":5}"""
        val data = Reward(3, 4, 5)

        assertEquals(json, Json.encodeToString(data))
    }

    @Test
    fun `test user resources serializer`() {
        val json = """{"coins":3,"crystals":4,"exp":5}"""
        val data = UserResources(3, 4, 5)

        assertEquals(json, Json.encodeToString(data))
    }

    @Test
    fun `test cost deserializer`() {
        val json = """{"coins":3,"crystals":4,"exp":5}"""
        val data = Cost(3, 4, 5)
        val deserializedData = Json.decodeFromString<Cost>(json)

        assertEquals(data.coins, deserializedData.coins)
        assertEquals(data.crystals, deserializedData.crystals)
        assertEquals(data.exp, deserializedData.exp)
    }

    @Test
    fun `test reward deserializer`() {
        val json = """{"coins":3,"crystals":4,"exp":5}"""
        val data = Reward(3, 4, 5)

        val deserializedData = Json.decodeFromString<Reward>(json)

        assertEquals(data.coins, deserializedData.coins)
        assertEquals(data.crystals, deserializedData.crystals)
        assertEquals(data.exp, deserializedData.exp)
    }

    @Test
    fun `test user resources deserializer`() {
        val userContainer = UserContainer()
        userContainer.load()

        val json = """{"coins":3,"crystals":4,"exp":5}"""
        val data = UserResources(3, 4, 5)

        val deserializedData = Json.decodeFromString<UserResources>(json)

        assertEquals(data.coins, deserializedData.coins)
        assertEquals(data.crystals, deserializedData.crystals)
        assertEquals(data.exp, deserializedData.exp)
    }
}