package gamedata.event

import Game.Event.Arguments.UnitId
import Game.Event.HandlerGeneric
import Game.Game
import gamedata.GameData

class CurrentUnitChangedEvent(
    private val game: Game,
    private val gameData: GameData,
) : HandlerGeneric<UnitId>() {

    override fun handle(unitId: UnitId) {
        unitId.NewId?.let { gameData.currentUnit = game.getUnitById(it) ?: return }
    }
}