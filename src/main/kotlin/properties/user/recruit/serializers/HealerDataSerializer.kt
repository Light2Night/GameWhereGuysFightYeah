package properties.user.recruit.serializers

import Game.Units.Characters.UnitTypes
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import properties.user.recruit.HealerData

class HealerDataSerializer : KSerializer<HealerData> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("healerData") {
        element<String>("type")
        element<Int>("maxHP")
        element<Int>("heal")
        element<Int>("healingEffectHeal")
        element<Int>("healingEffectTurns")
    }

    override fun deserialize(decoder: Decoder): HealerData = decoder.decodeStructure(descriptor) {
        var type: String? = null
        var maxHP: Int? = null
        var heal: Int? = null
        var healingEffectHeal: Int? = null
        var healingEffectTurns: Int? = null

        while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                -1 -> break
                0 -> type = decodeStringElement(descriptor, 0)
                1 -> maxHP = decodeIntElement(descriptor, 1)
                2 -> heal = decodeIntElement(descriptor, 2)
                3 -> healingEffectHeal = decodeIntElement(descriptor, 3)
                4 -> healingEffectTurns = decodeIntElement(descriptor, 4)
                else -> throw SerializationException("Unexpected index $index")
            }
        }

        return@decodeStructure HealerData(
            type = UnitTypes.valueOf(type ?: throw SerializationException("Missing type")),
            maxHP = maxHP ?: throw SerializationException("Missing maxHP"),
            heal = heal ?: throw SerializationException("Missing heal"),
            healingEffectHeal = healingEffectHeal ?: throw SerializationException("Missing healingEffectHeal"),
            healingEffectTurns = healingEffectTurns ?: throw SerializationException("Missing healingEffectTurns"),
        )
    }

    override fun serialize(encoder: Encoder, value: HealerData) = encoder.encodeStructure(descriptor) {
        encodeStringElement(descriptor, 0, value.type.toString())
        encodeIntElement(descriptor, 1, value.maxHP)
        encodeIntElement(descriptor, 2, value.heal)
        encodeIntElement(descriptor, 3, value.healingEffectHeal)
        encodeIntElement(descriptor, 4, value.healingEffectTurns)
    }
}