package properties.user.chest.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import properties.user.chest.Chest
import properties.user.chest.ChestStorage
import properties.user.quest.Quest

class ChestStorageSerializer : KSerializer<ChestStorage> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ChestStorage") {
        element<List<Quest>>("chests")
    }

    override fun deserialize(decoder: Decoder): ChestStorage = decoder.decodeStructure(descriptor) {
        while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                -1 -> break
                0 -> return@decodeStructure ChestStorage(decodeSerializableElement(descriptor, 0, MapSerializer(Int.serializer(), Chest.serializer())))
                else -> throw SerializationException("Unexpected index $index")
            }
        }

        return@decodeStructure ChestStorage()
    }

    override fun serialize(encoder: Encoder, value: ChestStorage) = encoder.encodeStructure(descriptor) {
        encodeSerializableElement(descriptor, 0, MapSerializer(Int.serializer(), Chest.serializer()), value.list)
    }
}