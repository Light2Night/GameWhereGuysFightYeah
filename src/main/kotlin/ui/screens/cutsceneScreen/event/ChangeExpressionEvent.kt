package ui.screens.cutsceneScreen.event

import ui.screens.cutsceneScreen.CutsceneState
import ui.screens.cutsceneScreen.Expression

class ChangeExpressionEvent(
    override val charID: Int,
    override val clickRequired: Boolean,
    override val wait: Boolean,
    val expression: Expression,
) : CutsceneEvent {
    override fun newState(state: CutsceneState): CutsceneState {
        return state.copy(
            leftExpression = if (charID == state.leftSlot) expression else state.leftExpression,
            centralExpression = if (charID == state.centralSlot) expression else state.centralExpression,
            rightExpression = if (charID == state.rightSlot) expression else state.rightExpression,
        )
    }
}