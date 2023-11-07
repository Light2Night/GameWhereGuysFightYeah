package ui.event

import Game.Event.Arguments.Actions.ActionInfo
import Game.Event.UnitActionEventable
import GameData

class MoveInfoUpdatedEvent(
    private val gameData: GameData,
) : UnitActionEventable {
    override fun onEvent(info: ActionInfo) {
        gameData.addActionInfo(info)
    }
}