package properties.user.quest.serializers

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import properties.resources.Reward
import properties.user.quest.KillNameQuest

class KillNameQuestSerializer : KSerializer<KillNameQuest> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("killNameQuest") {
        element<String>("type")
        element<Int>("id")
        element<Int>("x")
        element<Int>("y")
        element<String>("name")
        element<String>("description")
        element<String>("icon")
        element<Int>("requiredLevel")
        element<Reward>("reward")
        element<Double>("target")
        element<Double>("current")
        element<String>("additional")
    }

    override fun deserialize(decoder: Decoder): KillNameQuest = decoder.decodeStructure(descriptor) {
        var id: Int? = null
        var x: Int? = null
        var y: Int? = null
        var name: String? = null
        var description: String? = null
        var icon: String? = null
        var requiredLevel: Int? = null
        var reward: Reward? = null
        var target: Double? = null
        var current: Double? = null
        var additional: String? = null

        while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                -1 -> break
                0 -> decodeStringElement(descriptor, 0)
                1 -> id = decodeIntElement(descriptor, 1)
                2 -> x = decodeIntElement(descriptor, 2)
                3 -> y = decodeIntElement(descriptor, 3)
                4 -> name = decodeStringElement(descriptor, 4)
                5 -> description = decodeStringElement(descriptor, 5)
                6 -> icon = decodeStringElement(descriptor, 6)
                7 -> requiredLevel = decodeIntElement(descriptor, 7)
                8 -> reward = decodeSerializableElement(descriptor, 8, Reward.serializer())
                9 -> target = decodeDoubleElement(descriptor, 9)
                10 -> current = decodeDoubleElement(descriptor, 10)
                11 -> additional = decodeStringElement(descriptor, 11)
                else -> throw SerializationException("Unexpected index $index")
            }
        }

        return@decodeStructure KillNameQuest(
            id ?: throw SerializationException("Missing id"),
            x ?: throw SerializationException("Missing x"),
            y ?: throw SerializationException("Missing y"),
            name ?: throw SerializationException("Missing name"),
            description ?: throw SerializationException("Missing description"),
            icon ?: throw SerializationException("Missing icon"),
            requiredLevel ?: throw SerializationException("Missing requiredLevel"),
            reward ?: throw SerializationException("Missing reward"),
            target ?: throw SerializationException("Missing target"),
            current ?: throw SerializationException("Missing current"),
            additional ?: throw SerializationException("Missing additional"),
        )
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: KillNameQuest) = encoder.encodeStructure(descriptor) {
        encodeStringElement(descriptor, 0, descriptor.serialName)
        encodeIntElement(descriptor, 1, value.id)
        encodeIntElement(descriptor, 2, value.x)
        encodeIntElement(descriptor, 3, value.y)
        encodeStringElement(descriptor, 4, value.name)
        encodeStringElement(descriptor, 5, value.description)
        encodeStringElement(descriptor, 6, value.icon)
        encodeIntElement(descriptor, 7, value.requiredLevel)
        encodeSerializableElement(descriptor, 8, Reward.serializer(), value.reward)
        encodeDoubleElement(descriptor, 9, value.target)
        encodeDoubleElement(descriptor, 10, value.current)
        encodeStringElement(descriptor, 11, value.charName)
    }
}