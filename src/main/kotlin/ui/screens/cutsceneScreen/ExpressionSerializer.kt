package ui.screens.cutsceneScreen

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

class ExpressionSerializer : KSerializer<Expression> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("expression") {
        element<String>("name")
        element<String>("image")
    }

    override fun deserialize(decoder: Decoder): Expression = decoder.decodeStructure(descriptor) {
        var name: String? = null
        var image: String? = null

        while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                -1 -> break
                0 -> name = decodeStringElement(descriptor, 0)
                1 -> image = decodeStringElement(descriptor, 1)
                else -> throw SerializationException("Unexpected index $index")
            }
        }

        return@decodeStructure Expression(
            name ?: throw SerializationException("Missing name"),
            image ?: throw SerializationException("Missing image"),
        )
    }

    override fun serialize(encoder: Encoder, value: Expression) = encoder.encodeStructure(descriptor) {
        encodeStringElement(descriptor, 0, value.name)
        encodeStringElement(descriptor, 1, value.image)
    }
}