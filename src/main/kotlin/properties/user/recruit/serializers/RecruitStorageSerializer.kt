package properties.user.recruit.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import properties.user.recruit.Recruit
import properties.user.recruit.RecruitStorage

class RecruitStorageSerializer : KSerializer<RecruitStorage> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("RecruitStorage") {
        element<List<Recruit>>("guildRecruits")
        element<List<Recruit>>("recruits")
        element<List<Recruit>>("selectedRecruits")
    }

    override fun deserialize(decoder: Decoder): RecruitStorage = decoder.decodeStructure(descriptor) {
        var guildRecruits: List<Recruit>? = null
        var recruits: List<Recruit>? = null
        var selectedRecruits: List<Recruit>? = null

        while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                -1 -> break
                0 -> guildRecruits = decodeSerializableElement(descriptor, 0, ListSerializer(Recruit.serializer()))
                1 -> recruits = decodeSerializableElement(descriptor, 1, ListSerializer(Recruit.serializer()))
                2 -> selectedRecruits = decodeSerializableElement(descriptor, 2, ListSerializer(Recruit.serializer()))
                else -> throw SerializationException("Unexpected index $index")
            }
        }

        return@decodeStructure RecruitStorage(
            guildRecruits = guildRecruits ?: throw SerializationException("Missing guildRecruits"),
            recruits = recruits ?: throw SerializationException("Missing recruits"),
            selectedRecruits = selectedRecruits ?: throw SerializationException("Missing selectedRecruits"),
        )
    }

    override fun serialize(encoder: Encoder, value: RecruitStorage) = encoder.encodeStructure(descriptor) {
        encodeSerializableElement(descriptor, 0, ListSerializer(Recruit.serializer()), value.guildList)
        encodeSerializableElement(descriptor, 1, ListSerializer(Recruit.serializer()), value.list)
        encodeSerializableElement(descriptor, 2, ListSerializer(Recruit.serializer()), value.selectedList)
    }
}