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
import properties.user.chest.RewardSheet
import properties.user.chest.RewardSheetEntry

class RewardSheetSerializer : KSerializer<RewardSheet> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("RewardSheet") {
        element<Int>("amount")
        element<RewardSheetEntry>("coins")
        element<RewardSheetEntry>("crystals")
        element<RewardSheetEntry>("oneStar")
        element<RewardSheetEntry>("twoStar")
        element<RewardSheetEntry>("threeStar")
    }

    override fun deserialize(decoder: Decoder): RewardSheet = decoder.decodeStructure(descriptor) {
        var amount: Int? = null
        var coins: RewardSheetEntry? = null
        var crystals: RewardSheetEntry? = null
        var oneStar: RewardSheetEntry? = null
        var twoStar: RewardSheetEntry? = null
        var threeStar: RewardSheetEntry? = null

        while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                -1 -> break
                0 -> amount = decodeIntElement(descriptor, 0)
                1 -> coins = decodeSerializableElement(descriptor, 1, RewardSheetEntry.serializer())
                2 -> crystals = decodeSerializableElement(descriptor, 2, RewardSheetEntry.serializer())
                3 -> oneStar = decodeSerializableElement(descriptor, 3, RewardSheetEntry.serializer())
                4 -> twoStar = decodeSerializableElement(descriptor, 4, RewardSheetEntry.serializer())
                5 -> threeStar = decodeSerializableElement(descriptor, 5, RewardSheetEntry.serializer())
                else -> throw SerializationException("Unexpected index $index")
            }
        }

        return@decodeStructure RewardSheet(
            amount = amount ?: throw SerializationException("Missing amount"),
            coins = coins ?: throw SerializationException("Missing coins"),
            crystals = crystals ?: throw SerializationException("Missing crystals"),
            oneStar = oneStar ?: throw SerializationException("Missing oneStar"),
            twoStar = twoStar ?: throw SerializationException("Missing twoStar"),
            threeStar = threeStar ?: throw SerializationException("Missing threeStar"),
        )
    }

    override fun serialize(encoder: Encoder, value: RewardSheet) = encoder.encodeStructure(descriptor) {
        encodeIntElement(descriptor, 0, value.amount)
        encodeSerializableElement(descriptor, 1, RewardSheetEntry.serializer(), value.coins)
        encodeSerializableElement(descriptor, 2, RewardSheetEntry.serializer(), value.crystals)
        encodeSerializableElement(descriptor, 3, RewardSheetEntry.serializer(), value.oneStar)
        encodeSerializableElement(descriptor, 4, RewardSheetEntry.serializer(), value.twoStar)
        encodeSerializableElement(descriptor, 5, RewardSheetEntry.serializer(), value.threeStar)
    }
}