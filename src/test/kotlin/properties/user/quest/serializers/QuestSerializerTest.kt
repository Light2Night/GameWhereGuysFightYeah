package properties.user.quest.serializers

import Game.Units.Characters.UnitTypes
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import properties.Properties
import properties.resources.Reward
import properties.user.quest.KillCharacterQuest
import properties.user.quest.KillNameQuest
import properties.user.quest.KillQuest

class QuestSerializerTest {
    @Test
    fun `test kill quest serializer`() {
        Properties.loadLanguage()

        val json = """{"type":"killQuest","id":1,"x":2,"y":3,"name":"","description":"","icon":"Test_Icon","requiredLevel":1,"reward":{"coins":3,"crystals":4,"exp":5},"target":2.0,"current":1.0,"additional":"HEALER"}"""
        val data = KillQuest(
            1,
            2,
            3,
            "",
            "",
            "Test_Icon",
            1,
            Reward(3, 4, 5),
            2.0,
            1.0,
            UnitTypes.HEALER,
        )

        assertEquals(json, Json.encodeToString(QuestSerializer, data))
    }

    @Test
    fun `test kill name quest serializer`() {
        Properties.loadLanguage()

        val json = """{"type":"killNameQuest","id":1,"x":2,"y":3,"name":"","description":"","icon":"Test_Icon","requiredLevel":1,"reward":{"coins":3,"crystals":4,"exp":5},"target":2.0,"current":1.0,"additional":"Test"}"""
        val data = KillNameQuest(
            1,
            2,
            3,
            "",
            "",
            "Test_Icon",
            1,
            Reward(3, 4, 5),
            2.0,
            1.0,
            "Test",
        )

        assertEquals(json, Json.encodeToString(QuestSerializer, data))
    }

    @Test
    fun `test kill character quest serializer`() {
        Properties.loadLanguage()

        val json = """{"type":"killCharacterQuest","id":1,"x":2,"y":3,"name":"","description":"","icon":"Test_Icon","requiredLevel":1,"reward":{"coins":3,"crystals":4,"exp":5},"target":2.0,"current":1.0,"additional":"1"}"""
        val data = KillCharacterQuest(
            1,
            2,
            3,
            "",
            "",
            "Test_Icon",
            1,
            Reward(3, 4, 5),
            2.0,
            1.0,
            1,
        )

        assertEquals(json, Json.encodeToString(QuestSerializer, data))
    }

    @Test
    fun `test kill quest deserializer`() {
        Properties.loadLanguage()

        val json = """{"type":"killQuest","id":1,"x":2,"y":3,"name":"","description":"","icon":"Test_Icon","requiredLevel":1,"reward":{"coins":3,"crystals":4,"exp":5},"target":2.0,"current":1.0,"additional":"HEALER"}"""
        val data = KillQuest(
            1,
            2,
            3,
            "",
            "",
            "Test_Icon",
            1,
            Reward(3, 4, 5),
            2.0,
            1.0,
            UnitTypes.HEALER,
        )
        val deserializedData = Json.decodeFromString<KillQuest>(json)

        assertEquals(data.id, deserializedData.id)
        assertEquals(data.x, deserializedData.x)
        assertEquals(data.y, deserializedData.y)
        assertEquals(data.name, deserializedData.name)
        assertEquals(data.description, deserializedData.description)
        assertEquals(data.icon, deserializedData.icon)
        assertEquals(data.requiredLevel, deserializedData.requiredLevel)
        assertEquals(data.reward.coins, deserializedData.reward.coins)
        assertEquals(data.reward.crystals, deserializedData.reward.crystals)
        assertEquals(data.reward.exp, deserializedData.reward.exp)
        assertEquals(data.target, deserializedData.target)
        assertEquals(data.current, deserializedData.current)
        assertEquals(data.unitType, deserializedData.unitType)
    }

    @Test
    fun `test kill name quest deserializer`() {
        Properties.loadLanguage()

        val json = """{"type":"killNameQuest","id":1,"x":2,"y":3,"name":"","description":"","icon":"Test_Icon","requiredLevel":1,"reward":{"coins":3,"crystals":4,"exp":5},"target":2.0,"current":1.0,"additional":"Test"}"""
        val data = KillNameQuest(
            1,
            2,
            3,
            "",
            "",
            "Test_Icon",
            1,
            Reward(3, 4, 5),
            2.0,
            1.0,
            "Test",
        )
        val deserializedData = Json.decodeFromString<KillNameQuest>(json)

        assertEquals(data.id, deserializedData.id)
        assertEquals(data.x, deserializedData.x)
        assertEquals(data.y, deserializedData.y)
        assertEquals(data.name, deserializedData.name)
        assertEquals(data.description, deserializedData.description)
        assertEquals(data.icon, deserializedData.icon)
        assertEquals(data.requiredLevel, deserializedData.requiredLevel)
        assertEquals(data.reward.coins, deserializedData.reward.coins)
        assertEquals(data.reward.crystals, deserializedData.reward.crystals)
        assertEquals(data.reward.exp, deserializedData.reward.exp)
        assertEquals(data.target, deserializedData.target)
        assertEquals(data.current, deserializedData.current)
        assertEquals(data.charName, deserializedData.charName)
    }

    @Test
    fun `test kill character quest deserializer`() {
        Properties.loadLanguage()

        val json = """{"type":"killCharacterQuest","id":1,"x":2,"y":3,"name":"","description":"","icon":"Test_Icon","requiredLevel":1,"reward":{"coins":3,"crystals":4,"exp":5},"target":2.0,"current":1.0,"additional":"1"}"""
        val data = KillCharacterQuest(
            1,
            2,
            3,
            "",
            "",
            "Test_Icon",
            1,
            Reward(3, 4, 5),
            2.0,
            1.0,
            1,
        )
        val deserializedData = Json.decodeFromString<KillCharacterQuest>(json)

        assertEquals(data.id, deserializedData.id)
        assertEquals(data.x, deserializedData.x)
        assertEquals(data.y, deserializedData.y)
        assertEquals(data.name, deserializedData.name)
        assertEquals(data.description, deserializedData.description)
        assertEquals(data.icon, deserializedData.icon)
        assertEquals(data.requiredLevel, deserializedData.requiredLevel)
        assertEquals(data.reward.coins, deserializedData.reward.coins)
        assertEquals(data.reward.crystals, deserializedData.reward.crystals)
        assertEquals(data.reward.exp, deserializedData.reward.exp)
        assertEquals(data.target, deserializedData.target)
        assertEquals(data.current, deserializedData.current)
        assertEquals(data.charID, deserializedData.charID)
    }
}