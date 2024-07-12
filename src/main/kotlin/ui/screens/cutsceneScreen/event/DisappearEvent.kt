package ui.screens.cutsceneScreen.event

import ui.screens.cutsceneScreen.CutsceneDirection
import ui.screens.cutsceneScreen.CutscenePosition
import ui.screens.cutsceneScreen.CutsceneState

class DisappearEvent(
    override val charID: Int,
    override val clickRequired: Boolean,
    override val wait: Boolean,
    val position: CutscenePosition,
    val direction: CutsceneDirection,
) : CutsceneEvent {
    override fun newState(state: CutsceneState): CutsceneState {
        return state.copy(
            leftSlot = if (position == CutscenePosition.Left) null else state.leftSlot,
            centralSlot = if (position == CutscenePosition.Center) null else state.centralSlot,
            rightSlot = if (position == CutscenePosition.Right) null else state.rightSlot,
            leftExpression = "neutral",
            centralExpression = "neutral",
            rightExpression = "neutral",
        )
    }
}