package properties.user.recruit.serializers

import Game.Units.Characters.UnitTypes
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import properties.user.recruit.BarbarianData
import properties.user.recruit.HealerData
import properties.user.recruit.MageData
import properties.user.recruit.RecruitData

object RecruitDataSerializer : JsonContentPolymorphicSerializer<RecruitData>(RecruitData::class) {
    override fun selectDeserializer(element: JsonElement) = when(UnitTypes.valueOf(element.jsonObject["type"]?.jsonPrimitive?.content ?: throw SerializationException("Missing type"))) {
        UnitTypes.BARBARIAN -> BarbarianData.serializer()
        UnitTypes.MAGICIAN -> MageData.serializer()
        UnitTypes.HEALER -> HealerData.serializer()
    }
}