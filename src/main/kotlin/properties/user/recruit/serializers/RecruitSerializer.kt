package properties.user.recruit.serializers

import kotlinx.serialization.ExperimentalSerializationApi
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
import properties.resources.Cost
import properties.user.recruit.Recruit
import properties.user.recruit.RecruitData
import ui.screens.cutsceneScreen.Expression

class RecruitSerializer : KSerializer<Recruit> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("recruit") {
        element<Int>("id")
        element<Int>("charID")
        element<String>("name")
        element<String>("description")
        element<Int>("stars")
        element<Int>("level")
        element<String>("profileImage")
        element<List<Expression>>("expressions")
        element<RecruitData>("rawData")
        element<Cost?>("cost")
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun deserialize(decoder: Decoder): Recruit = decoder.decodeStructure(descriptor) {
        var id: Int? = null
        var charID: Int? = null
        var name: String? = null
        var description: String? = null
        var stars: Int? = null
        var level: Int? = null
        var profileImage: String? = null
        var expressions: List<Expression>? = null
        var rawData: RecruitData? = null
        var cost: Cost? = null

        while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                -1 -> break
                0 -> id = decodeIntElement(descriptor, 0)
                1 -> charID = decodeIntElement(descriptor, 1)
                2 -> name = decodeStringElement(descriptor, 2)
                3 -> description = decodeStringElement(descriptor, 3)
                4 -> stars = decodeIntElement(descriptor, 4)
                5 -> level = decodeIntElement(descriptor, 5)
                6 -> profileImage = decodeStringElement(descriptor, 6)
                7 -> expressions = decodeSerializableElement(descriptor, 7, ListSerializer(Expression.serializer()))
                8 -> rawData = decodeSerializableElement(descriptor, 8, RecruitDataSerializer)
                9 -> cost = decodeNullableSerializableElement(descriptor, 9, Cost.serializer())
                else -> throw SerializationException("Unexpected index $index")
            }
        }

        return@decodeStructure Recruit(
            id ?: throw SerializationException("Missing id"),
            charID ?: throw SerializationException("Missing charID"),
            name ?: throw SerializationException("Missing name"),
            description ?: throw SerializationException("Missing description"),
            stars ?: throw SerializationException("Missing stars"),
            level ?: throw SerializationException("Missing level"),
            profileImage ?: throw SerializationException("Missing profileImage"),
            expressions ?: throw SerializationException("Missing expressions"),
            rawData ?: throw SerializationException("Missing rawData"),
            cost,
        )
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: Recruit) = encoder.encodeStructure(descriptor) {
        encodeIntElement(descriptor, 0, value.id)
        encodeIntElement(descriptor, 1, value.charID)
        encodeStringElement(descriptor, 2, value.name)
        encodeStringElement(descriptor, 3, value.description)
        encodeIntElement(descriptor, 4, value.stars)
        encodeIntElement(descriptor, 5, value.level)
        encodeStringElement(descriptor, 6, value.profileImage)
        encodeSerializableElement(descriptor, 7, ListSerializer(Expression.serializer()), value.expressions)
        encodeSerializableElement(descriptor, 8, RecruitDataSerializer, value.rawData)
        encodeNullableSerializableElement(descriptor, 9, Cost.serializer(), value.cost)
    }
}