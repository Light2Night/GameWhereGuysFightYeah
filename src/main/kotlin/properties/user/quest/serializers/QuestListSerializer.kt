package properties.user.quest.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import properties.user.quest.Quest

class QuestListSerializer : KSerializer<List<Quest>> {
    private val builtIn: KSerializer<List<Quest>> = ListSerializer(QuestSerializer)

    override fun deserialize(decoder: Decoder): List<Quest> {
        return builtIn.deserialize(decoder)
    }

    override val descriptor: SerialDescriptor = builtIn.descriptor

    override fun serialize(encoder: Encoder, value: List<Quest>) {
        builtIn.serialize(encoder, value)
    }

}