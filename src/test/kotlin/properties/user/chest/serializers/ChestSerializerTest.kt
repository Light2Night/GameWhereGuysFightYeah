package properties.user.chest.serializers

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import properties.user.chest.*

class ChestSerializerTest {
    @Test
    fun `test chest serializer`() {
        val chestJson = """{"id":1,"type":"Wooden","sheet":{"amount":3,"coins":{"type":"Coins","weight":1,"from":1,"to":2},"crystals":{"type":"Crystals","weight":1,"from":1,"to":2},"oneStar":{"type":"OneStar","weight":1,"from":1,"to":2},"twoStar":{"type":"TwoStar","weight":1,"from":1,"to":2},"threeStar":{"type":"ThreeStar","weight":1,"from":1,"to":2}}}"""
        val chestData = Chest(1, ChestType.Wooden, RewardSheet(
            3,
            RewardSheetEntry(SheetEntryType.Coins, 1, 1..2),
            RewardSheetEntry(SheetEntryType.Crystals, 1, 1..2),
            RewardSheetEntry(SheetEntryType.OneStar, 1, 1..2),
            RewardSheetEntry(SheetEntryType.TwoStar, 1, 1..2),
            RewardSheetEntry(SheetEntryType.ThreeStar, 1, 1..2),
        ))

        assertEquals(chestJson, Json.encodeToString(chestData))
    }

    @Test
    fun `test chest deserializer`() {
        val json = """{"id":1,"type":"Wooden","sheet":{"amount":3,"coins":{"type":"Coins","weight":1,"from":1,"to":2},"crystals":{"type":"Crystals","weight":1,"from":1,"to":2},"oneStar":{"type":"OneStar","weight":1,"from":1,"to":2},"twoStar":{"type":"TwoStar","weight":1,"from":1,"to":2},"threeStar":{"type":"ThreeStar","weight":1,"from":1,"to":2}}}"""
        val data = Chest(1, ChestType.Wooden, RewardSheet(
            3,
            RewardSheetEntry(SheetEntryType.Coins, 1, 1..2),
            RewardSheetEntry(SheetEntryType.Crystals, 1, 1..2),
            RewardSheetEntry(SheetEntryType.OneStar, 1, 1..2),
            RewardSheetEntry(SheetEntryType.TwoStar, 1, 1..2),
            RewardSheetEntry(SheetEntryType.ThreeStar, 1, 1..2),
        ))
        val deserializedData = Json.decodeFromString<Chest>(json)

        assertEquals(data.id, deserializedData.id)
        assertEquals(data.type, deserializedData.type)
        assertEquals(data.sheet.amount, deserializedData.sheet.amount)
        assertEquals(data.sheet.coins.type, deserializedData.sheet.coins.type)
        assertEquals(data.sheet.coins.weight, deserializedData.sheet.coins.weight)
        assertEquals(data.sheet.coins.value, deserializedData.sheet.coins.value)
        assertEquals(data.sheet.crystals.type, deserializedData.sheet.crystals.type)
        assertEquals(data.sheet.crystals.weight, deserializedData.sheet.crystals.weight)
        assertEquals(data.sheet.crystals.value, deserializedData.sheet.crystals.value)
        assertEquals(data.sheet.oneStar.type, deserializedData.sheet.oneStar.type)
        assertEquals(data.sheet.oneStar.weight, deserializedData.sheet.oneStar.weight)
        assertEquals(data.sheet.oneStar.value, deserializedData.sheet.oneStar.value)
        assertEquals(data.sheet.twoStar.type, deserializedData.sheet.twoStar.type)
        assertEquals(data.sheet.twoStar.weight, deserializedData.sheet.twoStar.weight)
        assertEquals(data.sheet.twoStar.value, deserializedData.sheet.twoStar.value)
        assertEquals(data.sheet.threeStar.type, deserializedData.sheet.threeStar.type)
        assertEquals(data.sheet.threeStar.weight, deserializedData.sheet.threeStar.weight)
        assertEquals(data.sheet.threeStar.value, deserializedData.sheet.threeStar.value)
    }
}