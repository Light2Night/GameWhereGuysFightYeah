package properties.user.chest.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import properties.user.chest.Chest
import properties.user.chest.ChestType
import properties.user.chest.RewardSheet

class ChestSerializer : KSerializer<Chest> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Chest") {
        element<Int>("id")
        element<String>("type")
        element<RewardSheet>("sheet")
    }

    override fun deserialize(decoder: Decoder): Chest = decoder.decodeStructure(descriptor) {
        var id: Int? = null
        var type: ChestType? = null
        var sheet: RewardSheet? = null

        while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                -1 -> break
                0 -> id = decodeIntElement(descriptor, 0)
                1 -> type = ChestType.valueOf(decodeStringElement(descriptor, 1))
                2 -> sheet = decodeSerializableElement(descriptor, 2, RewardSheet.serializer())
                else -> throw SerializationException("Unexpected index $index")
            }
        }

        return@decodeStructure Chest(
            id = id ?: throw SerializationException("Missing id"),
            type = type ?: throw SerializationException("Missing type"),
            sheet = sheet ?: throw SerializationException("Missing sheet"),
        )
    }

    override fun serialize(encoder: Encoder, value: Chest) = encoder.encodeStructure(descriptor) {
        encodeIntElement(descriptor, 0, value.id)
        encodeStringElement(descriptor, 1, value.type.toString())
        encodeSerializableElement(descriptor, 2, RewardSheet.serializer(), value.sheet)
    }
}