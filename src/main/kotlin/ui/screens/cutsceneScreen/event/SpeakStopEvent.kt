package ui.screens.cutsceneScreen.event

import ui.screens.cutsceneScreen.CutsceneState

class SpeakStopEvent(
    override val charID: Int,
    override val clickRequired: Boolean,
    override val wait: Boolean,
) : CutsceneEvent {
    override fun newState(state: CutsceneState): CutsceneState {
        return state.copy(speakingChar = null, speakingName = null, speakingText = "")
    }
}