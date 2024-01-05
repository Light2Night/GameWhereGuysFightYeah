package properties.user

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import properties.resources.UserResources
import properties.resources.calculateRequiredExp
import properties.user.chest.ChestStorage
import properties.user.quest.QuestStorage
import properties.user.recruit.RecruitStorage
import properties.user.worldMap.WorldMap

class UserSerializer : KSerializer<User> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("chat.User") {
        element<String>("name")
        element<UserResources>("resources")
        element<Int>("level")
        element<QuestStorage>("quests")
        element<RecruitStorage>("recruits")
        element<WorldMap>("worldMap")
        element<ChestStorage>("chests")
    }

    override fun deserialize(decoder: Decoder): User = decoder.decodeStructure(descriptor) {
        val user = User()

        while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                -1 -> break
                0 -> user.name = decodeStringElement(descriptor, 0)
                1 -> user.resources = decodeSerializableElement(descriptor, 1, UserResources.serializer())
                2 -> user.level = decodeIntElement(descriptor, 2)
                3 -> user.quests = decodeSerializableElement(descriptor, 3, QuestStorage.serializer())
                4 -> user.recruits = decodeSerializableElement(descriptor, 4, RecruitStorage.serializer())
                5 -> user.worldMap = decodeSerializableElement(descriptor, 5, WorldMap.serializer())
                6 -> user.chests = decodeSerializableElement(descriptor, 6, ChestStorage.serializer())
                else -> throw SerializationException("Unexpected index $index")
            }
        }

        return@decodeStructure user.also { it.requiredExp = calculateRequiredExp(it.level) }
    }

    override fun serialize(encoder: Encoder, value: User) = encoder.encodeStructure(descriptor) {
        encodeStringElement(descriptor, 0, value.name)
        encodeSerializableElement(descriptor, 1, UserResources.serializer(), value.resources)
        encodeIntElement(descriptor, 2, value.level)
        encodeSerializableElement(descriptor, 3, QuestStorage.serializer(), value.quests)
        encodeSerializableElement(descriptor, 4, RecruitStorage.serializer(), value.recruits)
        encodeSerializableElement(descriptor, 5, WorldMap.serializer(), value.worldMap)
        encodeSerializableElement(descriptor, 6, ChestStorage.serializer(), value.chests)
    }
}