package properties.user.recruit.serializers

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import properties.user.recruit.*

class RecruitSerializerTest {
    @Test
    fun `test list recruits serializer`() {
        val json = """[{"id":0,"charID":0,"name":"test0","description":"description_test0","stars":1,"level":1,"profileImage":"characters/barbarian_placeholder.png","rawData":{"type":"BARBARIAN","maxHP":200,"damage":20,"damageDelta":10},"cost":null},{"id":1,"charID":1,"name":"test1","description":"description_test1","stars":1,"level":1,"profileImage":"characters/magician_placeholder.png","rawData":{"type":"MAGICIAN","maxHP":100,"damage":25,"damageDelta":20,"magicalEffectDamage":15,"magicalEffectTurns":2},"cost":null},{"id":2,"charID":2,"name":"test2","description":"description_test2","stars":1,"level":1,"profileImage":"characters/healer_placeholder.png","rawData":{"type":"HEALER","maxHP":125,"heal":15,"healingEffectHeal":7,"healingEffectTurns":3},"cost":null}]"""
        val recruitFactory = RecruitFactory()
        val data = listOf(
            recruitFactory.getPreset(0) ?: throw Exception(),
            recruitFactory.getPreset(1) ?: throw Exception(),
            recruitFactory.getPreset(2) ?: throw Exception(),
        )

        assertEquals(json, Json.encodeToString(data))
    }

    @Test
    fun `test single recruit deserializer`() {
        val json = """[{"id":0,"charID":0,"name":"test0","description":"description_test0","stars":1,"level":1,"profileImage":"characters/barbarian_placeholder.png","rawData":{"type":"BARBARIAN","maxHP":200,"damage":20,"damageDelta":10},"cost":null},{"id":1,"charID":1,"name":"test1","description":"description_test1","stars":1,"level":1,"profileImage":"characters/magician_placeholder.png","rawData":{"type":"MAGICIAN","maxHP":100,"damage":25,"damageDelta":20,"magicalEffectDamage":15,"magicalEffectTurns":2},"cost":null},{"id":2,"charID":2,"name":"test2","description":"description_test2","stars":1,"level":1,"profileImage":"characters/healer_placeholder.png","rawData":{"type":"HEALER","maxHP":125,"heal":15,"healingEffectHeal":7,"healingEffectTurns":3},"cost":null}]"""
        val recruitFactory = RecruitFactory()
        val data = listOf(
            recruitFactory.getPreset(0) ?: throw Exception(),
            recruitFactory.getPreset(1) ?: throw Exception(),
            recruitFactory.getPreset(2) ?: throw Exception(),
        )
        val deserializedData = Json.decodeFromString<List<Recruit>>(json)

        assertEquals(data[0].id, deserializedData[0].id)
        assertEquals(data[0].charID, deserializedData[0].charID)
        assertEquals(data[0].name, deserializedData[0].name)
        assertEquals(data[0].description, deserializedData[0].description)
        assertEquals(data[0].stars, deserializedData[0].stars)
        assertEquals(data[0].level, deserializedData[0].level)
        assertEquals(data[0].profileImage, deserializedData[0].profileImage)
        assertEquals((data[0].rawData as BarbarianData).maxHP, (deserializedData[0].rawData as BarbarianData).maxHP)
        assertEquals((data[0].rawData as BarbarianData).damage, (deserializedData[0].rawData as BarbarianData).damage)
        assertEquals((data[0].rawData as BarbarianData).damageDelta, (deserializedData[0].rawData as BarbarianData).damageDelta)
        assertEquals(data[0].cost?.coins, deserializedData[0].cost?.coins)
        assertEquals(data[0].cost?.crystals, deserializedData[0].cost?.crystals)
        assertEquals(data[0].cost?.exp, deserializedData[0].cost?.exp)

        assertEquals(data[1].id, deserializedData[1].id)
        assertEquals(data[1].charID, deserializedData[1].charID)
        assertEquals(data[1].name, deserializedData[1].name)
        assertEquals(data[1].description, deserializedData[1].description)
        assertEquals(data[1].stars, deserializedData[1].stars)
        assertEquals(data[1].level, deserializedData[1].level)
        assertEquals(data[1].profileImage, deserializedData[1].profileImage)
        assertEquals((data[1].rawData as MageData).maxHP, (deserializedData[1].rawData as MageData).maxHP)
        assertEquals((data[1].rawData as MageData).damage, (deserializedData[1].rawData as MageData).damage)
        assertEquals((data[1].rawData as MageData).damageDelta, (deserializedData[1].rawData as MageData).damageDelta)
        assertEquals((data[1].rawData as MageData).magicalEffectDamage, (deserializedData[1].rawData as MageData).magicalEffectDamage)
        assertEquals((data[1].rawData as MageData).magicalEffectTurns, (deserializedData[1].rawData as MageData).magicalEffectTurns)
        assertEquals(data[1].cost?.coins, deserializedData[1].cost?.coins)

        assertEquals(data[2].id, deserializedData[2].id)
        assertEquals(data[2].charID, deserializedData[2].charID)
        assertEquals(data[2].name, deserializedData[2].name)
        assertEquals(data[2].description, deserializedData[2].description)
        assertEquals(data[2].stars, deserializedData[2].stars)
        assertEquals(data[2].level, deserializedData[2].level)
        assertEquals(data[2].profileImage, deserializedData[2].profileImage)
        assertEquals((data[2].rawData as HealerData).maxHP, (deserializedData[2].rawData as HealerData).maxHP)
        assertEquals((data[2].rawData as HealerData).heal, (deserializedData[2].rawData as HealerData).heal)
        assertEquals((data[2].rawData as HealerData).healingEffectHeal, (deserializedData[2].rawData as HealerData).healingEffectHeal)
        assertEquals((data[2].rawData as HealerData).healingEffectTurns, (deserializedData[2].rawData as HealerData).healingEffectTurns)
        assertEquals(data[2].cost?.coins, deserializedData[2].cost?.coins)
    }
}