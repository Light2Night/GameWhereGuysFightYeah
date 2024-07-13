package ui.screens.cutsceneScreen.event

import ui.screens.cutsceneScreen.CutsceneState

class SpeakCustomEvent(
    override val charID: Int,
    override val clickRequired: Boolean,
    override val wait: Boolean,
    val name: String,
    val text: String,
) : CutsceneEvent {
    override fun newState(state: CutsceneState): CutsceneState {
        return state.copy(speakingChar = null, speakingName = name, speakingText = text)
    }
}