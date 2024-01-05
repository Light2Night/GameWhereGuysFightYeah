package properties.settings

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

class SettingsSerializer : KSerializer<Settings> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("chat.Settings") {
        element<String>("language")
    }

    override fun deserialize(decoder: Decoder): Settings = decoder.decodeStructure(descriptor) {
        val settings = Settings()

        while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                -1 -> break
                0 -> settings.language = decodeStringElement(descriptor, 0)
                else -> throw SerializationException("Unexpected index $index")
            }
        }

        return@decodeStructure settings
    }

    override fun serialize(encoder: Encoder, value: Settings) = encoder.encodeStructure(descriptor) {
        encodeStringElement(descriptor, 0, value.language)
    }
}