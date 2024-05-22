package ui.screens.cutsceneScreen.event

import ui.screens.cutsceneScreen.CutscenePosition
import ui.screens.cutsceneScreen.CutsceneState

class ChangePositionEvent(
    override val charID: Int,
    override val clickRequired: Boolean,
    override val wait: Boolean,
    val oldPosition: CutscenePosition,
    val newPosition: CutscenePosition,
) : CutsceneEvent {
    override fun newState(state: CutsceneState): CutsceneState {
        return state.copy(
            leftSlot = if (newPosition == CutscenePosition.Left) charID else if (oldPosition == CutscenePosition.Left) null else state.leftSlot,
            centralSlot = if (newPosition == CutscenePosition.Center) charID else if (oldPosition == CutscenePosition.Center) null else state.centralSlot,
            rightSlot = if (newPosition == CutscenePosition.Right) charID else if (oldPosition == CutscenePosition.Right) null else state.rightSlot,
        )
    }
}