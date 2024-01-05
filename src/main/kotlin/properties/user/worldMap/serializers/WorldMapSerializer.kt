package properties.user.worldMap.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import properties.user.worldMap.WorldMap

class WorldMapSerializer : KSerializer<WorldMap> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("WorldMap") {
        element<String>("test")
    }

    override fun deserialize(decoder: Decoder): WorldMap = decoder.decodeStructure(descriptor) {
        while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                -1 -> break
                0 -> decodeStringElement(descriptor, 0)
                else -> throw SerializationException("Unexpected index $index")
            }
        }

        return@decodeStructure WorldMap()
    }

    override fun serialize(encoder: Encoder, value: WorldMap) = encoder.encodeStructure(descriptor) {
        encodeStringElement(descriptor, 0, "Da?")
    }
}