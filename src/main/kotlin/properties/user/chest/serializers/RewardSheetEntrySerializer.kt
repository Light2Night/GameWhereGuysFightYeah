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
import properties.user.chest.*

class RewardSheetEntrySerializer : KSerializer<RewardSheetEntry> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("RewardSheetEntry") {
        element<String>("type")
        element<Int>("weight")
        element<Int>("from")
        element<Int>("to")
    }

    override fun deserialize(decoder: Decoder): RewardSheetEntry = decoder.decodeStructure(descriptor) {
        var type: SheetEntryType? = null
        var weight: Int? = null
        var from: Int? = null
        var to: Int? = null

        while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                -1 -> break
                0 -> type = SheetEntryType.valueOf(decodeStringElement(descriptor, 0))
                1 -> weight = decodeIntElement(descriptor, 1)
                2 -> from = decodeIntElement(descriptor, 2)
                3 -> to = decodeIntElement(descriptor, 3)
                else -> throw SerializationException("Unexpected index $index")
            }
        }

        return@decodeStructure RewardSheetEntry(
            type = type ?: throw SerializationException("Missing type"),
            weight = weight ?: throw SerializationException("Missing weight"),
            value = (from ?: throw SerializationException("Missing from"))..(to ?: throw SerializationException("Missing to")),
        )
    }

    override fun serialize(encoder: Encoder, value: RewardSheetEntry) = encoder.encodeStructure(descriptor) {
        encodeStringElement(descriptor, 0, value.type.toString())
        encodeIntElement(descriptor, 1, value.weight)
        encodeIntElement(descriptor, 2, value.value.first)
        encodeIntElement(descriptor, 3, value.value.last)
    }
}