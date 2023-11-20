package gamedata.event

import Game.Event.Arguments.Actions.ActionInfo
import Game.Event.UnitActionEventable
import gamedata.GameData

class MoveInfoUpdatedEvent(
    private val gameData: GameData,
) : UnitActionEventable {
    override fun onEvent(info: ActionInfo) {
        gameData.addActionInfo(info)
    }
}