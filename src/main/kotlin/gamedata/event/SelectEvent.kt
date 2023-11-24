package gamedata.event

import Game.Event.Arguments.UnitId
import Game.Event.HandlerGeneric
import Game.Game
import gamedata.GameData

class SelectEvent(
    private val game: Game,
    private val gameData: GameData,
) : HandlerGeneric<UnitId>() {

    override fun handle(unitId: UnitId) {
        unitId.NewId?.let { gameData.selectedUnit = game.getUnitById(it) ?: return }
    }
}