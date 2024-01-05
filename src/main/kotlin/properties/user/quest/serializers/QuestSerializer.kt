package properties.user.quest.serializers

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import properties.user.quest.KillCharacterQuest
import properties.user.quest.KillNameQuest
import properties.user.quest.KillQuest
import properties.user.quest.Quest

object QuestSerializer : JsonContentPolymorphicSerializer<Quest>(Quest::class) {
    override fun selectDeserializer(element: JsonElement) = when(element.jsonObject["type"]?.jsonPrimitive?.content) {
        "killQuest" -> KillQuest.serializer()
        "killNameQuest" -> KillNameQuest.serializer()
        "killCharacterQuest" -> KillCharacterQuest.serializer()
        else -> throw SerializationException("Unknown quest type: ${element.jsonObject["type"]?.jsonPrimitive?.content}")
    }
}