package properties.resources.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import properties.resources.Reward

class RewardSerializer : KSerializer<Reward> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Reward") {
        element<Int>("coins")
        element<Int>("crystals")
        element<Int>("exp")
    }

    override fun deserialize(decoder: Decoder): Reward = decoder.decodeStructure(descriptor) {
        val resources = Reward(0, 0, 0)

        while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                -1 -> break
                0 -> resources.coins = decodeIntElement(descriptor, 0)
                1 -> resources.crystals = decodeIntElement(descriptor, 1)
                2 -> resources.exp = decodeIntElement(descriptor, 2)
                else -> throw SerializationException("Unexpected index $index")
            }
        }

        return@decodeStructure resources
    }

    override fun serialize(encoder: Encoder, value: Reward) = encoder.encodeStructure(descriptor) {
        encodeIntElement(descriptor, 0, value.coins)
        encodeIntElement(descriptor, 1, value.crystals)
        encodeIntElement(descriptor, 2, value.exp)
    }
}