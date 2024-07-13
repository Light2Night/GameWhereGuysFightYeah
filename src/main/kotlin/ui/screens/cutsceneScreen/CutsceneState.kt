package ui.screens.cutsceneScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class CutsceneState(
    leftSlot: Int? = null,
    centralSlot: Int? = null,
    rightSlot: Int? = null,
    leftExpression: String = "neutral",
    centralExpression: String = "neutral",
    rightExpression: String = "neutral",
    speakingChar: Int? = null,
    speakingName: String? = null,
    speakingText: String = "",
) {
    var leftSlot by mutableStateOf(leftSlot)
    var centralSlot by mutableStateOf(centralSlot)
    var rightSlot by mutableStateOf(rightSlot)
    var leftExpression by mutableStateOf(leftExpression)
    var centralExpression by mutableStateOf(centralExpression)
    var rightExpression by mutableStateOf(rightExpression)
    var speakingChar by mutableStateOf(speakingChar)
    var speakingName by mutableStateOf(speakingName)
    var speakingText by mutableStateOf(speakingText)

    fun copy(
        leftSlot: Int? = this.leftSlot,
        centralSlot: Int? = this.centralSlot,
        rightSlot: Int? = this.rightSlot,
        leftExpression: String = this.leftExpression,
        centralExpression: String = this.centralExpression,
        rightExpression: String = this.rightExpression,
        speakingChar: Int? = this.speakingChar,
        speakingName: String? = this.speakingName,
        speakingText: String = this.speakingText
    ): CutsceneState = CutsceneState(
        leftSlot,
        centralSlot,
        rightSlot,
        leftExpression,
        centralExpression,
        rightExpression,
        speakingChar,
        speakingName,
        speakingText
    )
}
