package ui.screens.cutsceneScreen.event

import ui.screens.cutsceneScreen.CutsceneState

class SpeakEvent(
    override val charID: Int,
    override val clickRequired: Boolean,
    override val wait: Boolean,
    val text: String,
) : CutsceneEvent {
    override fun newState(state: CutsceneState): CutsceneState {
        return state.copy(speakingChar = charID, speakingText = text)
    }
}