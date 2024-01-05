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
import properties.resources.UserResources

class UserResourcesSerializer : KSerializer<UserResources> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("UserResources") {
        element<Int>("coins")
        element<Int>("crystals")
        element<Int>("exp")
    }

    override fun deserialize(decoder: Decoder): UserResources = decoder.decodeStructure(descriptor) {
        var coins = 0
        var crystals = 0
        var exp = 0

        while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                -1 -> break
                0 -> coins = decodeIntElement(descriptor, 0)
                1 -> crystals = decodeIntElement(descriptor, 1)
                2 -> exp = decodeIntElement(descriptor, 2)
                else -> throw SerializationException("Unexpected index $index")
            }
        }

        return@decodeStructure UserResources(
            coins,
            crystals,
            exp,
            updateLevels = false,
        )
    }

    override fun serialize(encoder: Encoder, value: UserResources) = encoder.encodeStructure(descriptor) {
        encodeIntElement(descriptor, 0, value.coins)
        encodeIntElement(descriptor, 1, value.crystals)
        encodeIntElement(descriptor, 2, value.exp)
    }
}