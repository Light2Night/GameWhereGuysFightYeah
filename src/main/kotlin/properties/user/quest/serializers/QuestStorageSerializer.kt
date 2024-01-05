package properties.user.quest.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import properties.user.quest.Quest
import properties.user.quest.QuestStorage

class QuestStorageSerializer : KSerializer<QuestStorage> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("QuestStorage") {
        element<List<Quest>>("quests")
    }

    override fun deserialize(decoder: Decoder): QuestStorage = decoder.decodeStructure(descriptor) {
        while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                -1 -> break
                0 -> return@decodeStructure QuestStorage(decodeSerializableElement(descriptor, 0, QuestListSerializer()))
                else -> throw SerializationException("Unexpected index $index")
            }
        }

        return@decodeStructure QuestStorage()
    }

    override fun serialize(encoder: Encoder, value: QuestStorage) = encoder.encodeStructure(descriptor) {
        encodeSerializableElement(descriptor, 0, QuestListSerializer(), value.list)
    }
}