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
import properties.user.recruit.MageData

class MageDataSerializer : KSerializer<MageData> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("mageData") {
        element<String>("type")
        element<Int>("maxHP")
        element<Int>("damage")
        element<Int>("damageDelta")
        element<Int>("magicalEffectDamage")
        element<Int>("magicalEffectTurns")
    }

    override fun deserialize(decoder: Decoder): MageData = decoder.decodeStructure(descriptor) {
        var type: String? = null
        var maxHP: Int? = null
        var damage: Int? = null
        var damageDelta: Int? = null
        var magicalEffectDamage: Int? = null
        var magicalEffectTurns: Int? = null

        while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                -1 -> break
                0 -> type = decodeStringElement(descriptor, 0)
                1 -> maxHP = decodeIntElement(descriptor, 1)
                2 -> damage = decodeIntElement(descriptor, 2)
                3 -> damageDelta = decodeIntElement(descriptor, 3)
                4 -> magicalEffectDamage = decodeIntElement(descriptor, 4)
                5 -> magicalEffectTurns = decodeIntElement(descriptor, 5)
                else -> throw SerializationException("Unexpected index $index")
            }
        }

        return@decodeStructure MageData(
            type = UnitTypes.valueOf(type ?: throw SerializationException("Missing type")),
            maxHP = maxHP ?: throw SerializationException("Missing maxHP"),
            damage = damage ?: throw SerializationException("Missing damage"),
            damageDelta = damageDelta ?: throw SerializationException("Missing damageDelta"),
            magicalEffectDamage = magicalEffectDamage ?: throw SerializationException("Missing magicalEffectDamage"),
            magicalEffectTurns = magicalEffectTurns ?: throw SerializationException("Missing magicalEffectTurns"),
        )
    }

    override fun serialize(encoder: Encoder, value: MageData) = encoder.encodeStructure(descriptor) {
        encodeStringElement(descriptor, 0, value.type.toString())
        encodeIntElement(descriptor, 1, value.maxHP)
        encodeIntElement(descriptor, 2, value.damage)
        encodeIntElement(descriptor, 3, value.damageDelta)
        encodeIntElement(descriptor, 4, value.magicalEffectDamage)
        encodeIntElement(descriptor, 5, value.magicalEffectTurns)
    }
}