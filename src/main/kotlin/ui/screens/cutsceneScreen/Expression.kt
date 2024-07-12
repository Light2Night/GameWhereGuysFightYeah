package ui.screens.cutsceneScreen

import kotlinx.serialization.Serializable

@Serializable(with = ExpressionSerializer::class)
data class Expression(
    val name: String,
    val image: String,
)