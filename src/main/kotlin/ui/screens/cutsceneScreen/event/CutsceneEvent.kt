package ui.screens.cutsceneScreen.event

import ui.screens.cutsceneScreen.CutsceneState

interface CutsceneEvent {
    val charID: Int
    val clickRequired: Boolean
    val wait: Boolean

    fun newState(state: CutsceneState): CutsceneState
}
